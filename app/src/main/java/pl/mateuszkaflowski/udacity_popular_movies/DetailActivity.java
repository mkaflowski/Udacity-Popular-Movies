package pl.mateuszkaflowski.udacity_popular_movies;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Movie;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.MovieCollector;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Trailer;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Trailer>>, TrailerAdapter.ClickCallback {

    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE:image";

    private int LOADER_ID = 127;

    private Movie movie;
    private TrailerAdapter trailerAdapter;


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvRate)
    TextView tvRate;
    @BindView(R.id.tvOverview)
    TextView tvOverview;
    @BindView(R.id.ivPoster)
    ImageView ivPoster;
    @BindView(R.id.trailerRecyclerView)
    RecyclerView rvTrailers;


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

        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_EXTRA, movie);

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ID, bundle, this);

        rvTrailers.setLayoutManager(new LinearLayoutManager(this));
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

    @Override
    public Loader<List<Trailer>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<List<Trailer>>(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                KLog.i("onStartLoading");
                forceLoad();
            }

            @Override
            public List<Trailer> loadInBackground() {
                KLog.i("loadInBackground");

                try {
                    List<Trailer> trailers = MovieCollector.getTrailers(CommonConstants.API_KEY, (Movie) args.getParcelable(MOVIE_EXTRA));
                    return trailers;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

                return null;
            }

        };
    }


    @Override
    public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> data) {
        KLog.i("onLoadFinished");
        for (Trailer trailer : data) {
            KLog.i(trailer.getKey());
        }
        trailerAdapter = new TrailerAdapter(data);
        rvTrailers.setAdapter(trailerAdapter);
        trailerAdapter.setClickCallback(this);
    }


    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void itemClick(View view, int position, Trailer trailer) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getYoutubeLink())));

    }
}
