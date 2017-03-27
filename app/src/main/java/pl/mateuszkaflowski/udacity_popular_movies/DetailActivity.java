package pl.mateuszkaflowski.udacity_popular_movies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE:image";

    private Movie movie;

    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvDate) TextView tvDate;
    @BindView(R.id.tvRate) TextView tvRate;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.ivPoster) ImageView ivPoster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewCompat.setTransitionName(ivPoster, EXTRA_IMAGE);

        movie = getIntent().getParcelableExtra(MOVIE_EXTRA);
        setTitle(movie.getOriginalTitle());

        initilizeViews();
    }

    private void initilizeViews() {

        tvTitle.setText(movie.getOriginalTitle());
        tvDate.setText(movie.getReleaseDate());
        tvRate.setText(Float.toString(movie.getUserRating()) + " / 10");
        tvOverview.setText(movie.getOverview());

        Picasso.with(this).load(movie.getPosterImageUrl()).noFade().into(ivPoster);
    }

    public static void launch(Activity activity, Intent intent, View transitionView) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity, transitionView, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

}
