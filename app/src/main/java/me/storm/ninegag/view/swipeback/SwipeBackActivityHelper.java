
package me.storm.ninegag.view.swipeback;

import android.app.Activity;
import android.app.ActivityOptions;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Method;

import me.storm.ninegag.R;

/**
 * @author Yrom
 */
public class SwipeBackActivityHelper {
    private Activity mActivity;

    private SwipeBackLayout mSwipeBackLayout;

    public SwipeBackActivityHelper(Activity activity) {
        mActivity = activity;
    }

    @SuppressWarnings("deprecation")
    public void onActivityCreate() {
        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        mActivity.getWindow().getDecorView().setBackgroundDrawable(null);
        mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(mActivity).inflate(
                R.layout.swipeback_layout, null);
        mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {
                if (state == SwipeBackLayout.STATE_IDLE && scrollPercent == 0) {
                    convertActivityFromTranslucent();
                }
            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                convertActivityToTranslucent();
            }

            @Override
            public void onScrollOverThreshold() {

            }
        });
    }

    public void onPostCreate() {
        mSwipeBackLayout.attachToActivity(mActivity);
        convertActivityFromTranslucent();
    }

    public View findViewById(int id) {
        if (mSwipeBackLayout != null) {
            return mSwipeBackLayout.findViewById(id);
        }
        return null;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    /**
     * Convert a translucent themed Activity
     * {@link android.R.attr#windowIsTranslucent} to a fullscreen opaque
     * Activity.
     * <p/>
     * Call this whenever the background of a translucent Activity has changed
     * to become opaque. Doing so will allow the {@link android.view.Surface} of
     * the Activity behind to be released.
     * <p/>
     * This call has no effect on non-translucent activities or on activities
     * with the {@link android.R.attr#windowIsFloating} attribute.
     */
    public void convertActivityFromTranslucent() {
        try {
            Method method = Activity.class.getDeclaredMethod("convertFromTranslucent", new Class[0]);
            method.setAccessible(true);
            method.invoke(mActivity, new Object[0]);
        } catch (Throwable t) {
        }
    }

    /**
     * Convert a translucent themed Activity
     * {@link android.R.attr#windowIsTranslucent} back from opaque to
     * translucent following a call to {@link #convertActivityFromTranslucent()}
     * .
     *


     * Calling this allows the Activity behind this one to be seen again. Once
     * all such Activities have been redrawn
     *


     * This call has no effect on non-translucent activities or on activities
     * with the {@link android.R.attr#windowIsFloating} attribute.
     */
    public void convertActivityToTranslucent() {
        try {
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes) {
                if (clazz.getSimpleName().contains(
                        "TranslucentConversionListener")) {
                    translucentConversionListenerClazz = clazz;
                }
            }
            Method[] methods = Activity.class.getDeclaredMethods();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Method method = Activity.class.getDeclaredMethod(
                        "convertToTranslucent",
                        translucentConversionListenerClazz);
                method.setAccessible(true);
                method.invoke(mActivity, new Object[] { null });
            } else {
                Method method = Activity.class.getDeclaredMethod(
                        "convertToTranslucent",
                        translucentConversionListenerClazz,
                        ActivityOptions.class);
                method.setAccessible(true);
                method.invoke(mActivity, new Object[] { null, null });
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
