package stpaulsmarthom.com.stpaulsmarthom.Adapters;

/**
 * Created by Paras-Android on 22-12-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.KaisthanaSamithiModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgDetailModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgTimingModel;
import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by Belal on 10/18/2017.
 */

public class OrgDetailAdapter extends RecyclerView.Adapter<OrgDetailAdapter.MyViewHolder> {

    private List<OrgTimingModel> orgList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_org_timing_type,tv_org_timing;
        public LinearLayout ll_org;


        public MyViewHolder(View view) {
            super(view);
            tv_org_timing_type = (TextView) view.findViewById(R.id.tv_org_timing_type);
            tv_org_timing = (TextView) view.findViewById(R.id.tv_org_timing);
            ll_org = (LinearLayout)view.findViewById(R.id.ll_org_timing);
        }
    }


    public OrgDetailAdapter(List<OrgTimingModel> orgList, Context context) {
        this.context = context;
        this.orgList = orgList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.org_timing_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final OrgTimingModel orgDetailModel = orgList.get(position);
        Log.e("name in adapter",""+ orgDetailModel.getPhone());
        holder.tv_org_timing.setText(orgDetailModel.getPhone());

        if(TextUtils.isEmpty(orgDetailModel.getName()))
        {
            holder.tv_org_timing_type.setVisibility(View.GONE);
        }
        else
        {
            holder.tv_org_timing_type.setText(orgDetailModel.getName());

        }
        if(position % 2==0)
            holder.ll_org.setBackgroundColor(Color.parseColor("#ececec"));
        else
        holder.ll_org.setBackgroundColor(Color.parseColor("#FFFFFF"));


    }

    @Override
    public int getItemCount() {
        return orgList.size();
    }
}