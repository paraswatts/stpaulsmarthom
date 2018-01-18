package stpaulsmarthom.com.stpaulsmarthom.Adapters;

/**
 * Created by Paras-Android on 22-12-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import stpaulsmarthom.com.stpaulsmarthom.Activities.BishopDetailActivity;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.BishopModel;
import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by Belal on 10/18/2017.
 */

public class BishopAdapter extends RecyclerView.Adapter<BishopAdapter.MyViewHolder> {

    private List<BishopModel> bishopList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_bishop;
        public ImageView iv_bishop;
        public LinearLayout ll_bishop_detail;

        public MyViewHolder(View view) {
            super(view);
            tv_bishop = (TextView) view.findViewById(R.id.tv_bishop);
            iv_bishop = (ImageView) view.findViewById(R.id.iv_bishop);
            ll_bishop_detail = (LinearLayout)view.findViewById(R.id.ll_bishop_detail);
        }
    }


    public BishopAdapter(List<BishopModel> moviesList, Context context) {
        this.context = context;
        this.bishopList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bishop_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final BishopModel bishop = bishopList.get(position);
       // Log.e("name in adapter",""+bishop.getName());
        holder.tv_bishop.setText(bishop.getName());
        final String imageUrl= "http://stpaulsmarthomabahrain.com/membershipform/church_admin/Bishops/"+bishop.getImage_url();
//        Glide.with(context)
//                .load(imageUrl)
//                .placeholder(R.drawable.sample)
//                .into(holder.iv_bishop);
        Picasso picasso = Picasso.with(context);
        picasso.setIndicatorsEnabled(false);
        picasso.load(imageUrl)
                .placeholder(R.drawable.sample)
                .error(R.drawable.sample)
                .into(holder.iv_bishop);
        holder.ll_bishop_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bishopDetailIntent = new Intent(context, BishopDetailActivity.class);
                bishopDetailIntent.putExtra("bishopPhone",bishop.getPhone());
                bishopDetailIntent.putExtra("bishopEmail",bishop.getEmail());
                bishopDetailIntent.putExtra("bishopLocation",bishop.getAddress());
                bishopDetailIntent.putExtra("bishopImage",imageUrl);
                bishopDetailIntent.putExtra("bishopName",bishop.getName());
                bishopDetailIntent.putExtra("bishopDob",bishop.getDob());
                context.startActivity(bishopDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bishopList.size();
    }
}