package me.storm.ninegag.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.etsy.android.grid.StaggeredGridView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.storm.ninegag.R;
import me.storm.ninegag.data.ImageCacheManager;
import me.storm.ninegag.model.Feed;
import me.storm.ninegag.util.DensityUtils;


/**
 * Created by storm on 14-3-26.
 */
public class FeedsAdapter extends CursorAdapter {
    private static final int[] COLORS = {R.color.holo_blue_light, R.color.holo_green_light, R.color.holo_orange_light, R.color.holo_purple_light, R.color.holo_red_light};

    private static final int IMAGE_MAX_HEIGHT = 240;

    private LayoutInflater mLayoutInflater;

    private StaggeredGridView mListView;

    private Drawable mDefaultImageDrawable;

    private Resources mResource;

    public FeedsAdapter(Context context, StaggeredGridView listView) {
        super(context, null, false);
        mResource = context.getResources();
        mLayoutInflater = LayoutInflater.from(context);
        mListView = listView;
    }

    @Override
    public Feed getItem(int position) {
        mCursor.moveToPosition(position);
        return Feed.fromCursor(mCursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return mLayoutInflater.inflate(R.layout.listitem_feed, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder holder = getHolder(view);
        if (holder.imageRequest != null) {
            holder.imageRequest.cancelRequest();
        }

        view.setEnabled(!mListView.isItemChecked(cursor.getPosition()
                + mListView.getHeaderViewsCount()));

        Feed feed = Feed.fromCursor(cursor);
        mDefaultImageDrawable = new ColorDrawable(mResource.getColor(COLORS[cursor.getPosition() % COLORS.length]));

        holder.caption.setText(feed.caption);
        //TODO: here you set the html for all scroll views
        holder.gist.loadData("" +
                "<body><p>Live Gist example in html</p><script src=\"https://gist.github.com/d95d5429655c1123ddca8730d5e8dc9f.js\"></script><link rel=\"stylesheet\" href=\"https://assets-cdn.github.com/assets/gist-embed-b03925b9c18fa42b22f168e7d17a7ca70ca50a4126cbfca9f91a1a8bc79b5905.css\"><div id=\"gist39255888\" class=\"gist\">" +
                "    <div class=\"gist-file\">" +
                "      <div class=\"gist-data\">" +
                "        <div class=\"js-gist-file-update-container js-task-list-container file-box\">" +
                "  <div id=\"file-wurst-client-v2-21-2-error-report-md\" class=\"file\">" +
                "    " +
                "  <div id=\"readme\" class=\"readme blob instapaper_body\">" +
                "    <article class=\"markdown-body entry-content\" itemprop=\"text\"><blockquote>" +
                "<p>This is an auto-generated error report for the <a href=\"https://www.wurst-client.tk\">Wurst Client</a>.</p>" +
                "</blockquote>" +
                "" +
                "<h1><a id=\"user-content-description\" class=\"anchor\" href=\"#description\" aria-hidden=\"true\"><svg aria-hidden=\"true\" class=\"octicon octicon-link\" height=\"16\" version=\"1.1\" viewBox=\"0 0 16 16\" width=\"16\"><path d=\"M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z\"></path></svg></a>Description</h1>" +
                "" +
                "<p>An error occurred while processing events." +
                "Event type: UpdateEvent</p>" +
                "" +
                "<h1><a id=\"user-content-stacktrace\" class=\"anchor\" href=\"#stacktrace\" aria-hidden=\"true\"><svg aria-hidden=\"true\" class=\"octicon octicon-link\" height=\"16\" version=\"1.1\" viewBox=\"0 0 16 16\" width=\"16\"><path d=\"M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z\"></path></svg></a>Stacktrace</h1>" +
                "" +
                "<pre><code>java.lang.NullPointerException" +
                "" +
                "</code></pre>" +
                "" +
                "<h1><a id=\"user-content-system-details\" class=\"anchor\" href=\"#system-details\" aria-hidden=\"true\"><svg aria-hidden=\"true\" class=\"octicon octicon-link\" height=\"16\" version=\"1.1\" viewBox=\"0 0 16 16\" width=\"16\"><path d=\"M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z\"></path></svg></a>System details</h1>" +
                "" +
                "<ul>" +
                "<li>OS: Windows 7 (amd64)</li>" +
                "<li>Java version: 1.8.0_101 (Oracle Corporation)</li>" +
                "<li>Wurst version: 2.21.2 (latest: 2.21.2)</li>" +
                "<li>Timestamp: 2016.08.27-10:35:50</li>" +
                "</ul>" +
                "</article>" +
                "  </div>" +
                "" +
                "  </div>" +
                "  " +
                "</div>" +
                "" +
                "      </div>" +
                "      <div class=\"gist-meta\">" +
                "        <a href=\"https://gist.github.com/anonymous/d95d5429655c1123ddca8730d5e8dc9f/raw/58883dfd56c88ffa63d58b34a8c2e96d06035226/Wurst-Client-v2.21.2-Error-Report.md\" style=\"float:right\">view raw</a>" +
                "        <a href=\"https://gist.github.com/anonymous/d95d5429655c1123ddca8730d5e8dc9f#file-wurst-client-v2-21-2-error-report-md\">Wurst-Client-v2.21.2-Error-Report.md</a>" +
                "        hosted with ❤ by <a href=\"https://github.com\">GitHub</a>" +
                "      </div>" +
                "    </div>" +
                "</div>" +
                "</body>" +
                "","","utf-8");
    }

    private Holder getHolder(final View view) {
        Holder holder = (Holder) view.getTag();
        if (holder == null) {
            holder = new Holder(view);
            view.setTag(holder);
        }
        return holder;
    }

    static class Holder {
        @InjectView(R.id.iv_normal)
        WebView gist;

        @InjectView(R.id.tv_caption)
        TextView caption;

        public ImageLoader.ImageContainer imageRequest;

        public Holder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
