package pl.mateuszkaflowski.udacity_popular_movies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        movie = getIntent().getParcelableExtra(MOVIE_EXTRA);
        setTitle(movie.getOriginalTitle());

        initilizeViews();
    }

    private void initilizeViews() {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(movie.getOriginalTitle());

        TextView tvDate = (TextView) findViewById(R.id.tvDate);
        tvDate.setText(movie.getReleaseDate());

        TextView tvRate = (TextView) findViewById(R.id.tvRate);
        tvRate.setText(Float.toString(movie.getUserRating()) + " / 10");

        TextView tvOverview = (TextView) findViewById(R.id.tvOverview);
        tvOverview.setText(movie.getOverview());

        ImageView ivPoster = (ImageView) findViewById(R.id.ivPoster);
        Picasso.with(this).load(movie.getPosterImageUrl()).into(ivPoster);


    }

}
