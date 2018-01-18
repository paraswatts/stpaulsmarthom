package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

import stpaulsmarthom.com.stpaulsmarthom.R;

public class PdfViewerActivityStatic extends AppCompatActivity implements View.OnClickListener ,View.OnTouchListener{

    PDFView pdfView;
    TextView tv_current_page,tv_total_page;
    EditText et_pdf_pageno;

    ImageView bt_pageno;
    RelativeLayout ll1;
    RelativeLayout rl_pdf;
    boolean gone = true;
    ImageView search;
    static int total_pages;
    public static final String SAMPLE_FILE = "verses.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer_static);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#7ecce2"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#7ecce2"));
            getWindow().setNavigationBarColor(Color.parseColor("#7ecce2"));
        }

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout_pdf_static);

        TextView myActionBarTitle;
        myActionBarTitle = (TextView)findViewById(R.id.myActionBarTitle);
        myActionBarTitle.setText(getIntent().getStringExtra("pdfName"));
       // search =  findViewById(R.id.search);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7ecce2")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(getIntent().getStringExtra("pdfName"));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PdfViewerActivityStatic.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            }
        });
        ll1 = findViewById(R.id.ll1);
       // et_pdf_pageno = findViewById(R.id.et_pdf_pageno);
        //bt_pageno = findViewById(R.id.bt_pageno);
        rl_pdf = findViewById(R.id.rl_pdf);
        //tv_total_page = findViewById(R.id.tv_total_page);
        //tv_current_page = findViewById(R.id.tv_current_page);
        final String pdfurl = getIntent().getStringExtra("pdfUrl");

        final ProgressDialog  progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        pdfView= (PDFView)findViewById(R.id.pdfView);

        try {
            pdfView.fromAsset(getIntent().getStringExtra("pdfFile")).defaultPage(1)
                    .enableSwipe(true)
                    .pageFitPolicy(FitPolicy.WIDTH)


//                    onPageChange(new OnPageChangeListener() {
//                        @Override
//                        public void onPageChanged(int page, int pageCount) {
//                            // tv_current_page.setText(page);
//                            // tv_total_page.setText(page);
//                            tv_current_page.setText(String.valueOf(page));
//                            tv_total_page.setText(String.valueOf(pageCount));
//                            total_pages = pageCount;
//
//                        }
//                    }).
                    .onLoad(new OnLoadCompleteListener() {
                        @Override
                        public void loadComplete(int nbPages) {
                            progressDialog.dismiss();
                        }
                    })
                    .onError(new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            t.printStackTrace();
                            //Log.e("Error","Opening pdf" + pdfurl);
                        }
                    })
                    .load();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //

       // bt_pageno.setOnClickListener(this);

//        search.setOnTouchListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bt_pageno)
        {
            if(Integer.parseInt(et_pdf_pageno.getText().toString().trim())> total_pages)
            {
                et_pdf_pageno.requestFocus();

                Toast.makeText(PdfViewerActivityStatic.this, "Please Enter a valid page no", Toast.LENGTH_SHORT).show();
                //Sna.setError("Please Enter a valid page no");
            }
            else {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_pdf_pageno.getWindowToken(), 0);
                pdfView.jumpTo(Integer.parseInt(et_pdf_pageno.getText().toString().trim()));
            }
        }
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Log.e("Ontouch","Ontouch");
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(!gone){
                        //ll1.setVisibility(View.GONE);
                        ll1.animate().alpha(0.0f);
                        et_pdf_pageno.setText("");
                        gone = true;
                    }else{
                        ll1.setVisibility(View.VISIBLE);
                        et_pdf_pageno.setText("");
                        ll1.animate().alpha(1.0f);

                        gone = false;

                    }


                }
                return true;
            }

}
