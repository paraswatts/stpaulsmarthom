package stpaulsmarthom.com.stpaulsmarthom.Adapters;

/**
 * Created by Paras-Android on 22-12-2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;


import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.VicarModel;
import stpaulsmarthom.com.stpaulsmarthom.R;

/**
 * Created by Belal on 10/18/2017.
 */

public class VicarAdapter extends RecyclerView.Adapter<VicarAdapter.MyViewHolder> {

    private List<VicarModel> moviesList;

    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        //public JustifyTextView tv_our_vicar;
        public ImageView iv_our_vicar;
        public TextView tv_vicar_title;
        public WebView tv_our_vicar;
        ProgressBar  vicar_image_bar;
        SeekBar mSeekBar;

        public MyViewHolder(View view) {
            super(view);
            tv_our_vicar =  view.findViewById(R.id.tv_our_vicar);
            iv_our_vicar = (ImageView) view.findViewById(R.id.iv_our_vicar);
            vicar_image_bar = view.findViewById(R.id.vicar_image_bar);
            tv_vicar_title = view.findViewById(R.id.tv_vicar_title);
            mSeekBar =  view.findViewById(R.id.seek_bar);
        }
    }


    public VicarAdapter(List<VicarModel> moviesList,Context context) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vicar_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        VicarModel movie = moviesList.get(position);
        String html = " %s ";
        String vicar_text = movie.getOur_vicar();
        String text;
        text = "<html><body  style=\"text-align:justify;\">";
        text += movie.getOur_vicar() ;
        text += "</body></html>";
        holder.tv_our_vicar.loadData(text,"text/html", "utf-8");
        //holder.tv_our_vicar.setText(movie.getOur_vicar());
        holder.tv_vicar_title.setText(movie.getVicar_title());
//        Glide.with(context)
//                .load("http://stpaulsmarthomabahrain.com/membershipform/church_admin/upload/"+movie.getImage_url())
//                .placeholder(R.drawable.sample)
//                .into(holder.iv_our_vicar);
        holder.vicar_image_bar.setVisibility(View.VISIBLE);
        holder.mSeekBar.setProgress(holder.tv_our_vicar.getSettings().getTextZoom()/25);
        holder.mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                holder.tv_our_vicar.getSettings().setTextZoom(progress*25);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        holder.tv_our_vicar.getSettings().setJavaScriptEnabled(true);
        holder.tv_our_vicar.getSettings().setJavaScriptEnabled(true);
        holder.tv_our_vicar.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        holder.tv_our_vicar.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        holder.tv_our_vicar.getSettings().setAppCacheEnabled(false);
        holder.tv_our_vicar.getSettings().setBlockNetworkImage(true);
        holder.tv_our_vicar.getSettings().setLoadsImagesAutomatically(true);
        holder.tv_our_vicar.getSettings().setNeedInitialFocus(true);
        holder.tv_our_vicar.getSettings().setGeolocationEnabled(false);
        holder.tv_our_vicar.getSettings().setSaveFormData(false);
        Picasso picasso = Picasso.with(context);
        picasso.setIndicatorsEnabled(false);
        picasso.load("http://stpaulsmarthomabahrain.com/membershipform/church_admin/upload/"+movie.getImage_url())
                .placeholder(R.drawable.sample)
                .error(R.drawable.sample)
                .into(holder.iv_our_vicar, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.vicar_image_bar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}