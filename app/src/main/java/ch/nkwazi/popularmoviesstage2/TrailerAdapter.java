package ch.nkwazi.popularmoviesstage2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by nkwazi on 29.01.19.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private Context context;
    public List<Trailer> trailerList;

    public TrailerAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_detail, parent, false);
        return new TrailerAdapter.TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        int image = R.drawable.baseline_play_arrow_black_18dp;
        Picasso.get().load(image).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (null == trailerList){
            return 0;
        }
        return trailerList.size();
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
