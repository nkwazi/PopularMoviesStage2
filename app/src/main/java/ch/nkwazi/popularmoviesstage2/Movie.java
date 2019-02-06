package ch.nkwazi.popularmoviesstage2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nkwazi on 29.01.19.
 */

public class Movie implements Parcelable {

    public static final String TAG = "movies";

    @SerializedName("title")
    String movieTitle;
    @SerializedName("id")
    String movieId;
    @SerializedName("poster_path")
    String moviePosterPath;
    @SerializedName("overview")
    String movieOverview;
    @SerializedName("backdrop_path")
    String movieBackdropPath;
    @SerializedName("release_date")
    String movieReleaseDate;
    @SerializedName("vote_average")
    String movieRating;

    public Movie(String id, String title, String posterPath, String backdropPath, String rating, String releaseDate, String overview){
        this.movieId = id;
        this.movieTitle = title;
        this.moviePosterPath = posterPath;
        this.movieBackdropPath = backdropPath;
        this.movieRating = rating;
        this.movieReleaseDate = releaseDate;
        this.movieOverview = overview;
    }

    public Movie(Parcel in) {
        movieId = in.readString();
        movieTitle = in.readString();
        moviePosterPath = in.readString();
        movieBackdropPath = in.readString();
        movieRating = in.readString();
        movieReleaseDate = in.readString();
        movieOverview = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieId);
        parcel.writeString(movieTitle);
        parcel.writeString(moviePosterPath);
        parcel.writeString(movieBackdropPath);
        parcel.writeString(movieRating);
        parcel.writeString(movieReleaseDate);
        parcel.writeString(movieOverview);

    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[0];
        }
    };
}

