package ch.nkwazi.popularmoviesstage2;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.nkwazi.popularmoviesstage2.adapter.ReviewAdapter;
import ch.nkwazi.popularmoviesstage2.adapter.TrailerAdapter;
import ch.nkwazi.popularmoviesstage2.data.AppDatabase;
import ch.nkwazi.popularmoviesstage2.model.Movie;
import ch.nkwazi.popularmoviesstage2.model.Review;

import static ch.nkwazi.popularmoviesstage2.utils.JsonUtils.parseReviewJson;
import static ch.nkwazi.popularmoviesstage2.utils.JsonUtils.parseTrailerJson;
import static ch.nkwazi.popularmoviesstage2.utils.NetworkUtils.buildReviewUrl;
import static ch.nkwazi.popularmoviesstage2.utils.NetworkUtils.buildTrailerUrl;
import static ch.nkwazi.popularmoviesstage2.utils.NetworkUtils.makeHttpRequest;

/**
 * Created by nkwazi on 29.01.19.
 */

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.OnTrailerListener {

    private static final String BASE_URL = "http://image.tmdb.org/t/p/w500//";
    private static final String RATING = "Rating: ";
    private static final String RELEASED = "Released: ";

    @BindView(R.id.detail_trailers)
    RecyclerView trailer_rv;

    @BindView(R.id.rv_review)
    RecyclerView review_rv;

    @BindView(R.id.fav_button)
    ImageButton favButton;

    @BindView(R.id.connection)
    TextView connection;

    @BindView(R.id.movie_title)
    TextView movieTitle;

    @BindView(R.id.movie_overview)
    TextView movieOverview;

    @BindView(R.id.movie_rating)
    TextView movieRating;

    @BindView(R.id.movie_release_date)
    TextView movieReleaseDate;

    @BindView(R.id.movie_picture)
    ImageView moviePicture;

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private Movie movie;
    private Boolean isMovieFavorited = false;
    private ArrayList<String> movieTrailer;
    private ArrayList<Review> reviewArrayList;
    FavoriteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (isOnline()) {
            connection.setVisibility(View.INVISIBLE);
        } else {
            connection.setVisibility(View.VISIBLE);
        }

        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movie");
        int movieId = movie.getMovieId();

        populateUI();

        new TrailerAsync().execute(String.valueOf(movieId));
        new ReviewAsync().execute(String.valueOf(movieId));
        new SingleMovieAsyncTask().execute(movie);

        viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);

        handleFavoriteButton();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void handleFavoriteButton() {

        favButton.setOnClickListener(v -> {
            new SingleMovieAsyncTask().execute(movie);
            isMovieFavorited = true;
        });

    }

    @Override
    public void onTrailerClick(int i) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + movieTrailer.get(i)));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + movieTrailer.get(i)));
        try {
            this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex){
            this.startActivity(webIntent);
        }
    }

    private void populateUI(){

        movieTrailer = new ArrayList<>();
        reviewArrayList = new ArrayList<>();

        trailer_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trailerAdapter = new TrailerAdapter(movieTrailer, this, this);
        trailer_rv.setAdapter(trailerAdapter);

        review_rv.setLayoutManager(new LinearLayoutManager(this));
        reviewAdapter = new ReviewAdapter(reviewArrayList, this);
        review_rv.setAdapter(reviewAdapter);

        movie = getIntent().getParcelableExtra("movie");

        movieTitle.setText(movie.getMovieTitle());
        movieOverview.setText(movie.getMovieOverview());
        movieRating.setText(RATING + movie.getMovieRating());
        movieReleaseDate.setText(RELEASED + movie.getMovieReleaseDate());

        Picasso.get()
                .load(movie.getMoviePosterPath())
                .into(moviePicture);
    }

    public class TrailerAsync extends AsyncTask<String, Void, List<String>> {

        @Override
        protected List<String> doInBackground(String... strings) {
            if (strings.length == 0 ){
                return null;
            }
            String movieId = strings[0];
            URL url = buildTrailerUrl(movieId);

            try {

                String getResponse = makeHttpRequest(url);

                return parseTrailerJson(getResponse);

            } catch (IOException e) {

                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(List<String> trailers) {
            if(trailers != null && !trailers.isEmpty()) {
                movieTrailer.clear();
                movieTrailer.addAll(trailers);
                trailerAdapter.notifyDataSetChanged();
            }
        }
    }
    public class ReviewAsync extends AsyncTask<String, Void, ArrayList<Review>> {

        @Override
        protected ArrayList<Review> doInBackground(String... strings) {
            if(strings.length == 0 ){
                return null;
            }
            String movieId = strings[0];
            URL url = buildReviewUrl(movieId);

            try {

                String getResponse = makeHttpRequest(url);

                return parseReviewJson(getResponse);

            } catch (IOException e) {

                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(ArrayList<Review> reviews) {
            if(reviews != null && !reviews.isEmpty()) {
                reviewArrayList.clear();
                reviewArrayList.addAll(reviews);
                reviewAdapter.notifyDataSetChanged();
            }
        }
    }

    private class SingleMovieAsyncTask extends AsyncTask<Movie, Void, Movie> {

        @Override
        protected Movie doInBackground(Movie... movie) {
            AppDatabase appDatabase = AppDatabase.getInstance(DetailActivity.this);
            return appDatabase.movieDao().getSingleMovie(movie[0].getMovieId());
        }

        @Override
        protected void onPostExecute(Movie list) {
            super.onPostExecute(list);
            if(isMovieFavorited){
                if (list != null) {
                    unmarkAsFavorite(movie);
                    favButton.setImageResource(R.drawable.star_unfavorited);

                } else {
                    markAsFavorite(movie);
                    favButton.setImageResource(R.drawable.star_favorite);
                }
            }
            else {
                if (list != null) {
                    favButton.setImageResource(R.drawable.star_favorite);
                } else {
                    favButton.setImageResource(R.drawable.star_unfavorited);
                }
            }
        }
    }

    private void markAsFavorite(Movie movie) {
        viewModel.insert(movie);
    }

    private void unmarkAsFavorite(Movie movie) {
        viewModel.delete(movie);
    }

}
