package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by Paras-Android on 27-12-2017.
 */

public class KaisChildViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_kais_name,tv_kais_des,tv_kais_phone,tv_kais_roll;
    public LinearLayout ll_kaisthana;
    public ImageView iv_kais;

    public KaisChildViewHolder(View view) {
        super(view);
        tv_kais_name = (TextView) view.findViewById(R.id.tv_kais_name);
        tv_kais_des = (TextView) view.findViewById(R.id.tv_kais_des);
        tv_kais_phone = (TextView) view.findViewById(R.id.tv_kais_phone);
        ll_kaisthana = (LinearLayout)view.findViewById(R.id.ll_kaisthana);
        tv_kais_roll = view.findViewById(R.id.tv_kais_roll);
        iv_kais = view.findViewById(R.id.iv_kais);
    }
}