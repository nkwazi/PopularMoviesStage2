package ch.nkwazi.popularmoviesstage2;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.nkwazi.popularmoviesstage2.adapter.MovieAdapter;
import ch.nkwazi.popularmoviesstage2.model.Movie;
import ch.nkwazi.popularmoviesstage2.utils.NetworkUtils;

import static ch.nkwazi.popularmoviesstage2.utils.JsonUtils.parseMovieJson;
import static ch.nkwazi.popularmoviesstage2.utils.NetworkUtils.buildMovieUrl;
import static ch.nkwazi.popularmoviesstage2.utils.NetworkUtils.makeHttpRequest;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fetchingDataPB)
    ProgressBar mProgressBar;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.connection)
    TextView mConnection;

    private static final String TOP_RATED = "top_rated";
    private static final String POPULAR = "popular";
    private static final String KEY = "key";
    MovieAdapter movieAdapter;
    ArrayList<Movie> movieResults;
    private FavoriteViewModel favoriteViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY)) {
            movieResults = savedInstanceState.getParcelableArrayList(KEY);
        } else {
            movieResults = new ArrayList<>();
            if (NetworkUtils.isOnline(this)) {
                mConnection.setVisibility(View.INVISIBLE);
                loadMovies(TOP_RATED);
            } else {
                mConnection.setVisibility(View.VISIBLE);
            }
        }

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this, movieResults);
        mRecyclerView.setAdapter(movieAdapter);

        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY, movieResults);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_popular:
                loadMovies(POPULAR);
                return true;
            case R.id.sort_rated:
                loadMovies(TOP_RATED);
                return true;
            case R.id.favorite:
                favoriteViewModel.getFavoriteMovies().observe(this, movies -> {
                    movieResults.clear();
                    movieResults.addAll(movies);
                    movieAdapter.notifyDataSetChanged();
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadMovies(String string) {
        new MovieAsyncTask().execute(string);
    }

    public class MovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }
            String orderBy = strings[0];
            URL url = buildMovieUrl(orderBy);

            try {
                String getResponse = makeHttpRequest(url);
                return parseMovieJson(getResponse);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> MovieResults) {
            if (MovieResults != null && !MovieResults.isEmpty()) {
                movieResults.clear();
                movieResults.addAll(MovieResults);
                movieAdapter.notifyDataSetChanged();
            }
        }
    }
}