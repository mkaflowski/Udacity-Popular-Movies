package pl.mateuszkaflowski.udacity_popular_movies.moviedata;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class FavContentProvider extends ContentProvider {

    private FavMoviesDbHelper dbHelper;

    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(FavContract.AUTHORITY, FavContract.PATH_FAV, MOVIES);
        uriMatcher.addURI(FavContract.AUTHORITY, FavContract.PATH_FAV + "/#", MOVIE_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new FavMoviesDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor returnCursor = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        switch (uriMatcher.match(uri)) {
            case MOVIES:
                returnCursor = db.query(
                        FavContract.Fav.TABLE_FAV,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case MOVIE_WITH_ID:
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }

     if(returnCursor!=null)
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        Uri returnUri = null;
        switch (match) {
            case MOVIES:
                long id = db.insert(FavContract.Fav.TABLE_FAV, null, values);
                if (id > 0) {
                    //success
                    returnUri = ContentUris.withAppendedId(FavContract.Fav.CONTENT_URI, id);
                } else {
                    throw new UnsupportedOperationException("Unknown uri: " + uri);
                }
                break;
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        int id = 0;
        switch (match) {
            case MOVIES:
                id = db.delete(FavContract.Fav.TABLE_FAV, selection,selectionArgs);
                break;
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return id;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
