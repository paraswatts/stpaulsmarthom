package stpaulsmarthom.com.stpaulsmarthom.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;

import java.util.List;

import stpaulsmarthom.com.stpaulsmarthom.Activities.OrgChildViewHolder;
import stpaulsmarthom.com.stpaulsmarthom.Activities.SectionViewHolder;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgDetailModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.SectionHeader;
import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by Paras-Android on 27-12-2017.
 */

public class OrgSectionRecycler extends SectionRecyclerViewAdapter<SectionHeader, OrgDetailModel, SectionViewHolder, OrgChildViewHolder> {

    Context context;

    public OrgSectionRecycler(Context context, List<SectionHeader> sectionHeaderItemList) {
        super(context, sectionHeaderItemList);
        this.context = context;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_layout, sectionViewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public OrgChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.org_detail_layout, childViewGroup, false);
        return new OrgChildViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int sectionPosition, SectionHeader sectionHeader) {
        sectionViewHolder.memberType.setText(sectionHeader.getSectionText());
    }

    @Override
    public void onBindChildViewHolder(OrgChildViewHolder childViewHolder, int sectionPosition, int childPosition, final OrgDetailModel orgDetailModel) {
        //Log.e("name in adapter",""+ orgDetailModel.getName());
        childViewHolder.tv_org_name.setText(orgDetailModel.getName());
        childViewHolder.tv_org_des.setText(orgDetailModel.getDesignation());
        childViewHolder.tv_org_phone.setText(orgDetailModel.getPhone());
        childViewHolder.tv_org_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+orgDetailModel.getPhone()));
                context.startActivity(intent);
            }
        });
        if(childPosition % 2==0)
            childViewHolder.ll_org.setBackgroundColor(Color.parseColor("#ececec"));
        else
            childViewHolder.ll_org.setBackgroundColor(Color.parseColor("#FFFFFF"));    }
}