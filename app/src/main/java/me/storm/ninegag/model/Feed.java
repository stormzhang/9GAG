package me.storm.ninegag.model;

import android.database.Cursor;
import android.media.Image;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import me.storm.ninegag.dao.FeedsDataHelper;

/**
 * Created by storm on 14-3-25.
 */
public class Feed extends BaseModel {
    private static final HashMap<String, Feed> CACHE = new HashMap<String, Feed>();

    public String id;
    public String caption;
    public String link;
    public Image images;
    public Vote votes;

    public class Image {
        public String small;
        public String normal;
        public String large;
    }

    private class Vote {
        public int count;
    }

    private static void addToCache(Feed feed) {
        CACHE.put(feed.id, feed);
    }

    private static Feed getFromCache(String id) {
        return CACHE.get(id);
    }

    public static Feed fromJson(String json) {
        return new Gson().fromJson(json, Feed.class);
    }

    public static Feed fromCursor(Cursor cursor) {
        String id = cursor.getString(cursor.getColumnIndex(FeedsDataHelper.FeedsDBInfo.ID));
        Feed feed = getFromCache(id);
        if (feed != null) {
            return feed;
        }
        feed = new Gson().fromJson(
                cursor.getString(cursor.getColumnIndex(FeedsDataHelper.FeedsDBInfo.JSON)),
                Feed.class);
        addToCache(feed);
        return feed;
    }

    public static class FeedRequestData {
        public ArrayList<Feed> data;
        public Paging paging;

        public String getPage() {
            return paging.next;
        }
    }

    private class Paging {
        public String next;
    }
}
