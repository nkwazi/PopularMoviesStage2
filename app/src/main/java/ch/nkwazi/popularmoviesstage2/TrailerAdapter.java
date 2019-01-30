package ch.nkwazi.popularmoviesstage2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nkwazi on 29.01.19.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    public TrailerAdapterOnClickHandler trailerAdapterOnClickListener;
    private List<Trailer> trailerList;

    public interface TrailerAdapterOnClickHandler {
        void onClick(Trailer trailer);
    }

    TrailerAdapter(TrailerAdapterOnClickHandler mClickHandler) {
        this.trailerAdapterOnClickListener = mClickHandler;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView imageView;

        TrailerViewHolder(View view){
            super(view);
            imageView = view.findViewById(R.id.trailer_play_button);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
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
}
