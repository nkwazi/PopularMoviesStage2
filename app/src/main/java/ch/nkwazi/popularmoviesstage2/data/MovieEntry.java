package ch.nkwazi.popularmoviesstage2.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movie")
public class MovieEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int mMovieId;
    private Double mMovieRate;
    private String mMoviePoster;
    private String mMovieTitle;
    private String mMovieOverview;
    private String mMovieDate;

    @Ignore
    public MovieEntry(int mMovieId, Double mMovieRate, String mMoviePoster, String mMovieTitle, String mMovieOverview, String mMovieDate) {
        this.mMovieId = mMovieId;
        this.mMovieRate = mMovieRate;
        this.mMoviePoster = mMoviePoster;
        this.mMovieTitle = mMovieTitle;
        this.mMovieOverview = mMovieOverview;
        this.mMovieDate = mMovieDate;
    }

    public MovieEntry(int id, int mMovieId, Double mMovieRate, String mMoviePoster, String mMovieTitle, String mMovieOverview, String mMovieDate) {
        this.id = id;
        this.mMovieId = mMovieId;
        this.mMovieRate = mMovieRate;
        this.mMoviePoster = mMoviePoster;
        this.mMovieTitle = mMovieTitle;
        this.mMovieOverview = mMovieOverview;
        this.mMovieDate = mMovieDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    public Double getmMovieRate() {
        return mMovieRate;
    }

    public void setmMovieRate(Double mMovieRate) {
        this.mMovieRate = mMovieRate;
    }

    public String getmMoviePoster() {
        return mMoviePoster;
    }

    public void setmMoviePoster(String mMoviePoster) {
        this.mMoviePoster = mMoviePoster;
    }

    public String getmMovieTitle() {
        return mMovieTitle;
    }

    public void setmMovieTitle(String mMovieTitle) {
        this.mMovieTitle = mMovieTitle;
    }

    public String getmMovieOverview() {
        return mMovieOverview;
    }

    public void setmMovieOverview(String mMovieOverview) {
        this.mMovieOverview = mMovieOverview;
    }

    public String getmMovieDate() {
        return mMovieDate;
    }

    public void setmMovieDate(String mMovieDate) {
        this.mMovieDate = mMovieDate;
    }
}
