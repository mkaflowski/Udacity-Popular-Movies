package pl.mateuszkaflowski.udacity_popular_movies;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.FavDataSource;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.FavMoviesDbHelper;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Movie;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.MovieCollector;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Review;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Trailer;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks, TrailerAdapter.ClickCallback, View.OnClickListener {

    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE:image";

    private int LOADER_ID = 127;

    private Movie movie;

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
    @BindView(R.id.reviewRecyclerView)
    RecyclerView rvReviews;
    @BindView(R.id.btFav)
    Button btFav;
    private FavDataSource favDataSource;


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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTrailers.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTrailers.getContext(),
                layoutManager.getOrientation());
        rvTrailers.addItemDecoration(dividerItemDecoration);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        rvReviews.setLayoutManager(layoutManager2);
        dividerItemDecoration = new DividerItemDecoration(rvReviews.getContext(),
                layoutManager2.getOrientation());
        rvReviews.addItemDecoration(dividerItemDecoration);

        btFav.setOnClickListener(this);

        favDataSource = new FavDataSource(this);
    }

    private void initilizeViews() {

        tvTitle.setText(movie.getOriginalTitle());
        tvDate.setText(movie.getReleaseDate());
        tvRate.setText(movie.getUserRating() + " / 10");
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
    public Loader onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader(this) {
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                KLog.i("onStartLoading");
                forceLoad();
            }

            @Override
            public List<Object> loadInBackground() {
                KLog.i("loadInBackground");

                try {
                    List<Object> res = new ArrayList<>();
                    res.add(MovieCollector.getTrailers(CommonConstants.API_KEY, (Movie) args.getParcelable(MOVIE_EXTRA)));
                    res.add(MovieCollector.getReviews(CommonConstants.API_KEY, (Movie) args.getParcelable(MOVIE_EXTRA)));
                    return res;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

                return null;
            }

        };
    }


    @Override
    public void onLoadFinished(Loader loader, Object data) {
        KLog.i("onLoadFinished");
        List<Object> res = (List<Object>) data;

        List<Trailer> trailers = (List<Trailer>) res.get(0);
        TrailerAdapter trailerAdapter = new TrailerAdapter(trailers);
        rvTrailers.setAdapter(trailerAdapter);
        trailerAdapter.setClickCallback(this);

        List<Review> reviews = (List<Review>) res.get(1);
        ReviewAdapter reviewAdapter = new ReviewAdapter(reviews);
        rvReviews.setAdapter(reviewAdapter);

    }


    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void itemClick(View view, int position, Trailer trailer) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getYoutubeLink())));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btFav:
                KLog.d("Adding to database");
                favDataSource.open();
                favDataSource.insertMovie(movie);
                favDataSource.getAllMovies();
                favDataSource.close();
                break;
        }
    }
}
