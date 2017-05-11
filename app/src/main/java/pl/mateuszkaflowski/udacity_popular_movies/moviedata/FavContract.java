package pl.mateuszkaflowski.udacity_popular_movies.moviedata;

import android.net.Uri;
import android.provider.BaseColumns;


public class FavContract {
    static final String AUTHORITY = "pl.mateuszkaflowski.udacity_popular_movies";
    static final String PATH_FAV = "favourite";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static final class Fav implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(PATH_FAV).build();


    }
}
