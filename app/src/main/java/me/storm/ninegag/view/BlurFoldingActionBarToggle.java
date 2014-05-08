package me.storm.ninegag.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nineoldandroids.view.ViewHelper;

import me.storm.ninegag.util.Blur;

/**
 * Created by storm on 14-4-19.
 * <p/>
 * 自定义的ActionBarDrawerToggle,同时实现折叠和毛玻璃效果
 */
public class BlurFoldingActionBarToggle extends ActionBarDrawerToggle {
    private Activity mActivity;
    private View mView;
    private ImageView mBlurImage;

    public BlurFoldingActionBarToggle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.mActivity = activity;
    }

    public void onDrawerSlide(android.view.View drawerView, float slideOffset) {
        super.onDrawerSlide(drawerView, slideOffset);
        if (drawerView instanceof BaseFoldingLayout) {
            ((BaseFoldingLayout) drawerView).setFoldFactor(1 - slideOffset);
        }

        if (slideOffset > 0.0f) {
            setBlurAlpha(slideOffset);
        }
    }

    private void setBlurAlpha(float slideOffset) {
        if (mBlurImage.getVisibility() != View.VISIBLE) {
            startBlur();
        }
        ViewHelper.setAlpha(mBlurImage, slideOffset);
    }


    private void startBlur() {
        mBlurImage.setImageBitmap(null);
        mBlurImage.setVisibility(View.VISIBLE);

        Bitmap source = loadBitmapFromView(mView);
        Bitmap blur = Blur.fastblur(mActivity, source, 20);
        mBlurImage.setImageBitmap(blur);
    }

    public void setBlurImageAndView(ImageView blurImage, View view) {
        this.mBlurImage = blurImage;
        this.mView = view;
    }

    public static Bitmap loadBitmapFromView(View v) {
        // view not displayed before
        if (v.getMeasuredHeight() <= 0) {
            v.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return null;
        } else {
            Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
            v.draw(c);
            return b;
        }
    }
}
