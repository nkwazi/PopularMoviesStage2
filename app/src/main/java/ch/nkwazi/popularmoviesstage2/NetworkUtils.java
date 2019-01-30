package ch.nkwazi.popularmoviesstage2;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static ch.nkwazi.popularmoviesstage2.BuildConfig.API_KEY;

/**
 * Created by nkwazi on 29.01.19.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String KEY = API_KEY;
    private static final String QUERY_PARAM = "api_key";
    private static final String MOVIE_DB_URL = "https://api.themoviedb.org/";
    private static final String VERSION = "3";
    private static final String ID = "id";
    private static final String MEDIA_TYPE = "movie";
    private static final String REVIEW = "review";
    private static final String MOVIE_TITLE = "title";
    private static final String POSTER_PATH = "poster_path";
    private static final String OVERVIEW = "overview";
    private static final String USER_RATING = "vote_average";
    private static final String RELEASE_DATE = "release_date";
    private static final String BACKDROP_PATH = "backdrop_path";

    public static String makeHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static List<Movie> getMovieData(Context context, String data) throws JSONException {
        List<Movie> movieList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(data);
        JSONArray jsonMovies = jsonObject.getJSONArray("results");

        for (int i = 0; i < jsonMovies.length(); i++){
            JSONObject movieDetail = jsonMovies.getJSONObject(i);

            String id = movieDetail.getString(ID);
            String movieName = movieDetail.getString(MOVIE_TITLE);
            String posterPath = movieDetail.getString(POSTER_PATH);
            Log.v(TAG, "poster " + posterPath);
            String overview = movieDetail.getString(OVERVIEW);
            String userRating = movieDetail.getString(USER_RATING);
            String releaseDate = movieDetail.getString(RELEASE_DATE);
            String backdropPath = movieDetail.getString(BACKDROP_PATH);
            Log.v(TAG, "backdrop " + backdropPath);

            movieList.add(new Movie(id, movieName, posterPath, backdropPath, userRating, releaseDate, overview));
        }
        return movieList;
    }

    public static URL buildMovieUrl(String param){
        Uri uri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(VERSION)
                .appendPath(MEDIA_TYPE)
                .appendPath(param)
                .appendQueryParameter(QUERY_PARAM, KEY)
                .build();

        URL url= null;
        try {
            url = new URL(uri.toString());

        } catch (MalformedURLException e){
            e.printStackTrace();

        }

        Log.v(TAG, "Built Uri " + url);

        return url;
    }

    public static URL buildReviewUrl() {
        Uri uri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(VERSION)
                .appendPath(REVIEW)
                .appendQueryParameter(QUERY_PARAM, KEY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }
}

