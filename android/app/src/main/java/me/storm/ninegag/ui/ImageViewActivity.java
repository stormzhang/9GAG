package me.storm.ninegag.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import me.storm.ninegag.R;
import me.storm.ninegag.model.Feed;
import me.storm.ninegag.ui.adapter.FeedsAdapter;
import me.storm.ninegag.view.ProgressWheel;
import me.storm.ninegag.view.swipeback.SwipeBackActivity;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by storm on 14-4-15.
 */
public class ImageViewActivity extends SwipeBackActivity {
    public static final String IMAGE_URL = "";

    @InjectView(R.id.webView)
    WebView webView;

    @InjectView(R.id.fork_page)
    BootstrapButton fork_button;

    @InjectView(R.id.share_page)
    BootstrapButton share_button;

    @InjectView(R.id.eye_page)
    BootstrapButton eye_button;

    @OnClick(R.id.fork_page) void forkOnClick() {
        Log.e("ImageViewActivity","fork_page onclick");
    }
    @OnClick(R.id.share_page) void shareOnClick() {
        Log.e("ImageViewActivity","share_page onclick");
    }
    @OnClick(R.id.eye_page) void eyeOnClick() {
        Log.e("ImageViewActivity","eye_page onclick");
    }
    private PhotoViewAttacher mAttacher;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        ButterKnife.inject(this);
        String git_id = getIntent().getStringExtra(IMAGE_URL);

        Feed feed = Feed.getFromCache(git_id);
        setTitle(feed.title);

        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(feed.script_url, "", "utf-8");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAttacher != null) {
            mAttacher.cleanup();
        }
    }
}
