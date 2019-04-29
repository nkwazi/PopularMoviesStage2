package ch.nkwazi.popularmoviesstage2.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.nkwazi.popularmoviesstage2.R;

/**
 * Created by nkwazi on 29.01.19.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    Context context;
    private List<String> trailerList;
    private OnTrailerListener onTrailerListener;

    private static final String YOUTUBE_THUMB__URL = "https://img.youtube.com/vi/";

    public TrailerAdapter(List<String> trailers, Context context, OnTrailerListener onTrailerListener) {
        this.trailerList = trailers;
        this.context = context;
        this.onTrailerListener = onTrailerListener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_detail, parent, false);
        return new TrailerViewHolder(view, onTrailerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Uri thumbUrl = Uri.parse(YOUTUBE_THUMB__URL).buildUpon()
                .appendPath(trailerList.get(position)).appendPath("hqdefault.jpg")
                .build();
        Picasso.get().load(thumbUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() { return trailerList.size(); }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnTrailerListener onTrailerListener;

        @BindView(R.id.trailer_rv)
        ImageView imageView;

        TrailerViewHolder(@NonNull View view, OnTrailerListener onTrailerListener){
            super(view);
            ButterKnife.bind(this, view);
            this.onTrailerListener = onTrailerListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTrailerListener.onTrailerClick(getAdapterPosition());
        }
    }

    public interface OnTrailerListener {
        void onTrailerClick(int position);
    }

}
