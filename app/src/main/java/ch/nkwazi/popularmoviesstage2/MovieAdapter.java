package ch.nkwazi.popularmoviesstage2;

import android.content.Context;
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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private MovieAdapterOnClickHandler movieAdapterOnClickHandler;
    private List<Movie> movieList;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    MovieAdapter(MovieAdapterOnClickHandler mClickHandler){
        this.movieAdapterOnClickHandler = mClickHandler;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView myImageView;

        MovieViewHolder(View view){
            super(view);
            myImageView = view.findViewById(R.id.coverPicture);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie selectedMovie = movieList.get(adapterPosition);
            movieAdapterOnClickHandler.onClick(selectedMovie);
        }
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForMovie = R.layout.movie_overview;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldBeAttachedToParent = false;

        View view = inflater.inflate(layoutForMovie, parent, shouldBeAttachedToParent);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String image = movieList.get(position).moviePosterPath;
        Picasso.get().load("http://image.tmdb.org/t/p/w780".concat(image)).into(holder.myImageView);
    }

    @Override
    public int getItemCount() {
        if (null == movieList){
            return 0;
        }
        return movieList.size();
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }
}

