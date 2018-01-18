package stpaulsmarthom.com.stpaulsmarthom.CursorAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.File;

import stpaulsmarthom.com.stpaulsmarthom.Activities.BishopDetailActivity;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.R;

import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.COLUMN_VICAR_TITLE;

/**
 * Created by paras on 13-05-2017.
 */

public class OurVicarCursorAdapter extends CursorAdapter {
    private SparseBooleanArray mSelectedItemsIds;
    public WebView tv_our_vicar;
    public TextView tv_vicar_title;
    public ImageView iv_our_vicar;
    public SeekBar mSeekBar;
    public OurVicarCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
        mSelectedItemsIds = new SparseBooleanArray();
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.vicar_layout, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        tv_our_vicar =  view.findViewById(R.id.tv_our_vicar);
        iv_our_vicar = (ImageView) view.findViewById(R.id.iv_our_vicar);
        tv_vicar_title =  view.findViewById(R.id.tv_vicar_title);
        mSeekBar = view.findViewById(R.id.seek_bar);
        //Log.e("I am in cursor adapter","");
        int ourVicarIndex = cursor.getColumnIndex(DbContract.DbEntry.COLUMN_OUR_VICAR);
        int vicarImageIndex = cursor.getColumnIndex(DbContract.DbEntry.COLUMN_VICAR_IMAGE);



        tv_vicar_title.setText(cursor.getString(cursor.getColumnIndex(COLUMN_VICAR_TITLE)));

        //===========Extract Strings from columns of database


        final String ourVicarName = cursor.getString(ourVicarIndex);
        final String vicarImage = cursor.getString(vicarImageIndex);
        String text;
        text = "<html><body  style=\"text-align:justify;\">";
        text += ourVicarName ;
        text += "</body></html>";
        tv_our_vicar.loadData(text,"text/html", "utf-8");
        File imgFile = new  File(vicarImage);
        Log.e("Image vicar",imgFile.getAbsolutePath());
        if(imgFile.getAbsolutePath().toLowerCase().endsWith(".jpeg")||imgFile.getAbsolutePath().toLowerCase().endsWith(".jpg")) {
            Log.e("Image vicar3",imgFile.getAbsolutePath());

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            iv_our_vicar.setImageBitmap(myBitmap);

        }

        else {
            iv_our_vicar.setImageDrawable(context.getResources().getDrawable(R.drawable.sample));
            Log.e("Image vicar2",imgFile.getAbsolutePath());

        }


        mSeekBar.setProgress(tv_our_vicar.getSettings().getTextZoom()/25);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_our_vicar.getSettings().setTextZoom(progress*25);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        tv_our_vicar.getSettings().setJavaScriptEnabled(true);
        tv_our_vicar.getSettings().setJavaScriptEnabled(true);
        tv_our_vicar.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        tv_our_vicar.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        tv_our_vicar.getSettings().setAppCacheEnabled(false);
        tv_our_vicar.getSettings().setBlockNetworkImage(true);
        tv_our_vicar.getSettings().setLoadsImagesAutomatically(true);
        tv_our_vicar.getSettings().setNeedInitialFocus(true);
        tv_our_vicar.getSettings().setGeolocationEnabled(false);
        tv_our_vicar.getSettings().setSaveFormData(false);
       // Log.e("Notification type",notificationType);
        //Log.e("USername in cursor ",notificationsUserName);


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
