package ch.nkwazi.popularmoviesstage2.utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ch.nkwazi.popularmoviesstage2.model.Movie;
import ch.nkwazi.popularmoviesstage2.model.Review;

public class JsonUtils {

    private static final String MOVIE_TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String OVERVIEW = "overview";
    private static final String USER_RATING = "vote_average";
    private static final String RELEASE_DATE = "release_date";
    private static final String IMAGE_URL = "http://image.tmdb.org/t/p/w342/";
    private static final String MOVIE_ID = "id";
    private static final String MOVIE_TRAILER_KEY = "key";
    private static final String MOVIE_REVIEW_AUTHOR = "author";
    private static final String MOVIE_REVIEW_CONTENT = "content";

    public static ArrayList<Movie> parseMovieJson(String json) {
        ArrayList<Movie> movieList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++ ){

                JSONObject movieInfo = jsonArray.getJSONObject(i);

                String movieTitle = movieInfo.getString(MOVIE_TITLE);
                String movieReleaseDate = movieInfo.getString(RELEASE_DATE);
                String moviePoster = IMAGE_URL + movieInfo.getString(POSTER_PATH);
                String movieBackdrop = IMAGE_URL + movieInfo.getString(BACKDROP_PATH);
                String movieRating = movieInfo.getString(USER_RATING);
                String movieOverview = movieInfo.getString(OVERVIEW);
                int movieId = movieInfo.getInt(MOVIE_ID);

                movieList.add(new Movie(movieId, movieTitle, moviePoster, movieBackdrop, movieRating, movieReleaseDate, movieOverview));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movieList;

    }

    public static ArrayList<String> parseTrailerJson(String json){
        ArrayList<String> trailers = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++ ){

                JSONObject movieTrailer = jsonArray.getJSONObject(i);
                String key = movieTrailer.getString(MOVIE_TRAILER_KEY);

                trailers.add(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailers;
    }

    public static ArrayList<Review> parseReviewJson(String json){
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++ ){

                JSONObject movieReviews = jsonArray.getJSONObject(i);
                String author = movieReviews.getString(MOVIE_REVIEW_AUTHOR);
                String content = movieReviews.getString(MOVIE_REVIEW_CONTENT);

                reviews.add(new Review(author, content));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }

}