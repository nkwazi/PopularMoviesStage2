package ch.nkwazi.popularmoviesstage2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.nkwazi.popularmoviesstage2.api.ApiResponse;
import ch.nkwazi.popularmoviesstage2.api.RetrofitClient;
import ch.nkwazi.popularmoviesstage2.api.Service;
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

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private Movie movie;

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
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        trailer_rv.setLayoutManager(layoutManager);
        trailer_rv.setHasFixedSize(true);

        trailerAdapter = new TrailerAdapter(this);
        trailer_rv.setAdapter(trailerAdapter);

        loadTrailerData(savedInstance);
    }

    private void loadTrailerData(Bundle savedInstance){
        if (savedInstance != null && savedInstance.containsKey(Movie.TAG)) {
            trailerAdapter.setItems(savedInstance.<Trailer>getParcelableArrayList(Movie.TAG));
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
                        trailerAdapter.setItems(trailers);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<Trailer>> call, Throwable t) {

                }
            });
        }
    }

    private void populateReviews(Bundle savedInstance) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        review_rv.setLayoutManager(layoutManager);
        review_rv.setHasFixedSize(true);

        reviewAdapter = new ReviewAdapter(this);
        review_rv.setAdapter(reviewAdapter);

        loadReviewData(savedInstance);
    }

    private void loadReviewData(Bundle savedInstance) {
        if (savedInstance != null && savedInstance.containsKey(Movie.TAG)) {
            reviewAdapter.setItems(savedInstance.<Review>getParcelableArrayList(Movie.TAG));
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

}
