package com.example.langcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] projection = {DictionaryContract.Columns.DICTIONARY_CODE, DictionaryContract.Columns.DICTIONARY_POLISHNAME};
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(DictionaryContract.CONTENT_URI,
        //Cursor cursor = contentResolver.query(DictionaryContract.buildDictionaryUri(3),
                projection,
                null,
                null,
                null);

        if(cursor != null){
            Log.d(TAG, "onCreate: number of rows: " + cursor.getCount());
            while(cursor.moveToNext()){
                for(int i = 0; i < cursor.getColumnCount(); i++){
                    Log.d(TAG, "onCreate: " + cursor.getColumnName(i) + ": " + cursor.getString(i));
                }
                Log.d(TAG, "onCreate: ==========================================");
            }
            cursor.close();
        }

   //     AppDatabase appDatabase = AppDatabase.getInstance(this);
    //    final SQLiteDatabase db = appDatabase.getReadableDatabase();
    }
}