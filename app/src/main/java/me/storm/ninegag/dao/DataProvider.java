package me.storm.ninegag.dao;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import me.storm.ninegag.App;

/**
 * Created by storm on 14-4-8.
 */
public class DataProvider extends ContentProvider {

    static final Object DBLock = new Object();

    public static final String AUTHORITY = "com.storm.9gag.provider";

    private static DBHelper mDBHelper;

    public static DBHelper getDBHelper() {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(App.getContext());
        }
        return mDBHelper;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
