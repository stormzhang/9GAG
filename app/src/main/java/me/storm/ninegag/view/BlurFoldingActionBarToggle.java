package me.storm.ninegag.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;

import me.storm.ninegag.util.BitmapUtils;
import me.storm.ninegag.util.Blur;

/**
 * Created by storm on 14-4-19.
 * <p/>
 * 自定义的ActionBarDrawerToggle,同时实现折叠和毛玻璃效果
 */
public class BlurFoldingActionBarToggle extends ActionBarDrawerToggle {
    private static final int DEFAULT_RADIUS = 10;
    private static final int DEFAULT_DOWN_SAMPLING = 3;

    private Activity mActivity;
    private View mContainer;
    private ImageView mBlurImage;

    private int mBlurRadius;
    private int mDownSampling;

    public BlurFoldingActionBarToggle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.mActivity = activity;
        mBlurRadius = DEFAULT_RADIUS;
        mDownSampling = DEFAULT_DOWN_SAMPLING;
    }

    public void onDrawerSlide(android.view.View drawerView, float slideOffset) {
        super.onDrawerSlide(drawerView, slideOffset);
        if (drawerView instanceof BaseFoldingLayout) {
            ((BaseFoldingLayout) drawerView).setFoldFactor(1 - slideOffset);
        }

        if (slideOffset > 0.0f) {
            setBlurAlpha(slideOffset);
        } else {
            clearBlurImage();
        }
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        clearBlurImage();
    }

    private void setBlurAlpha(float slideOffset) {
        if (mBlurImage.getVisibility() != View.VISIBLE) {
            setBlurImage();
        }
        ViewHelper.setAlpha(mBlurImage, slideOffset);
    }

    private void setBlurImage() {
        mBlurImage.setImageBitmap(null);
        mBlurImage.setVisibility(View.VISIBLE);
        // do the downscaling for faster processing
        Bitmap downScaled = BitmapUtils.drawViewToBitmap(mContainer,
                mContainer.getWidth(), mContainer.getHeight(), mDownSampling);
        // apply the blur using the renderscript
        Bitmap blurred = Blur.fastblur(mActivity, downScaled, mBlurRadius);
        mBlurImage.setImageBitmap(blurred);
        downScaled.recycle();
    }

    public void setBlurImageAndView(ImageView blurImage, View view) {
        this.mBlurImage = blurImage;
        this.mContainer = view;
    }

    public void clearBlurImage() {
        mBlurImage.setVisibility(View.GONE);
        mBlurImage.setImageBitmap(null);
    }
}
