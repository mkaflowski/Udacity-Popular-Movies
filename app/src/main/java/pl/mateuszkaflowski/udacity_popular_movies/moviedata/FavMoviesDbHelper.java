package pl.mateuszkaflowski.udacity_popular_movies.moviedata;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavMoviesDbHelper extends SQLiteOpenHelper {

    public FavMoviesDbHelper(Context context) {
        super(context, FavContract.Fav.DATABASE_NAME, null, FavContract.Fav.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String DATABASE_CREATE = "create table "
                + FavContract.Fav.TABLE_FAV + "( " + FavContract.Fav._ID
                + " integer primary key autoincrement, "
                + FavContract.Fav.COLUMN_TITLE + " text not null, "
                + FavContract.Fav.COLUMN_POSTER + " text not null, "
                + FavContract.Fav.COLUMN_OVERVIEW + " text not null, "
                + FavContract.Fav.COLUMN_RATING + " text not null, "
                + FavContract.Fav.COLUMN_DATE + " text not null, "
                + FavContract.Fav.COLUMN_TMDB_ID + " text not null"
                +");";

        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavContract.Fav.TABLE_FAV);
        onCreate(db);
    }


}
