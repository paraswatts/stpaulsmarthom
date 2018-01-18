package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by Paras-Android on 27-12-2017.
 */

public class OrgChildViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_org_name,tv_org_des,tv_org_phone;
    public LinearLayout ll_org;

    public OrgChildViewHolder(View view) {
        super(view);
        tv_org_name = (TextView) view.findViewById(R.id.tv_org_name);
        tv_org_des = (TextView) view.findViewById(R.id.tv_org_des);
        tv_org_phone = (TextView) view.findViewById(R.id.tv_org_phone);
        ll_org = (LinearLayout)view.findViewById(R.id.ll_org);    }
}