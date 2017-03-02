package pl.mateuszkaflowski.udacity_popular_movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import pl.mateuszkaflowski.udacity_popular_movies.moviedata.MovieCollector;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.menuRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MovieCollector.getPopularMovies();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }).start();

    }
}
