package stpaulsmarthom.com.stpaulsmarthom.CursorAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import stpaulsmarthom.com.stpaulsmarthom.Activities.PdfViewerActivity;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by paras on 13-05-2017.
 */

public class AchensCursorAdapter extends CursorAdapter {
    private SparseBooleanArray mSelectedItemsIds;
    public TextView parish_item_name;
    LinearLayout parish_item_pdf;
    Context mContext;

    public AchensCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
        mContext = context;
        mSelectedItemsIds = new SparseBooleanArray();
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.parish_item_layout, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        parish_item_name = (TextView) view.findViewById(R.id.parish_item_name);

        //Log.e("I am in cursor adapter","");
        final int pdfNameIndex = cursor.getColumnIndex(DbContract.DbEntry.ACHENS_PDF_NAME);
        final int pdfPathIndex = cursor.getColumnIndex(DbContract.DbEntry.ACHENS_PDF_PATH);

        final int pdfIndex = cursor.getColumnIndex(DbContract.DbEntry.ACHENS_PDF_IMAGE);

        //final String pathPdf = cursor.getString(pdfPathIndex);
        final String pdfName = cursor.getString(pdfNameIndex);
        final String pdf = cursor.getString(pdfIndex);
        parish_item_pdf = view.findViewById(R.id.parish_pdf_item);
        parish_item_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = context.getSharedPreferences("PdfAchens",Context.MODE_PRIVATE);
                String path = preferences.getString(pdfName,"");

                //Log.e("Key Name",pdfName + "value"+path);

               // Log.e("pdf path",""+pathPdf);
                    if(!TextUtils.isEmpty(path))
                    {
                        String downloaded = preferences.getString(path,"");
                        if(downloaded.equals("yes")) {
                            view(pdf,pdfName);

                        }
                        else
                        {
                            Toast.makeText(context, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();

                        }
                        //Log.e("pdf name in adapter","ds"+pathPdf);
                    }
                    else
                    {
                        Toast.makeText(context, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();

                    }

            }});



        //===========Extract Strings from columns of database


        final String ourVicarName = cursor.getString(pdfNameIndex);

        parish_item_name.setText(ourVicarName);

       // Log.e("Notification type",notificationType);
        //Log.e("USername in cursor ",notificationsUserName);


    }

            public void view(String filename,String pdfname)
            {
                File pdfFile = new File(Environment.getExternalStorageDirectory() + "/StPaulsBahrain/" + "/Parish_Publication_PDF/"+filename);  // -> filename = maven.pdf
                Uri path = Uri.fromFile(pdfFile);
                //Log.e("pdf path",pdfFile.getAbsolutePath());

                Intent intent = new Intent(mContext, PdfViewerActivity.class);
                intent.putExtra("pdfUrl", pdfFile.getAbsolutePath());
                intent.putExtra("pdfName",pdfname);
                //mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://stpaulsmarthomabahrain.com/membershipform/church_admin/publication_pdf/" +parish.getFile_name())));
                mContext.startActivity(intent);
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
