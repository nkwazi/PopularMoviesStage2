package ch.nkwazi.popularmoviesstage2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nkwazi on 29.01.19.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private Context context;
    public List<Trailer> trailerList;

    public static final String YOUTUBE_THUMB__URL = "https://img.youtube.com/vi/";

    public TrailerAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trailer_detail, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer trailerItem = trailerList.get(position);
        Uri thumbUrl = Uri.parse(YOUTUBE_THUMB__URL).buildUpon()
                .appendPath(trailerItem.getKey()).appendPath("hqdefault.jpg")
                .build();
        Picasso.get().load(thumbUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (null == trailerList){
            return 0;
        }
        return trailerList.size();
    }

    public void setItems(List<Trailer> items) {
        trailerList = new ArrayList<>();
        this.trailerList.addAll(items);
        notifyDataSetChanged();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trailer_rv)
        ImageView imageView;

        TrailerViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Trailer trailer = trailerList.get(position);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
            v.getContext().startActivity(intent);
        }
    }
}
