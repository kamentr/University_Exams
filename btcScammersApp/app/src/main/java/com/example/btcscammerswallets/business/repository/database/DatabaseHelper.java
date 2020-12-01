package com.example.btcscammerswallets.business.repository.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final int DB_VERSION = 2;
    static final String DB_NAME = "btc_accounts_db_V" + DB_VERSION;
    private final DatabaseScripts databaseScripts = new DatabaseScripts();

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Run every script on create
        databaseScripts.getSqlScripts().values().forEach(db::execSQL);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //DROP all tables
//        databaseScripts.getSqlScripts().keySet().forEach(tableName -> {
//            db.execSQL("DROP TABLE IF EXISTS " + tableName);
//        });
        onCreate(db);
    }

}
