package ch.nkwazi.popularmoviesstage2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

/**
 * Created by nkwazi on 29.01.19.
 */

public class DetailActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://image.tmdb.org/t/p/w500//";
    private static final String RATING = "Rating: ";
    private static final String RELEASED = "Released: ";

    public static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.detail_trailers)
    RecyclerView trailer_rv;

    private TrailerAdapter trailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        populateUI();
        //populateTrailers();
    }

    private void populateUI(){

        TextView movieTitle = findViewById(R.id.movie_title);
        TextView movieOverview = findViewById(R.id.movie_overview);
        TextView movieRating = findViewById(R.id.movie_rating);
        TextView movieReleaseDate = findViewById(R.id.movie_release_date);
        ImageView moviePicture = findViewById(R.id.movie_picture);

        Intent intent = getIntent();

        Movie movie = intent.getParcelableExtra(Movie.TAG);

        movieTitle.setText(movie.movieTitle);
        movieOverview.setText(movie.movieOverview);
        movieRating.setText(RATING + movie.movieRating);
        movieReleaseDate.setText(RELEASED + movie.movieReleaseDate);

        Picasso.get().load(BASE_URL + movie.moviePosterPath).into(moviePicture);
    }

    private void populateTrailers(){

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailer_rv.setLayoutManager(layoutManager);
        trailer_rv.setHasFixedSize(true);

        trailerAdapter = new TrailerAdapter(this);
        trailer_rv.setAdapter(trailerAdapter);

        loadTrailerData();
    }

    private void loadTrailerData(){
        new FetchTrailerTask().execute();
    }

    class FetchTrailerTask extends AsyncTask<String, Void, List<Trailer>>{


        @Override
        protected List<Trailer> doInBackground(String... strings) {
            return null;
        }
    }
}
