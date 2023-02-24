package com.example.langcheck;

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
}
