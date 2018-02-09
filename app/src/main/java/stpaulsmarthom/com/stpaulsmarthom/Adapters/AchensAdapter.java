package stpaulsmarthom.com.stpaulsmarthom.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import stpaulsmarthom.com.stpaulsmarthom.Activities.PdfViewerActivity;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.AchensModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishBulletinModel;
import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by moody on 24/12/17.
 */

public class AchensAdapter extends  BaseAdapter {

    SharedPreferences preferences;
    private List<AchensModel> parishList;
    LinearLayout parish_item_pdf;
    TextView parish_pdf_name;
    private final Context mContext;

    // 1
    public AchensAdapter(List<AchensModel> parishList, Context context) {
        this.mContext = context;
        this.parishList = parishList;
    }


    // 2
    @Override
    public int getCount() {
        return parishList.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        final LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.archen, null);



        } else {
            grid = (View) convertView;
        }

        final AchensModel parish = parishList.get(position);
        preferences = mContext.getSharedPreferences("PdfAchens",Context.MODE_PRIVATE);
        parish_pdf_name = (TextView)grid.findViewById(R.id.parish_item_name);

        parish_pdf_name.setText(parish.getName());

        parish_item_pdf = (LinearLayout)grid.findViewById(R.id.parish_pdf_item);

        parish_item_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isConnected(mContext)) {
                    String downloaded = preferences.getString(parish.getImage_path(),"");
                    if(downloaded.equals("yes"))
                    {
                        view(parish.getImage_path(), parish.getName());

                    }
                    else {
                        getOutputMediaFile(parish.getImage_path(), parish.getName());
                    }
                    //getOutputMediaFile(parish.getImage_path(), parish.getName());
                }
                else
                {
                    Toast.makeText(mContext, "Oops no internet connection", Toast.LENGTH_SHORT).show();
                }

                // view(parish.getName());

//                    Intent intent = new Intent(mContext, PdfViewerActivity.class);
//                    intent.putExtra("pdfUrl","http://stpaulsmarthomabahrain.com/membershipform/church_admin/publication_pdf/" +parish.getFile_name());
//                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://stpaulsmarthomabahrain.com/membershipform/church_admin/publication_pdf/" +parish.getFile_name())));
                //mContext.startActivity(intent);
            }
        });

        return grid;
    }

    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    public void view(String filename,String pdfname)
    {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/StPaulsBahrain/" + "/Achens_Letter/"+filename);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        //Log.e("pdf path",pdfFile.getAbsolutePath());

                            Intent intent = new Intent(mContext, PdfViewerActivity.class);
                    intent.putExtra("pdfUrl", pdfFile.getAbsolutePath());
                    intent.putExtra("pdfName",pdfname);
                    //mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://stpaulsmarthomabahrain.com/membershipform/church_admin/publication_pdf/" +parish.getFile_name())));
        mContext.startActivity(intent);
    }

    private class DownloadFile extends AsyncTask<String, String, ArrayList<String>> {
        ProgressDialog progressDialog=null;
        private static final int  MEGABYTE = 1024 * 1024;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Downloading PDF");
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(true);
            progressDialog.show();
            super.onPreExecute();

        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            //Log.e("File download",fileUrl+fileName);
            File file = new File(strings[1]);

//            File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"Parish_PDF");
//
//            if (!sampleDir.exists()) {
//                if (!sampleDir.mkdirs()) {
//                    return null;
//                }
//            }
//
//            File pdfFile = new File(sampleDir, fileName);
//
//            try{
//                pdfFile.createNewFile();
//                Log.e("Pdf File Path",pdfFile.getAbsolutePath());
//            }catch (IOException e){
//                e.printStackTrace();
//            }
            //FileDownloader.downloadFile(fileUrl, file);
            int totalSize=0;

            try {

                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                totalSize = urlConnection.getContentLength();
                long total = 0;
                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while((bufferLength = inputStream.read(buffer))>0 ){
                    total += bufferLength;
                    publishProgress(""+(int)((total*100)/totalSize));
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<String> arrayList =  new ArrayList<>();
            arrayList.add(strings[3]);
            arrayList.add(strings[2]);
            arrayList.add(String.valueOf(totalSize));
            return arrayList;
        }
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(ArrayList<String> aVoid) {
           // progressDialog.dismiss();
          //  SaveShared(aVoid.get(0),"yes");
            super.onPostExecute(aVoid);
          //  view(aVoid.get(0),aVoid.get(1));

            File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"Achens_Letter/"+aVoid.get(0));
            int size =  Integer.parseInt(String.valueOf(sampleDir.length()));
            //Log.e("Size of file",size +"ds" + aVoid.get(2));
            progressDialog.dismiss();
            //Log.e("Files download",""+ aVoid.get(0)+aVoid.get(1));
            if(String.valueOf(size).equals(aVoid.get(2))) {
                view(aVoid.get(0),aVoid.get(1));
                SaveShared( "yes",aVoid.get(0));
            }else{
                SaveShared(aVoid.get(0), "no");
            }

        }
    }

    private void getOutputMediaFile(String name,String filename) {

        File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"Achens_Letter");
        if (!sampleDir.exists()) {
            if (!sampleDir.mkdirs()) {
                return;
            }
        }
        File mediaFile;
        String mPdfName = name;
        mediaFile = new File(sampleDir.getPath() + File.separator + mPdfName);
//        if(mediaFile.exists())
//        {
//            SaveShared(name,filename);
//            view(name,filename);
//
//        }
        //else
    //    {
            DbHelper dbHelper = new DbHelper(mContext);
            //Log.e("File name","before db"+filename);
            SaveShared(name,filename);
            //dbHelper.updatePublications(filename,filename);
            new DownloadFile().execute("http://stpaulsmarthomabahrain.com/membershipform/church_admin/achens_letter/"+name, mediaFile.getAbsolutePath(),filename,name);

      //  }
    }

    public void SaveShared(String value,String key)
    {
        //Log.e("Key Name",key + "value"+value);
        SharedPreferences preferences = mContext.getSharedPreferences("PdfAchens",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key,value);
        editor.commit();
    }

}
