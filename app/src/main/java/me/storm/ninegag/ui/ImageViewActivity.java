package me.storm.ninegag.ui;

import android.os.Bundle;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.storm.ninegag.R;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by storm on 14-4-15.
 */
public class ImageViewActivity extends BaseActivity {
    public static final String IMAGE_URL = "image_url";

    @InjectView(R.id.photoView)
    PhotoView mPhotoView;

    private PhotoViewAttacher mAttacher;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);
        ButterKnife.inject(this);

        String imageUrl = getIntent().getStringExtra(IMAGE_URL);
        ImageLoader.getInstance().displayImage(imageUrl, mPhotoView, new SimpleImageLoadingListener() {
            public void onLoadingComplete() {
                mAttacher = new PhotoViewAttacher(mPhotoView);
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
