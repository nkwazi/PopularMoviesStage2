package ch.nkwazi.popularmoviesstage2.data;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import ch.nkwazi.popularmoviesstage2.model.Movie;


public class MovieRepo {

    private MovieDao movieDao;
    private LiveData<List<Movie>> favMovies;

    public MovieRepo(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        movieDao = appDatabase.movieDao();
        favMovies = movieDao.loadAllFav();
    }

    public LiveData<List<Movie>> getFavoriteMovies(){
        return favMovies;
    }

    public void insert(Movie movie){
        new InsertFavoriteMovieAsyncTask(movieDao).execute(movie);

    }

    public void delete(Movie movie){
        new DeleteFavMovieAsyncTask(movieDao).execute(movie);

    }

    private static class InsertFavoriteMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private MovieDao movieDao;

        private InsertFavoriteMovieAsyncTask(MovieDao movieDao){
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.addToFav(movies[0]);
            return null;
        }
    }

    private static class DeleteFavMovieAsyncTask extends AsyncTask<Movie, Void, Void>{
        private MovieDao movieDao;

        private DeleteFavMovieAsyncTask(MovieDao movieDao){
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            movieDao.removeFav(movies[0]);
            return null;
        }
    }

}