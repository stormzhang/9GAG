package me.storm.ninegag.model;

import android.database.Cursor;
import android.media.Image;
import android.text.Html;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import me.storm.ninegag.dao.FeedsDataHelper;

/**
 * Created by storm on 14-3-25.
 */
public class Feed extends BaseModel {
    private static final HashMap<String, Feed> CACHE = new HashMap<String, Feed>();

    public String git_id;
    public String self_url;
    public String title;
    public String owner_name;
    public String owner_id;
    public String recommended_gists;
    public String script_url;
    public String comments;
    public String created_at;
    public String updated_at;


    private static void addToCache(Feed feed) {
        CACHE.put(feed.git_id, feed);
    }

    public static Feed getFromCache(String git_id) {
        return CACHE.get(git_id);
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

    public static class FeedRequestData extends ArrayList<Feed> {
        public String getPage() {
            if(!this.isEmpty()){
                return  this.get(this.size() -1).updated_at;
            }
            return "-1";
        }
    }
}
