package stpaulsmarthom.com.stpaulsmarthom.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

import stpaulsmarthom.com.stpaulsmarthom.Activities.BishopDetailActivity;
import stpaulsmarthom.com.stpaulsmarthom.Activities.KaisChildViewHolder;
import stpaulsmarthom.com.stpaulsmarthom.Activities.OrgChildViewHolder;
import stpaulsmarthom.com.stpaulsmarthom.Activities.SectionViewHolder;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.KaisthanaSamithiModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgDetailModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.SectionHeader;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.SectionHeaderKais;
import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by Paras-Android on 27-12-2017.
 */

public class KaisthanaSectionRecycler extends SectionRecyclerViewAdapter<SectionHeaderKais, KaisthanaSamithiModel, SectionViewHolder, KaisChildViewHolder> {

    Context context;

    public KaisthanaSectionRecycler(Context context, List<SectionHeaderKais> sectionHeaderItemList) {
        super(context, sectionHeaderItemList);
        this.context = context;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_layout, sectionViewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public KaisChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kaisthana_samithi_layout, childViewGroup, false);
        return new KaisChildViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int sectionPosition, SectionHeaderKais sectionHeader) {
        sectionViewHolder.memberType.setText(sectionHeader.getSectionText());
    }

    @Override
    public void onBindChildViewHolder(KaisChildViewHolder childViewHolder, int sectionPosition, int childPosition, final KaisthanaSamithiModel orgDetailModel) {
        Log.e("name in adapter",""+ orgDetailModel.getName());
        childViewHolder.tv_kais_name.setText(orgDetailModel.getName());
        childViewHolder.tv_kais_des.setText(orgDetailModel.getDesignation());
        childViewHolder.tv_kais_phone.setText(orgDetailModel.getPhone());
        childViewHolder.tv_kais_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+orgDetailModel.getPhone()));
                context.startActivity(intent);
            }
        });

        if(isConnected(context)) {
            final String imageUrl = "http://stpaulsmarthomabahrain.com/membershipform/church_admin/Kaisthana/"+orgDetailModel.getImage();
//        Glide.with(context)
//                .load(imageUrl)
//                .placeholder(R.drawable.sample)
//                .into(holder.iv_bishop);
            Picasso picasso = Picasso.with(context);
            picasso.setIndicatorsEnabled(false);
            picasso.load(imageUrl)
                    .placeholder(R.drawable.sample)
                    .error(R.drawable.sample)
                    .into(childViewHolder.iv_kais);
        }
        else{
//            final String imageUrl = "http://stpaulsmarthomabahrain.com/membershipform/church_admin/Kaisthana/"+orgDetailModel.getImage();
////        Glide.with(context)
////                .load(imageUrl)
////                .placeholder(R.drawable.sample)
////                .into(holder.iv_bishop);
//            Picasso picasso = Picasso.with(context);
//            picasso.setIndicatorsEnabled(false);
//            if(orgDetailModel.getImage()!=null) {
//                picasso.load(orgDetailModel.getImage())
//                        .placeholder(R.drawable.sample)
//                        .error(R.drawable.sample)
//                        .into(childViewHolder.iv_kais);
//            }
            File imgFile = new File(orgDetailModel.getImage());

            if(imgFile.getAbsolutePath().toLowerCase().endsWith(".jpg")||imgFile.getAbsolutePath().toLowerCase().endsWith(".jpeg")) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                childViewHolder.iv_kais.setImageBitmap(myBitmap);
            }

            else {
                childViewHolder.iv_kais.setImageDrawable(context.getResources().getDrawable(R.drawable.sample));


            }
        }
        childViewHolder.tv_kais_roll.setText(orgDetailModel.getRollno());
        if(childPosition % 2==0)
            childViewHolder.ll_kaisthana.setBackgroundColor(Color.parseColor("#ececec"));
        else
            childViewHolder.ll_kaisthana.setBackgroundColor(Color.parseColor("#FFFFFF"));    }


    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}