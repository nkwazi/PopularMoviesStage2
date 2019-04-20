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

    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context mContext, List<Movie> movieList) {
        this.context = mContext;
        this.movieList = movieList;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public final ImageView myImageView;

        MovieViewHolder(View view){
            super(view);
            myImageView = view.findViewById(R.id.coverPicture);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(v.getContext(), DetailActivity.class);

                        Movie currentMovie = movieList.get(pos);

                        intent.putExtra(Movie.TAG, currentMovie);
                        intent.putExtra(DetailActivity.TAG, currentMovie);

                        v.getContext().startActivity(intent);
                    }
                }
            });
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

    public void setMovieList(List<Movie> movie) {
        movieList = movie;
        notifyDataSetChanged();
    }
}

