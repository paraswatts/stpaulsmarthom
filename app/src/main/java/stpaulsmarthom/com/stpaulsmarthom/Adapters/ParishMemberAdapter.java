package stpaulsmarthom.com.stpaulsmarthom.Adapters;

/**
 * Created by Paras-Android on 22-12-2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.io.File;
import java.util.List;
import java.util.TreeMap;

import stpaulsmarthom.com.stpaulsmarthom.Activities.ParishMemberDetailActivity;
import stpaulsmarthom.com.stpaulsmarthom.Activities.ParishMemberDetailList;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishMemberModel;
import stpaulsmarthom.com.stpaulsmarthom.R;

import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_IMAGE;

/**
 * Created by Belal on 10/18/2017.
 */

public class ParishMemberAdapter extends RecyclerView.Adapter<ParishMemberAdapter.MyViewHolder> {

    private List<ParishMemberModel> parishMemberModelList;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_parish_name,tv_parish_email,tv_parish_phone,tv_parish_age;
        public ImageView iv_parish;
        public ImageView iv_parish_next;
        public LinearLayout ll_parish;

        public MyViewHolder(View view) {
            super(view);
            tv_parish_name = (TextView) view.findViewById(R.id.tv_parish_name);
            tv_parish_email = (TextView) view.findViewById(R.id.tv_parish_email);
            tv_parish_phone = (TextView) view.findViewById(R.id.tv_parish_phone);
            tv_parish_age = (TextView) view.findViewById(R.id.tv_parish_age);
            iv_parish = (ImageView) view.findViewById(R.id.iv_parish);
            iv_parish_next = (ImageView) view.findViewById(R.id.iv_parish_next);
            ll_parish = view.findViewById(R.id.ll_parish);
        }
    }


    public ParishMemberAdapter(List<ParishMemberModel> parishMemberModelList, Context context) {
        this.context = context;
        this.parishMemberModelList = parishMemberModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parish_member_item, parent, false);

        return new MyViewHolder(itemView);
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
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ParishMemberModel parish = parishMemberModelList.get(position);
        holder.tv_parish_name.setText(parish.getMembername());
        holder.tv_parish_email.setText(parish.getMailingadress());
        holder.tv_parish_age.setText(parish.getMembernamerollno());
        holder.tv_parish_phone.setText(parish.getTelephonemob1());


//        if(position % 2==0)
//            holder.ll_parish.setBackgroundColor(Color.parseColor("#ececec"));
//        else
//            holder.ll_parish.setBackgroundColor(Color.parseColor("#FFFFFF"));


//        holder.tv_parish_name.setAllCaps(true);
        //holder.tv_parish_name.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        final String imageUrl= "http://stpaulsmarthomabahrain.com/membershipform/"+parish.getMemberimagepath();
       // if (isConnected(context)) {
