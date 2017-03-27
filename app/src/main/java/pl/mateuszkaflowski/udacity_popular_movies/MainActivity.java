package pl.mateuszkaflowski.udacity_popular_movies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Movie;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ClickCallback, CollectMovieInfoAsyncTask.OnMovieCollectedCallback {

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
        movieAdapter.setClickCallback(this);

        recyclerView.setAdapter(movieAdapter);

        collectTask = new CollectMovieInfoAsyncTask();
        collectTask.setOnMovieCollectedCallback(this);
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
                collectTask.setOnMovieCollectedCallback(this);
                collectTask.execute(POPULAR_IDENTIFIER);
                break;
            case R.id.menuTopRated:
                collectTask = new CollectMovieInfoAsyncTask();
                collectTask.setOnMovieCollectedCallback(this);
                collectTask.execute(TOP_RATED_IDENTIFIER);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemClick(View view, int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        Movie movie = movieList.get(position);
        intent.putExtra(DetailActivity.MOVIE_EXTRA, movie);
        DetailActivity.launch(this, intent, view);
    }


    @Override
    public void onMovieCollected(List<Movie> movies) {
        if (movies == null) {
            Toast.makeText(this, "Fetching data error...", Toast.LENGTH_SHORT).show();
            return;
        }

        movieList.clear();
        movieList.addAll(movies);

        movieAdapter.notifyDataSetChanged();
    }
}
