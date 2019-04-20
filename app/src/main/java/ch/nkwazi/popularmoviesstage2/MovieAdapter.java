package ch.nkwazi.popularmoviesstage2;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

    private final Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context mContext, List<Movie> movieList) {
        this.context = mContext;
        this.movieList = movieList;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        final ImageView myImageView;

        MovieViewHolder(View view){
            super(view);
            myImageView = view.findViewById(R.id.coverPicture);
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_overview, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String image = movieList.get(position).moviePosterPath;
        Picasso.get().load("http://image.tmdb.org/t/p/w780".concat(image)).into(holder.myImageView);

        holder.myImageView.setOnClickListener(v -> {
            Movie currentMovie = movieList.get(position);
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(Movie.TAG, currentMovie);
            intent.putExtra(DetailActivity.TAG, currentMovie);
            context.startActivity(intent);
            });
    }

    @Override
    public int getItemCount() {
        if (null == movieList){ return 0; }
        return movieList.size();
    }
}

