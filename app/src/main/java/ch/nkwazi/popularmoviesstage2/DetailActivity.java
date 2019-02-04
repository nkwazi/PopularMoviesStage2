package ch.nkwazi.popularmoviesstage2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by nkwazi on 29.01.19.
 */

public class DetailActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://image.tmdb.org/t/p/w500//";
    private static final String RATING = "Rating: ";
    private static final String RELEASED = "Released: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        populateUI();

    }

    private void populateUI(){

        TextView movieTitle = findViewById(R.id.movie_title);
        TextView movieOverview = findViewById(R.id.movie_overview);
        TextView movieRating = findViewById(R.id.movie_rating);
        TextView movieReleaseDate = findViewById(R.id.movie_release_date);
        ImageView moviePicture = findViewById(R.id.movie_picture);
        ImageView trailerPicture = findViewById(R.id.trailer_play_button);

        Intent intent = getIntent();

        Movie movie = intent.getParcelableExtra("movie");
        Trailer trailer = intent.getParcelableExtra("trailer");

        movieTitle.setText(movie.movieTitle);
        movieOverview.setText(movie.movieOverview);
        movieRating.setText(RATING + movie.movieRating);
        movieReleaseDate.setText(RELEASED + movie.movieReleaseDate);

        Picasso.get().load(BASE_URL + movie.moviePosterPath).into(moviePicture);
        //Picasso.get().load(R.drawable.baseline_play_arrow_black_18dp).into(trailerPicture);
    }
}
