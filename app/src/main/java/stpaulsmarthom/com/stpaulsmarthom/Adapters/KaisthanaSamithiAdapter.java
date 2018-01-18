package stpaulsmarthom.com.stpaulsmarthom.Adapters;

/**
 * Created by Paras-Android on 22-12-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.KaisthanaSamithiModel;
import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by Belal on 10/18/2017.
 */

public class KaisthanaSamithiAdapter extends RecyclerView.Adapter<KaisthanaSamithiAdapter.MyViewHolder> {

    private List<KaisthanaSamithiModel> kaisthanaList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_kais_name,tv_kais_des,tv_kais_phone;
        public LinearLayout ll_kaisthana;


        public MyViewHolder(View view) {
            super(view);
            tv_kais_name = (TextView) view.findViewById(R.id.tv_kais_name);
            tv_kais_des = (TextView) view.findViewById(R.id.tv_kais_des);
            tv_kais_phone = (TextView) view.findViewById(R.id.tv_kais_phone);
            ll_kaisthana = (LinearLayout)view.findViewById(R.id.ll_kaisthana);
        }
    }


    public KaisthanaSamithiAdapter(List<KaisthanaSamithiModel> moviesList, Context context) {
        this.context = context;
        this.kaisthanaList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kaisthana_samithi_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final KaisthanaSamithiModel kaisthana = kaisthanaList.get(position);
        //Log.e("name in adapter",""+ kaisthana.getName());
        holder.tv_kais_name.setText(kaisthana.getName());
        holder.tv_kais_des.setText(kaisthana.getDesignation());
        holder.tv_kais_phone.setText(kaisthana.getPhone());

        holder.tv_kais_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+kaisthana.getPhone()));
                context.startActivity(intent);
            }
        });
        if(position % 2==0)
            holder.ll_kaisthana.setBackgroundColor(Color.parseColor("#ececec"));
        else
        holder.ll_kaisthana.setBackgroundColor(Color.parseColor("#FFFFFF"));


    }

    @Override
    public int getItemCount() {
        return kaisthanaList.size();
    }
}