package stpaulsmarthom.com.stpaulsmarthom.Adapters;

/**
 * Created by Paras-Android on 22-12-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import stpaulsmarthom.com.stpaulsmarthom.Activities.OrganisationDetailActivity;
import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by Belal on 10/18/2017.
 */

public class OrgItemAdapter extends RecyclerView.Adapter<OrgItemAdapter.MyViewHolder> {

    private String[] orgList,orgNumber,organisations;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_org_name;
        public RelativeLayout rl_org_item;

        public MyViewHolder(View view) {
            super(view);
            tv_org_name = (TextView) view.findViewById(R.id.tv_org_name);
            rl_org_item = (RelativeLayout)view.findViewById(R.id.rl_org_item);

        }
    }


    public OrgItemAdapter(String[]  orgList,String[] orgNumber,String[] organisations, Context context) {
        this.context = context;
        this.orgList = orgList;
        this.orgNumber = orgNumber;
        this.organisations = organisations;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.org_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_org_name.setText(orgList[position]);
        //Log.e("Org Number",""+position + orgList.length);
        holder.rl_org_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(context, OrganisationDetailActivity.class);
                    intent.putExtra("orgNumber", orgNumber[position]);
                intent.putExtra("orgName", organisations[position]);

                context.startActivity(intent);


            }
        });
    }

    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    @Override
    public int getItemCount() {
        return orgList.length;
    }
}