package ch.nkwazi.popularmoviesstage2.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by nkwazi on 29.01.19.
 */

@Entity
public class Movie implements Parcelable {

    @PrimaryKey
    private int movieId;
    private String movieTitle;
    private String moviePosterPath;
    private String movieBackdropPath;
    private String movieRating;
    private String movieReleaseDate;
    private String movieOverview;

    public Movie(int movieId, String movieTitle, String moviePosterPath, String movieBackdropPath, String movieRating, String movieReleaseDate, String movieOverview){
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.moviePosterPath = moviePosterPath;
        this.movieBackdropPath = movieBackdropPath;
        this.movieRating = movieRating;
        this.movieReleaseDate = movieReleaseDate;
        this.movieOverview = movieOverview;
    }

    protected Movie(Parcel in) {
        movieId = in.readInt();
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
        parcel.writeInt(movieId);
        parcel.writeString(movieTitle);
        parcel.writeString(moviePosterPath);
        parcel.writeString(movieBackdropPath);
        parcel.writeString(movieRating);
        parcel.writeString(movieReleaseDate);
        parcel.writeString(movieOverview);
    }

    public int getMovieId() { return movieId; }

    public String getMovieTitle() { return movieTitle; }

    public String getMoviePosterPath() { return moviePosterPath; }

    public String getMovieBackdropPath() { return movieBackdropPath; }

    public String getMovieRating() { return movieRating; }

    public String getMovieReleaseDate() { return movieReleaseDate; }

    public String getMovieOverview() { return movieOverview; }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

