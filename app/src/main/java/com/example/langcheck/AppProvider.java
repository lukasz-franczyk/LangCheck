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
    static final String CONTENT_AUTHORITY = "com.example.langcheck.provider";
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
        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch(match) {
            case DICTIONARY:
                return DictionaryContract.CONTENT_TYPE;

            case DICTIONARY_ID:
                return DictionaryContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Log.d(TAG, "Entering insert, called with uri:" + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final SQLiteDatabase db;

        Uri returnUri;
        long recordId;
        switch(match){
            case DICTIONARY:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(DictionaryContract.TABLE_NAME, null, contentValues);
                if(recordId >= 0) {
                    returnUri = DictionaryContract.buildDictionaryUri(recordId);
                }
                else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());

                    }

                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        Log.d(TAG, "Exiting insert, returning " + returnUri);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "delete called with uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch(match){
            case DICTIONARY:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(DictionaryContract.TABLE_NAME, s, strings);
                break;

            case DICTIONARY_ID:
                db = mOpenHelper.getWritableDatabase();
                long dictionaryId = DictionaryContract.getDictionaryId(uri);
                selectionCriteria = DictionaryContract.Columns._ID + " = " + dictionaryId;

                if((s != null) && (s.length() > 0)){
                    selectionCriteria += " AND (" + s + ")";
                }

                count = db.delete(DictionaryContract.TABLE_NAME, selectionCriteria, strings);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        Log.d(TAG, "Exiting delete, returning " + count);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Log.d(TAG, "update called with uri " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "match is " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch(match){
            case DICTIONARY:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(DictionaryContract.TABLE_NAME, contentValues, s, strings);
                break;

            case DICTIONARY_ID:
                db = mOpenHelper.getWritableDatabase();
                long dictionaryId = DictionaryContract.getDictionaryId(uri);
                selectionCriteria = DictionaryContract.Columns._ID + " = " + dictionaryId;

                if((s != null) && (s.length() > 0)){
                    selectionCriteria += " AND (" + s + ")";
                }

                count = db.update(DictionaryContract.TABLE_NAME, contentValues, selectionCriteria, strings);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        Log.d(TAG, "Exiting update, returning " + count);
        return count;
    }
}
