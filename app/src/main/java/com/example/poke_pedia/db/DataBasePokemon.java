package com.example.poke_pedia.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBasePokemon extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pokemon.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DataBasePokemon(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE pokemon (_id INT, name TEXT, PRIMARY KEY(_id));");
        sqLiteDatabase.execSQL("CREATE TABLE stats (_id INT, stat TEXT, value INT, PRIMARY KEY(_id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
