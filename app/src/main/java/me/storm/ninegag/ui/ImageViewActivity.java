package me.storm.ninegag.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.storm.ninegag.R;
import me.storm.ninegag.ui.fragment.FeedsFragment;
import me.storm.ninegag.view.ProgressWheel;
import me.storm.ninegag.view.swipeback.SwipeBackActivity;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 点击每一条项目，会进入到这个Activity，展示一张大图片
 *
 * Created by storm on 14-4-15.
 */
public class ImageViewActivity extends SwipeBackActivity {
    public static final String IMAGE_URL = "image_url"; // FeedsFragment 中也使用了，也就是传入的源头 intent.putExtra(ImageViewActivity.IMAGE_URL, imageUrl);

    @InjectView(R.id.photoView)
    PhotoView photoView;// 可双击放大缩小，Pinch的那货

    @InjectView(R.id.progressWheel)
    ProgressWheel progressWheel;

    private PhotoViewAttacher mAttacher;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        ButterKnife.inject(this);

        setTitle(R.string.view_big_image);

        mAttacher = new PhotoViewAttacher(photoView);
        // 单击图片事件
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                finish();
            }
        });

        String imageUrl = getIntent().getStringExtra(IMAGE_URL);
        // UIL，这边下载作者用了UIL，但在列表中，图片的下载它用的是 Volley，猜测作者想练习两种方式
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
    public void onDestroy() {
        super.onDestroy();
        if (mAttacher != null) {
            mAttacher.cleanup();
        }
    }
}
