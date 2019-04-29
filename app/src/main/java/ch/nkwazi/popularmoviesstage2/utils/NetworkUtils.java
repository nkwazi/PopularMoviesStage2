package ch.nkwazi.popularmoviesstage2.utils;

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
    private static final String MEDIA_TYPE = "movie";
    private static final String REVIEW = "reviews";
    private static final String VIDEO = "videos";

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

    public static URL buildTrailerUrl(String id){
        Uri uri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(VERSION)
                .appendPath(MEDIA_TYPE)
                .appendPath(id)
                .appendPath(VIDEO)
                .appendQueryParameter(QUERY_PARAM, KEY)
                .build();

        URL url= null;
        try {
            url = new URL(uri.toString());

        } catch (MalformedURLException e){
            e.printStackTrace();

        }

        Log.v(TAG, "Built Trailer Uri " + url);

        return url;
    }

    public static URL buildReviewUrl(String id){
        Uri uri = Uri.parse(MOVIE_DB_URL).buildUpon()
                .appendPath(VERSION)
                .appendPath(MEDIA_TYPE)
                .appendPath(id)
                .appendPath(REVIEW)
                .appendQueryParameter(QUERY_PARAM, KEY)
                .build();

        URL url= null;
        try {
            url = new URL(uri.toString());

        } catch (MalformedURLException e){
            e.printStackTrace();

        }

        Log.v(TAG, "Built Review Uri " + url);

        return url;
    }

}

