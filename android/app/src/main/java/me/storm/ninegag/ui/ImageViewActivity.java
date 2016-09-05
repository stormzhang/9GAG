package me.storm.ninegag.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
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


    private PhotoViewAttacher mAttacher;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        ButterKnife.inject(this);
        String git_id = getIntent().getStringExtra(IMAGE_URL);
        Feed feed = Feed.getFromCache(git_id);
        setTitle(R.string.view_big_image);




        Feed.getFromCache(git_id);
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
