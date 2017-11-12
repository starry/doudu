package com.rain.doudu.api.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DouDu.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper mDatabaseHelper = null;

    public static DatabaseHelper getInstance(Context context) {
        if (mDatabaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (mDatabaseHelper == null) {
                    mDatabaseHelper = new DatabaseHelper(context);
                }
            }
        }
        return mDatabaseHelper;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists books (_bid integer primary key autoincrement,isbn13,varchar,book text)");
        db.execSQL("create table if not exists diary ( title varchar, content varchar, dtime varchar ,uid integer )");
        db.execSQL("create table if not exists review (id varchar,title varchar,rating varchar,content varchar,uid integer)");
        db.execSQL("create table if not exists user(id integer primary key autoincrement ,name varchar not null unique,pwd varchar not null unique,birth char,city char,desc char)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
