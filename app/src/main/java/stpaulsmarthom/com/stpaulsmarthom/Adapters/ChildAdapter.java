package stpaulsmarthom.com.stpaulsmarthom.Adapters;

/**
 * Created by Paras-Android on 22-12-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import stpaulsmarthom.com.stpaulsmarthom.Activities.ParishMemberDetailActivity;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ChildModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishMemberModel;
import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by Belal on 10/18/2017.
 */

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MyViewHolder> {

    private List<ChildModel> childModelList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView parish_child_name,parish_dob_child,parish_blood_child,parish_in_bah_child,
                parish_employee_child,parish_sex_child,parish_inst_child,parish_emplr_name_child;
        public ImageView iv_parish_child;
        public ImageView iv_parish_next;

        public MyViewHolder(View view) {
            super(view);
            parish_child_name = view.findViewById(R.id.parish_child_name);
            parish_dob_child = view.findViewById(R.id.parish_dob_child);
            parish_blood_child = view.findViewById(R.id.parish_blood_child);
            parish_in_bah_child = view.findViewById(R.id.parish_in_bah_child);
            parish_employee_child = view.findViewById(R.id.parish_employee_child);
            parish_sex_child = view.findViewById(R.id.parish_sex_child);
            parish_inst_child = view.findViewById(R.id.parish_inst_child);
            parish_emplr_name_child = view.findViewById(R.id.parish_emplr_name_child);
            iv_parish_child = view.findViewById(R.id.iv_parish_child);
        }
    }


    public ChildAdapter(List<ChildModel> childModelList, Context context) {
        this.context = context;
        this.childModelList = childModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ChildModel child = childModelList.get(position);


            holder.parish_child_name.setText(child.getChildname());
        holder.parish_dob_child.setText(child.getDatebrithchild());
        holder.parish_blood_child.setText(child.getBloodchild());
        holder.parish_in_bah_child.setText(child.getChildinbaharian());
        holder.parish_employee_child.setText(child.getEmployechild());
        holder.parish_sex_child.setText(child.getSexchild());
        holder.parish_emplr_name_child.setText(child.getEmployername());

        holder.parish_inst_child.setText(child.getStudentchild());

        final String imageUrl= "http://stpaulsmarthomabahrain.com/membershipform/"+child.getChildsimage();
        //Log.e("Child image path",""+imageUrl);
        //if(isConnected(context)) {
//            Picasso.with(context)
//                    .load(imageUrl)
//                    .placeholder(R.drawable.sample)
//                    .error(R.drawable.sample)
//                    .into(holder.iv_parish_child);

        Picasso picasso = Picasso.with(context);
        picasso.setIndicatorsEnabled(false);
        picasso.load(imageUrl)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.iv_parish_child, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(context)
                                    .load(imageUrl)
                                    .error(R.drawable.sample)
                                    .into(holder.iv_parish_child, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Log.v("Picasso","Could not fetch image");
                                        }
                                    });
                        }
                    });
        //}
//        else
//        {
////            File imgFile = new File(child.getChildsimage());
////            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
////            holder.iv_parish_child.setImageBitmap(myBitmap);
//
//            Picasso picasso = Picasso.with(context);
//            picasso.setIndicatorsEnabled(false);
//                    picasso.load(imageUrl)
//                    .networkPolicy(NetworkPolicy.OFFLINE)
//                    .into(holder.iv_parish_child, new com.squareup.picasso.Callback() {
//                        @Override
//                        public void onSuccess() {
//
//                        }
//
//                        @Override
//                        public void onError() {
//                            //Try again online if cache failed
//                            Picasso.with(context)
//                                    .load(imageUrl)
//                                    .error(R.drawable.sample)
//                                    .into(holder.iv_parish_child, new com.squareup.picasso.Callback() {
//                                        @Override
//                                        public void onSuccess() {
//
//                                        }
//
//                                        @Override
//                                        public void onError() {
//                                            Log.v("Picasso","Could not fetch image");
//                                        }
//                                    });
//                        }
//                    });
//        }
    }

    @Override
    public int getItemCount() {
        return childModelList.size();
    }

    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}