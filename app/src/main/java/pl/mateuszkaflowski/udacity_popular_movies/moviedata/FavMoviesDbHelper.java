package pl.mateuszkaflowski.udacity_popular_movies.moviedata;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavMoviesDbHelper extends SQLiteOpenHelper {


    public static final String TABLE_FAV = "favourite";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "originalTitle";
    public static final String COLUMN_POSTER = "posterImageUrl";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_RATING = "userRating";
    public static final String COLUMN_DATE = "releaseDate";
    public static final String COLUMN_TMDB_ID = "tmdbId";

    private static final String DATABASE_NAME = "favmovies.db";
    private static final int DATABASE_VERSION = 1;

    public FavMoviesDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public FavMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String DATABASE_CREATE = "create table "
                + TABLE_FAV + "( " + COLUMN_ID
                + " integer primary key autoincrement, "
                + COLUMN_TITLE + " text not null, "
                + COLUMN_POSTER + " text not null, "
                + COLUMN_OVERVIEW + " text not null, "
                + COLUMN_RATING + " text not null, "
                + COLUMN_DATE + " text not null, "
                + COLUMN_TMDB_ID + " text not null"
                +");";

        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);
        onCreate(db);
    }


}
