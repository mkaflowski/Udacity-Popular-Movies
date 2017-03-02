package pl.mateuszkaflowski.udacity_popular_movies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Movie;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.MovieCollector;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.menuRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movieList);

        recyclerView.setAdapter(movieAdapter);

        CollectMovieInfoAsyncTask collectTask = new CollectMovieInfoAsyncTask();
        collectTask.execute();
    }

    private class CollectMovieInfoAsyncTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            try {
                List<Movie> movies = MovieCollector.getPopularMovies();
                return movies;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
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
