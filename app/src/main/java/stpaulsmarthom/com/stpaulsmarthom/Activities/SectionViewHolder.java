package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by Paras-Android on 27-12-2017.
 */

public class SectionViewHolder extends RecyclerView.ViewHolder {

    public TextView memberType;
    public SectionViewHolder(View itemView) {
        super(itemView);
        memberType = (TextView) itemView.findViewById(R.id.header_id);
    }
}