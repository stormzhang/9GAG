package me.storm.ninegag.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import me.storm.ninegag.model.Category;
import me.storm.ninegag.model.Feed;
import me.storm.ninegag.util.database.Column;
import me.storm.ninegag.util.database.SQLiteTable;
import android.support.v4.content.CursorLoader;

/**
 * Created by storm on 14-4-8.
 */
public class FeedsDataHelper extends BaseDataHelper {
    private Category mCategory;

    public FeedsDataHelper(Context context, Category category) {
        super(context);
        mCategory = category;
    }

    @Override
    protected Uri getContentUri() {
        return DataProvider.FEEDS_CONTENT_URI;
    }

    private ContentValues getContentValues(Feed feed) {
        ContentValues values = new ContentValues();
        values.put(FeedsDBInfo.ID, feed.id);
        values.put(FeedsDBInfo.CATEGORY, mCategory.ordinal());
        values.put(FeedsDBInfo.JSON, feed.toJson());
        return values;
    }

    public Feed query(long id) {
        Feed feed = null;
        Cursor cursor = query(null, FeedsDBInfo.CATEGORY + "=?" + " AND " + FeedsDBInfo.ID + "= ?",
                new String[] {
                        String.valueOf(mCategory.ordinal()), String.valueOf(id)
                }, null);
        if (cursor.moveToFirst()) {
            feed = Feed.fromCursor(cursor);
        }
        cursor.close();
        return feed;
    }

    public void bulkInsert(List<Feed> feeds) {
        ArrayList<ContentValues> contentValues = new ArrayList<ContentValues>();
        for (Feed feed : feeds) {
            ContentValues values = getContentValues(feed);
            contentValues.add(values);
        }
        ContentValues[] valueArray = new ContentValues[contentValues.size()];
        bulkInsert(contentValues.toArray(valueArray));
    }

    public int deleteAll() {
        synchronized (DataProvider.DBLock) {
            DBHelper mDBHelper = DataProvider.getDBHelper();
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            int row = db.delete(FeedsDBInfo.TABLE_NAME, FeedsDBInfo.CATEGORY + "=?", new String[] {
                    String.valueOf(mCategory.ordinal())
            });
            return row;
        }
    }

    public CursorLoader getCursorLoader() {
        return new CursorLoader(getContext(), getContentUri(), null, FeedsDBInfo.CATEGORY + "=?",
                new String[] {
                        String.valueOf(mCategory.ordinal())
                }, FeedsDBInfo._ID + " ASC");
    }

    public static final class FeedsDBInfo implements BaseColumns {
        private FeedsDBInfo() {
        }

        public static final String TABLE_NAME = "feeds";

        public static final String ID = "id";

        public static final String CATEGORY = "category";

        public static final String JSON = "json";

        public static final SQLiteTable TABLE = new SQLiteTable(TABLE_NAME)
                .addColumn(ID, Column.DataType.INTEGER)
                .addColumn(CATEGORY, Column.DataType.INTEGER).addColumn(JSON, Column.DataType.TEXT);
    }
}
