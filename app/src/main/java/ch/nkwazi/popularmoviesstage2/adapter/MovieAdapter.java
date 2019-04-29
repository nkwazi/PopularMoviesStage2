package ch.nkwazi.popularmoviesstage2.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ch.nkwazi.popularmoviesstage2.DetailActivity;
import ch.nkwazi.popularmoviesstage2.R;
import ch.nkwazi.popularmoviesstage2.model.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nkwazi on 29.01.19.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context mContext, List<Movie> movieList) {
        this.context = mContext;
        this.movieList = movieList;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.coverPicture)
        ImageView myImageView;

        MovieViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_overview, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Picasso.get()
                .load(movieList.get(position).getMoviePosterPath())
                .into(holder.myImageView);

        holder.myImageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("movie", movieList.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return movieList.size(); }

    public void setMovieList(List<Movie> movie) {
        this.movieList = movie;
        notifyDataSetChanged();
    }

}

