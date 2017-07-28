package com.dk.main;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TableLayout;

import static com.dk.main.DBConstants.TABLE_NAME;
import static android.provider.BaseColumns._ID;
import static com.dk.main.DBConstants.ACCOUNT;
import static com.dk.main.DBConstants.PASSWORD;
import static com.dk.main.DBConstants.TOKEN;
import static com.dk.main.DBConstants.USERID;

/**
 * Created by DK on 2017/7/10.
 */

public class DBHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "taxi_go.db";
    private final static int DATABASE_VERSION = 9;
    protected static final String TAG = "DBHelper";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        onCreate();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dropDB(db);

        final String INIT_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TOKEN + " CHAR , " +
                USERID + " CHAR , " +
                ACCOUNT + " CHAR UNIQUE, " +
                PASSWORD + " CHAR);";
        db.execSQL(INIT_TABLE);


    }

    public void dropDB(SQLiteDatabase db) {
        final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion) {
        dropDB(db);
        onCreate(db);

    }
}
