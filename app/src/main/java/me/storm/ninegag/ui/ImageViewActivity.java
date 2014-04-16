package me.storm.ninegag.ui;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.storm.ninegag.R;
import me.storm.ninegag.view.ProgressWheel;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by storm on 14-4-15.
 */
public class ImageViewActivity extends BaseActivity {
    public static final String IMAGE_URL = "image_url";

    @InjectView(R.id.photoView)
    PhotoView photoView;

    @InjectView(R.id.progressWheel)
    ProgressWheel progressWheel;

    private PhotoViewAttacher mAttacher;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        ButterKnife.inject(this);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.view_big_image);

        mAttacher = new PhotoViewAttacher(photoView);
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                finish();
            }
        });

        String imageUrl = getIntent().getStringExtra(IMAGE_URL);
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheOnDisc(true)
                .considerExifParams(true).build();
        ImageLoader.getInstance().displayImage(imageUrl, photoView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressWheel.setVisibility(View.GONE);
                mAttacher.update();
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                progressWheel.setProgress(360 * current / total);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAttacher != null) {
            mAttacher.cleanup();
        }
    }
}
