package com.example.langcheck;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Basic database class for the application
 *
 * The only class that should use this is {@link AppProvider}.
 */

class AppDatabase extends SQLiteOpenHelper {

  private static final String TAG = "AppDatabase";

  public static final String DATABASE_NAME = "LangCheck.db";
  public static final int DATABASE_VERSION = 1;

  // Implement AppDatabase as a Singleton
  private static AppDatabase instance = null;

  private AppDatabase(Context context){
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
   Log.d(TAG, "AppDatabase: constructor");
  }

 /**
  * Get an instance of the app's singleton database helper object
  *
  * @param context the content providers context
  * @return a SQLite database helper object
  */
static AppDatabase getInstance(Context context){
  if(instance == null){
   Log.d(TAG, "getInstance: creating new instance");
   instance = new AppDatabase(context);
  }

  return instance;
}


 @Override
 public void onCreate(SQLiteDatabase db) {
  Log.d(TAG, "onCreate: starts");
  String sSQL;
  //sSQL = "CREATE TABLE Dictionary (_id INTEGER PRIMARY KEY NOT NULL, EnglishName TEXT NOT NULL, PolishName TEXT NOT NULL, Code TEXT NOT NULL, OriginName TEXT)";
  sSQL = "CREATE TABLE " + DictionaryContract.TABLE_NAME + " ("
          + DictionaryContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, "
          + DictionaryContract.Columns.DICTIONARY_ENGLISHNAME + " TEXT NOT NULL, "
          + DictionaryContract.Columns.DICTIONARY_POLISHNAME + " TEXT NOT NULL, "
          + DictionaryContract.Columns.DICTIONARY_CODE + " TEXT NOT NULL, "
          + DictionaryContract.Columns.DICTIONARY_ORIGINNAME + "TEXT);";
  Log.d(TAG, sSQL);
  db.execSQL(sSQL);

  Log.d(TAG, "onCreate: ends");
 }

 @Override
 public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    Log.d(TAG, "onUpgrade: starts");
    switch(i) {
        case 1:
            // upgrade logic from version
            break;
        default:
            throw new IllegalStateException("onUpgrade() with unknown newVersion: " + i1);
    }
    Log.d(TAG, "onUpgrade: ends");
 }
}
