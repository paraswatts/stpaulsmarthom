package stpaulsmarthom.com.stpaulsmarthom.CursorAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.io.File;

import stpaulsmarthom.com.stpaulsmarthom.Activities.BishopDetailActivity;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by paras on 13-05-2017.
 */

public class OurBishopCursorAdapter extends CursorAdapter {
    private SparseBooleanArray mSelectedItemsIds;
    public TextView tv_bishop;
    public ImageView iv_bishop;
    public LinearLayout ll_bishop_detail;
    public TextView tv_bishop_dob;
    public OurBishopCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
        mSelectedItemsIds = new SparseBooleanArray();
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.bishop_layout, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        tv_bishop = view.findViewById(R.id.tv_bishop);
        iv_bishop =  view.findViewById(R.id.iv_bishop);
        ll_bishop_detail = view.findViewById(R.id.ll_bishop_detail);
        tv_bishop_dob = view.findViewById(R.id.tv_bishop_dob);
        //Log.e("I am in cursor adapter","");
        int bishopNameIndex = cursor.getColumnIndex(DbContract.DbEntry.COLUMN_BISHOP_NAME);
        int bishopPhoneIndex = cursor.getColumnIndex(DbContract.DbEntry.COLUMN_BISHOP_PHONE);
        int bishopAddressIndex = cursor.getColumnIndex(DbContract.DbEntry.COLUMN_BISHOP_ADDRESS);
        int bishopEmailIndex = cursor.getColumnIndex(DbContract.DbEntry.COLUMN_BISHOP_EMAIL);
        int bishopImageIndex = cursor.getColumnIndex(DbContract.DbEntry.COLUMN_BISHOP_IMAGE);





        //===========Extract Strings from columns of database


        final String bishopName = cursor.getString(bishopNameIndex);
        final String bishopPhone = cursor.getString(bishopPhoneIndex);
        final String bishopAddress = cursor.getString(bishopAddressIndex);
        final String bishopEmail = cursor.getString(bishopEmailIndex);
        final String bishopImage = cursor.getString(bishopImageIndex);


       // Log.e("Notification type",notificationType);
        //Log.e("USername in cursor ",notificationsUserName);

        tv_bishop.setText(bishopName);

        File imgFile = new  File(bishopImage);
        if(imgFile.getAbsolutePath().toLowerCase().endsWith(".jpg")||imgFile.getAbsolutePath().toLowerCase().endsWith(".jpgeg")) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            iv_bishop.setImageBitmap(myBitmap);
        }
        else {
            iv_bishop.setImageDrawable(context.getResources().getDrawable(R.drawable.sample));


        }
        ll_bishop_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bishopDetailIntent = new Intent(context, BishopDetailActivity.class);
                bishopDetailIntent.putExtra("bishopPhone", bishopPhone);
                bishopDetailIntent.putExtra("bishopEmail", bishopEmail);
                bishopDetailIntent.putExtra("bishopLocation", bishopAddress);
                bishopDetailIntent.putExtra("bishopImage", bishopImage);
                bishopDetailIntent.putExtra("bishopName", bishopName);
                bishopDetailIntent.putExtra("bishopDob", cursor.getString(cursor.getColumnIndex(DbContract.DbEntry.COLUMN_BISHOP_DOB)));
                context.startActivity(bishopDetailIntent);

            }}
            );
    }






    // get List after update or delete




    public void  toggleSelection(int position) {

        selectView(position, !mSelectedItemsIds.get(position));

    }



    // Remove selection after unchecked

    public void  removeSelection() {

        mSelectedItemsIds = new SparseBooleanArray();

        notifyDataSetChanged();

    }



    // Item checked on selection

    public void selectView(int position, boolean value) {

        if (value)

            mSelectedItemsIds.put(position,  value);

        else

            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();

    }



    // Get number of selected item

    public int  getSelectedCount() {

        return mSelectedItemsIds.size();

    }



    public SparseBooleanArray getSelectedIds() {

        return mSelectedItemsIds;

    }
}
