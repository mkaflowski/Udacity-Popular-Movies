package pl.mateuszkaflowski.udacity_popular_movies;

import android.os.AsyncTask;
import android.widget.Toast;

import com.socks.library.KLog;

import java.util.List;

import pl.mateuszkaflowski.udacity_popular_movies.moviedata.Movie;
import pl.mateuszkaflowski.udacity_popular_movies.moviedata.MovieCollector;

class CollectMovieInfoAsyncTask extends AsyncTask<String, Void, List<Movie>> {

    private OnMovieCollectedCallback onMovieCollectedCallback;
    // TODO: repleace api key
    private static final String API_KEY = CommonConstants.API_KEY;

    @Override
    protected List<Movie> doInBackground(String... strings) {
        KLog.d(strings[0]);
        if (strings[0].equals(MainActivity.POPULAR_IDENTIFIER)) {
            try {
                return MovieCollector.getPopularMovies(API_KEY);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        if (strings[0].equals(MainActivity.TOP_RATED_IDENTIFIER)) {
            try {
                return MovieCollector.getTopRatedMovies(API_KEY);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if(onMovieCollectedCallback!=null)
            onMovieCollectedCallback.onMovieCollected(movies);
    }

    public void setOnMovieCollectedCallback(OnMovieCollectedCallback onMovieCollectedCallback) {
        this.onMovieCollectedCallback = onMovieCollectedCallback;
    }

    public interface OnMovieCollectedCallback{
        public void onMovieCollected(List<Movie> movies);
    }
}
