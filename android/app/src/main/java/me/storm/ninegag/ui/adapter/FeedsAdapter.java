package me.storm.ninegag.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.etsy.android.grid.StaggeredGridView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.storm.ninegag.R;
import me.storm.ninegag.model.Feed;


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


        view.setEnabled(!mListView.isItemChecked(cursor.getPosition()
                + mListView.getHeaderViewsCount()));

        Feed feed = Feed.fromCursor(cursor);
        holder.git_id = feed.git_id;

        holder.caption.setText(feed.title);

        holder.gist.clearCache(true);
        holder.gist.clearHistory();

        holder.gist.loadUrl("about:blank");
        // holder.gist.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        holder.gist.loadData(feed.script_url, "", "utf-8");
    }

    private Holder getHolder(final View view) {
        Holder holder = (Holder) view.getTag();
        if (holder == null) {
            holder = new Holder(view);
            view.setTag(holder);
        }
        return holder;
    }

    public static class Holder {
        @InjectView(R.id.iv_normal)
        WebView gist;

        public String git_id = "";
        @InjectView(R.id.tv_caption)
        TextView caption;

        @InjectView(R.id.fork)
        BootstrapButton fork_button;

        @InjectView(R.id.share)
        BootstrapButton share_button;

        @InjectView(R.id.eye)
        BootstrapButton eye_button;

        @OnClick(R.id.fork) void forkOnClick() {
            Log.e("FeedsAdapter - Holder","fork onclick");
        }
        @OnClick(R.id.share) void shareOnClick() {
            Log.e("FeedsAdapter - Holder","share onclick");
        }
        @OnClick(R.id.eye) void eyeOnClick() {
            Log.e("FeedsAdapter - Holder","eye onclick");
        }

        public Holder(View view) {
            ButterKnife.inject(this, view);
            gist.getSettings().setJavaScriptEnabled(true);
        }
    }
}
