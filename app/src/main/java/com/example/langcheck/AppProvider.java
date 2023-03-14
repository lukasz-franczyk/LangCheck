package com.example.langcheck;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * This is only that knows about {@link AppDatabase}
 */

public class AppProvider extends ContentProvider {

    private static final String TAG = "AppProvider";

    private AppDatabase mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    static final String CONTENT_AUTHORITY = "com.example.langcheck";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int DICTIONARY = 100;
    private static final int DICTIONARY_ID = 101;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        // eg. content://com.example.langcheck.provider/Dictionary
        matcher.addURI(CONTENT_AUTHORITY, DictionaryContract.TABLE_NAME, DICTIONARY);
        // eg. content://com.example.langcheck.provider/Dictionary/8
        matcher.addURI(CONTENT_AUTHORITY, DictionaryContract.TABLE_NAME + "/#", DICTIONARY_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = AppDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query: called with URI " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "query match is " + match);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch(match) {
            case DICTIONARY:
                queryBuilder.setTables(DictionaryContract.TABLE_NAME);
                break;
            case DICTIONARY_ID:
                queryBuilder.setTables(DictionaryContract.TABLE_NAME);
                long dictionaryId = DictionaryContract.getDictionaryId(uri);
                queryBuilder.appendWhere(DictionaryContract.Columns._ID + " = " + dictionaryId);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
