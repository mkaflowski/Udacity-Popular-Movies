package pl.mateuszkaflowski.udacity_popular_movies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.socks.library.KLog;

import java.util.List;

import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Movie;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.MovieCollector;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.menuRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        CollectMovieInfoAsyncTask collectTask = new CollectMovieInfoAsyncTask();
        collectTask.execute();
    }

    private class CollectMovieInfoAsyncTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... voids) {
            try {
                return MovieCollector.getPopularMovies();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            KLog.d(movies.size());
        }
    }

}
