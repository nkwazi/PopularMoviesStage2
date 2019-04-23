package ch.nkwazi.popularmoviesstage2.data;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ch.nkwazi.popularmoviesstage2.FavoriteDetailActivity;
import ch.nkwazi.popularmoviesstage2.R;

public class MovieCursorAdapter extends CursorAdapter {
    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/";
    private static final String POSTER_SIZE ="w500";

    public MovieCursorAdapter(Context context, Cursor cursor){
        super(context,cursor,0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.movie_overview,parent,false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        ImageView posterImage =  view.findViewById(R.id.movie_picture);
        int posterPathColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);


        int tmdbIdColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TMDB_ID);
        int movieTitleColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE);
        int userRatingColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_USER_RATING);
        int plotSynopsisColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_PLOT_SYNOPSIS);
        int releaseDateColumnIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE);

        final String currentTmdbId = cursor.getString(tmdbIdColumnIndex);
        final String currentMovieTitle = cursor.getString(movieTitleColumnIndex);
        final String currentMoviePosterPath = cursor.getString(posterPathColumnIndex);
        final String currentUserRating = cursor.getString(userRatingColumnIndex);
        final String currentReleaseDate = cursor.getString(releaseDateColumnIndex);
        final String currentPlotSynopsis = cursor.getString(plotSynopsisColumnIndex);


        Picasso.get().load(IMAGE_URL+POSTER_SIZE+currentMoviePosterPath).into(posterImage);
        posterImage.setOnClickListener(v -> {
            Intent detailsIntent = new Intent(context, FavoriteDetailActivity.class);
            detailsIntent.putExtra("tmdbId",currentTmdbId);
            detailsIntent.putExtra("poster path",currentMoviePosterPath);
            detailsIntent.putExtra("movie title",currentMovieTitle);
            detailsIntent.putExtra("user rating",currentUserRating);
            detailsIntent.putExtra("release date",currentReleaseDate);
            detailsIntent.putExtra("plot synopsis",currentPlotSynopsis);
            (context).startActivity(detailsIntent);

        });

    }
}

