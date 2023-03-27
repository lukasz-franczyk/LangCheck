package com.example.langcheck;

import static com.example.langcheck.AppProvider.CONTENT_AUTHORITY;
import static com.example.langcheck.AppProvider.CONTENT_AUTHORITY_URI;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DictionaryContract {

    static final String TABLE_NAME = "Dictionary";

    public static class Columns{
        public static final String _ID = BaseColumns._ID;
        public static final String DICTIONARY_ENGLISHNAME = "EnglishName";
        public static final String DICTIONARY_POLISHNAME = "PolishName";
        public static final String DICTIONARY_CODE = "Code";
        public static final String DICTIONARY_ORIGINNAME = "OriginName";

        private Columns(){
            //private constructor to prevent instantiation
        }
    }

    /**
     * The URI to access the Dictionary table
     */
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    static Uri buildDictionaryUri(long dictionaryId){
        return ContentUris.withAppendedId(CONTENT_URI, dictionaryId);
    }

    static long getDictionaryId(Uri uri){
        return ContentUris.parseId(uri);
    }
}