//            Picasso.with(context)
//                    .load(imageUrl)
//                    .placeholder(R.drawable.sample)
//                    .error(R.drawable.sample)
//                    .into(holder.iv_parish);

        Picasso picasso = Picasso.with(context);
        picasso.setIndicatorsEnabled(false);
        picasso.load(imageUrl)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.iv_parish, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(context)
                                    .load(imageUrl)
                                    .error(R.drawable.sample)
                                    .into(holder.iv_parish, new Callback() {
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
       // }
//        else
//        {
//            Picasso.with(context)
//                    .load(imageUrl)
//                    .networkPolicy(NetworkPolicy.OFFLINE)
//                    .into(holder.iv_parish, new Callback() {
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
//                                    .into(holder.iv_parish, new Callback() {
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
////            File imgFile = new  File(parish.getMemberimagepath());
////            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
////            holder.iv_parish.setImageBitmap(myBitmap);
//        }
        holder.tv_parish_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent testIntent = new Intent(Intent.ACTION_SENDTO);
                testIntent.setData(Uri.parse("mailto:"+parish.getMailingadress()));
                context.startActivity(testIntent);
            }
        });

        holder.tv_parish_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+parish.getTelephone()));
                context.startActivity(intent);
            }
        });
        holder.iv_parish_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent parishDetailIntent = new Intent(context, ParishMemberDetailList.class);
                parishDetailIntent.putExtra("parish_member_name",parish.getMembername());
                parishDetailIntent.putExtra("parish_roll_no",parish.getMembernamerollno());
                parishDetailIntent.putExtra("parish_area",parish.getMembernamearea());
                parishDetailIntent.putExtra("parish_years_bah",parish.getMemberyears());
                parishDetailIntent.putExtra("parish_member_blood",parish.getMemberblood());
                parishDetailIntent.putExtra("parish_marital_status",parish.getMembermarital());
                parishDetailIntent.putExtra("parish_dom",parish.getMemberdatemarriage());
                parishDetailIntent.putExtra("parish_dob",parish.getMemberdate());
                parishDetailIntent.putExtra("parish_email",parish.getMailingadress());
                parishDetailIntent.putExtra("parish_company",parish.getCompanyname());
                parishDetailIntent.putExtra("parish_tel_res",parish.getTelephoneres1());
                parishDetailIntent.putExtra("parish_tel_mobile",parish.getTelephonemob1());
                parishDetailIntent.putExtra("parish_fax",parish.getFax());
                parishDetailIntent.putExtra("parish_home",parish.getHomeparirsh());
                parishDetailIntent.putExtra("parish_flat",parish.getFlatno());
                parishDetailIntent.putExtra("parish_bldg",parish.getBlgdno());
                parishDetailIntent.putExtra("parish_area_bah",parish.getArea());
                parishDetailIntent.putExtra("parish_road",parish.getRoad());
                parishDetailIntent.putExtra("parish_block",parish.getBlock());
                parishDetailIntent.putExtra("parish_tel_res_bah",parish.getTelephoneres2());
                parishDetailIntent.putExtra("parish_tel_mob_bah",parish.getTelephonemob2());
                parishDetailIntent.putExtra("parish_house_ind",parish.getHousenameindia());
                parishDetailIntent.putExtra("parish_town",parish.getTownindia());
                parishDetailIntent.putExtra("parish_district",parish.getDistrictindia());
                parishDetailIntent.putExtra("parish_pin",parish.getPinindia());
                parishDetailIntent.putExtra("parish_state",parish.getStateindia());
                parishDetailIntent.putExtra("parish_tel_code",parish.getTelcodeindia());
                parishDetailIntent.putExtra("parish_mobile",parish.getTelephonemob3());
                parishDetailIntent.putExtra("parish_wife_name",parish.getWifename());
                parishDetailIntent.putExtra("parish_dob_wife",parish.getDatebrithfamily());
                parishDetailIntent.putExtra("parish_emplr_name",parish.getEmaploynamefamily());
                parishDetailIntent.putExtra("parish_blood_wife",parish.getBloodfamily());
                parishDetailIntent.putExtra("parish_wife_employee",parish.getEmployefamily());
                parishDetailIntent.putExtra("parish_wife_in_bah",parish.getInbaharahianfamily());
                parishDetailIntent.putExtra("parish_emer_tel_res",parish.getTelephoneres3());
                parishDetailIntent.putExtra("parish_emer_mobile",parish.getTelephonemob3());
                parishDetailIntent.putExtra("parish_tel_off",parish.getTelephone());
                parishDetailIntent.putExtra("parish_emer_contact",parish.getEmergencyname());
                parishDetailIntent.putExtra("parish_emer_contact_india",parish.getEmergencyindianame());
                parishDetailIntent.putExtra("parish_po",parish.getPostofficeindia());
                parishDetailIntent.putExtra("parish_native",parish.getNativefamily());
                parishDetailIntent.putExtra("iv_parish_member_detail",parish.getMemberimagepath());
                parishDetailIntent.putExtra("parish_member_wife",parish.getWife_image());
                context.startActivity(parishDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parishMemberModelList.size();
    }
}