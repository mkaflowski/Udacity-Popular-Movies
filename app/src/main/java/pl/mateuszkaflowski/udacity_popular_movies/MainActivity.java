package pl.mateuszkaflowski.udacity_popular_movies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Movie;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.MovieCollector;

public class MainActivity extends AppCompatActivity {

    public static final String POPULAR_IDENTIFIER = "POPULAR";
    public static final String TOP_RATED_IDENTIFIER = "TOP_RATED";
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private CollectMovieInfoAsyncTask collectTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.menuRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movieList);

        recyclerView.setAdapter(movieAdapter);

        collectTask = new CollectMovieInfoAsyncTask();
        collectTask.execute(POPULAR_IDENTIFIER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuPopular:
                collectTask = new CollectMovieInfoAsyncTask();
                collectTask.execute(POPULAR_IDENTIFIER);
                break;
            case R.id.menuTopRated:
                collectTask = new CollectMovieInfoAsyncTask();
                collectTask.execute(TOP_RATED_IDENTIFIER);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class CollectMovieInfoAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings[0].equals(POPULAR_IDENTIFIER)) {
                try {
                    List<Movie> movies = MovieCollector.getPopularMovies(CommonConstants.API_KEY);
                    return movies;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            if (strings[0].equals(TOP_RATED_IDENTIFIER)) {
                try {
                    List<Movie> movies = MovieCollector.getTopRatedMovies(CommonConstants.API_KEY);
                    return movies;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if (movies == null) {
                Toast.makeText(MainActivity.this, "Fetching data error...", Toast.LENGTH_SHORT).show();
                return;
            }

            KLog.d(movies.size());

            movieList.clear();
            movieList.addAll(movies);

            movieAdapter.notifyDataSetChanged();
        }
    }

}
