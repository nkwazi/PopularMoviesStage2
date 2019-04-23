package ch.nkwazi.popularmoviesstage2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.nkwazi.popularmoviesstage2.api.ApiResponse;
import ch.nkwazi.popularmoviesstage2.api.RetrofitClient;
import ch.nkwazi.popularmoviesstage2.api.Service;
import ch.nkwazi.popularmoviesstage2.data.MovieContract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

    @BindView(R.id.rv_review)
    RecyclerView review_rv;

    @BindView(R.id.fav_button)
    ImageButton favButton;

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private Movie movie;
    private Boolean isMovieFavorited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        movie = intent.getParcelableExtra(Movie.TAG);

        populateUI();
        populateTrailers(savedInstanceState);
        populateReviews(savedInstanceState);
        handleFavoriteButton();
    }

    public void handleFavoriteButton() {
        final ImageButton favButton = findViewById(R.id.fav_button);
        isMovieFavorited  = isMovieInDatabase(movie.movieId);
        if(isMovieFavorited){
            favButton.setImageResource(R.drawable.baseline_favorite_black_18dp);
        } else {
            favButton.setImageResource(R.drawable.baseline_favorite_black_18dp);
        }
        favButton.setOnClickListener(v -> {

            boolean isFavourite = isMovieInDatabase(movie.movieId);
            if(isFavourite){
                getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI.buildUpon().appendPath(movie.movieId).build(),null,null);
                favButton.setImageResource(R.drawable.baseline_favorite_black_18dp);
                Toast.makeText(getApplicationContext(),"Movie Removed From Favourites :(" ,Toast.LENGTH_SHORT).show();
            }
            else
            {
                saveMovieToDatabase();
                favButton.setImageResource(R.drawable.baseline_favorite_black_18dp);
            }
        });
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

    private void populateTrailers(Bundle savedInstance){

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailer_rv.setLayoutManager(layoutManager);
        trailer_rv.setHasFixedSize(true);

        trailerAdapter = new TrailerAdapter(this);
        trailer_rv.setAdapter(trailerAdapter);

        loadTrailerData(savedInstance);
    }

    private void loadTrailerData(Bundle savedInstance){
        if (savedInstance != null && savedInstance.containsKey(Movie.TAG)) {
            trailerAdapter.setItems(savedInstance.getParcelableArrayList(Movie.TAG));
        } else {
            Service apiService = RetrofitClient.getClient().create(Service.class);

            //TODO replace this in the model
            int numb = Integer.parseInt(movie.movieId);

            Call<ApiResponse<Trailer>> call  = apiService.getMovieTrailers(numb, BuildConfig.API_KEY);

            call.enqueue(new Callback<ApiResponse<Trailer>>() {
                @Override
                public void onResponse(Call<ApiResponse<Trailer>> call, Response<ApiResponse<Trailer>> response) {
                    if (response.isSuccessful()) {
                        List<Trailer> trailers = response.body().results;
                        if (trailers.isEmpty()){
                            trailer_rv.setVisibility(View.GONE);
                        }
                        trailerAdapter.setItems(trailers);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<Trailer>> call, Throwable t) {
                    trailer_rv.setVisibility(View.GONE);
                }
            });
        }
    }

    private void populateReviews(Bundle savedInstance) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        review_rv.setLayoutManager(layoutManager);
        review_rv.setHasFixedSize(true);

        reviewAdapter = new ReviewAdapter(this);
        review_rv.setAdapter(reviewAdapter);

        loadReviewData(savedInstance);
    }

    private void loadReviewData(Bundle savedInstance) {
        if (savedInstance != null && savedInstance.containsKey(Movie.TAG)) {
            reviewAdapter.setItems(savedInstance.getParcelableArrayList(Movie.TAG));
        } else {
            Service apiService = RetrofitClient.getClient().create(Service.class);

            //TODO replace this in the model
            int id = Integer.parseInt(movie.movieId);

            Call<ApiResponse<Review>> call  = apiService.getMovieReviews(id, BuildConfig.API_KEY);

            call.enqueue(new Callback<ApiResponse<Review>>() {
                @Override
                public void onResponse(Call<ApiResponse<Review>> call, Response<ApiResponse<Review>> response) {
                    if (response.isSuccessful()) {
                        List<Review> reviews = response.body().results;
                        reviewAdapter.setItems(reviews);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<Review>> call, Throwable t) {

                }
            });
        }
    }

    private void saveMovieToDatabase(){
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_TMDB_ID,movie.movieId);
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE,movie.movieTitle);
        values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH,movie.moviePosterPath);
        values.put(MovieContract.MovieEntry.COLUMN_USER_RATING,movie.movieRating);
        values.put(MovieContract.MovieEntry.COLUMN_PLOT_SYNOPSIS,movie.movieOverview);
        values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE,movie.movieReleaseDate);
        Uri newUri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,values);
        if(newUri != null){
            Toast.makeText(this,"Movie saved to favourites <3",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isMovieInDatabase(String id){
        String[] projection = {

                MovieContract.MovieEntry.COLUMN_TMDB_ID,
                MovieContract.MovieEntry.COLUMN_MOVIE_TITLE,
                MovieContract.MovieEntry.COLUMN_POSTER_PATH,
                MovieContract.MovieEntry.COLUMN_USER_RATING,
                MovieContract.MovieEntry.COLUMN_PLOT_SYNOPSIS,
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE};

        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,projection,
                MovieContract.MovieEntry.COLUMN_TMDB_ID + "= '"+id+"'",null,null);
        if (cursor != null){
            if (cursor.getCount()>0){
                return true;
            }
            cursor.close();
        }
        return false;
    }

}
