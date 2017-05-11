package pl.mateuszkaflowski.udacity_popular_movies.moviedata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;


public class FavDataSource {
    private SQLiteDatabase database;
    private FavMoviesDbHelper dbHelper;

    public static String[] allColumns = {FavMoviesDbHelper.COLUMN_TITLE,
            FavMoviesDbHelper.COLUMN_DATE,
            FavMoviesDbHelper.COLUMN_RATING,
            FavMoviesDbHelper.COLUMN_OVERVIEW,
            FavMoviesDbHelper.COLUMN_POSTER,
            FavMoviesDbHelper.COLUMN_TMDB_ID
    };

    public FavDataSource(Context context) {
        dbHelper = new FavMoviesDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertMovie(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(FavMoviesDbHelper.COLUMN_TITLE, movie.getOriginalTitle());
        values.put(FavMoviesDbHelper.COLUMN_TMDB_ID, movie.getId());
        values.put(FavMoviesDbHelper.COLUMN_DATE, movie.getReleaseDate());
        values.put(FavMoviesDbHelper.COLUMN_OVERVIEW, movie.getOverview());
        values.put(FavMoviesDbHelper.COLUMN_POSTER, movie.getPosterImageUrl());
        values.put(FavMoviesDbHelper.COLUMN_RATING, movie.getUserRating());
        return database.insert(FavMoviesDbHelper.TABLE_FAV, null, values);
    }

    public boolean deleteMovie(Movie movie) {
        String where = FavMoviesDbHelper.COLUMN_TMDB_ID + "=" + movie.getId();
        return database.delete(FavMoviesDbHelper.TABLE_FAV, where, null) > 0;
    }

    public List<Movie> getAllMovies() {

        Cursor query = database.query(FavMoviesDbHelper.TABLE_FAV, allColumns, null, null, null, null, null);
        KLog.d("movie cout = "+query.getColumnCount());

        List<Movie> movies = new ArrayList<>();
        while (query.moveToNext()){
            String title = query.getString(0);
            String date = query.getString(1);
            String rating = query.getString(2);
            String overview = query.getString(3);
            String poster = query.getString(4);
            String TMDB_ID = query.getString(5);

            Movie movie = new Movie();
            movie.setId(TMDB_ID);
            movie.setOriginalTitle(title);
            movie.setOverview(overview);
            movie.setPosterImageUrl(poster);
            movie.setReleaseDate(date);
            movie.setUserRating(rating);

            movies.add(movie);
        }
        query.close();

        return movies;
    }

}
