package ch.nkwazi.popularmoviesstage2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.List;
import android.content.Intent;
import butterknife.BindView;
import butterknife.ButterKnife;
import ch.nkwazi.popularmoviesstage2.api.ApiResponse;
import ch.nkwazi.popularmoviesstage2.api.RetrofitClient;
import ch.nkwazi.popularmoviesstage2.api.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fetchingDataPB)
    ProgressBar mProgressBar;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private MovieAdapter movieAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        loadMovies("top_rated");
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
                loadMovies("popular");
                return true;
            case R.id.sort_rated:
                loadMovies("top_rated");
                return true;
            case R.id.favorite:
                launchFavoriteActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void launchFavoriteActivity() {
        Intent intent = new Intent(this,FavoriteActivity.class);
        startActivity(intent);
    }

    private void loadMovies(String sort) {
        Service apiService = RetrofitClient.getClient().create(Service.class);

        Call<ApiResponse<Movie>> call;

        if (sort.equals("top_rated")){
            call = apiService.getTopRatedMovies(BuildConfig.API_KEY);
        } else {
            call = apiService.getPopularMovies(BuildConfig.API_KEY);
        }

        call.enqueue(new Callback<ApiResponse<Movie>>() {
            @Override
            public void onResponse(Call<ApiResponse<Movie>> call, Response<ApiResponse<Movie>> response) {
                if (response.isSuccessful()) {
                    List<Movie> movies = fetchResults(response);
                    mRecyclerView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Movie>> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Movie> fetchResults(Response<ApiResponse<Movie>> response) {
        ApiResponse<Movie> movieApiResponse = response.body();
        return movieApiResponse.results;
    }
}