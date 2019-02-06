package ch.nkwazi.popularmoviesstage2.api;

import ch.nkwazi.popularmoviesstage2.Movie;
import ch.nkwazi.popularmoviesstage2.Review;
import ch.nkwazi.popularmoviesstage2.Trailer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nkwazi on 06.02.19.
 */

public interface Service {

    @GET("movie/popular")
    Call<ApiResponse<Movie>> getPopularMovies(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<ApiResponse<Movie>> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("movie/{id}/videos")
    Call<ApiResponse<Trailer>> getMovieTrailers(@Path("id") int id, @Query("api_key") String api_key);

    @GET("movie/{id}/reviews")
    Call<ApiResponse<Review>> getMovieReviews(@Path("id") int id, @Query("api_key") String api_key);

}
