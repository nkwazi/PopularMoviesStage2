package ch.nkwazi.popularmoviesstage2;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import ch.nkwazi.popularmoviesstage2.data.MovieRepo;
import ch.nkwazi.popularmoviesstage2.model.Movie;

public class FavoriteViewModel extends AndroidViewModel {

    private MovieRepo movieRepo;
    private LiveData<List<Movie>> favoriteMovies;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        movieRepo = new MovieRepo(application);
        favoriteMovies = movieRepo.getFavoriteMovies();
    }

    LiveData<List<Movie>> getFavoriteMovies(){
        return favoriteMovies;
    }

    void insert(Movie movie){
        movieRepo.insert(movie);
    }

    void delete(Movie movie){
        movieRepo.delete(movie);
    }

}
