package pl.mateuszkaflowski.udacity_popular_movies.moviedata;

import android.net.Uri;
import android.provider.BaseColumns;


public class FavContract {
    static final String AUTHORITY = "pl.mateuszkaflowski.udacity_popular_movies";
    static final String PATH_FAV = "favourite";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static final class Fav implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_FAV).build();

        public static final String TABLE_FAV = "favourite";
        public static final String COLUMN_TITLE = "originalTitle";
        public static final String COLUMN_POSTER = "posterImageUrl";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATING = "userRating";
        public static final String COLUMN_DATE = "releaseDate";
        public static final String COLUMN_TMDB_ID = "tmdbId";

        public static final String DATABASE_NAME = "favmovies.db";
        public static final int DATABASE_VERSION = 1;

        public static String[] allColumns = {COLUMN_TITLE,
                COLUMN_DATE,
                COLUMN_RATING,
                COLUMN_OVERVIEW,
                COLUMN_POSTER,
                COLUMN_TMDB_ID
        };

    }
}
