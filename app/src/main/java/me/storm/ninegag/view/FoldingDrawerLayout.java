package me.storm.ninegag.view;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by storm on 14-4-19.
 *
 * 修复不能自定义DrawerListener的bug
 */
public class FoldingDrawerLayout extends DrawerLayout {
    public FoldingDrawerLayout(Context context) {
        super(context);
    }

    public FoldingDrawerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FoldingDrawerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (isDrawerView2(child)) {
                System.out.println("at" + i);
                BaseFoldingLayout foldingNavigationLayout = new BaseFoldingLayout(
                        getContext());
                foldingNavigationLayout.setAnchorFactor(1);
                removeView(child);
                foldingNavigationLayout.addView(child);
                ViewGroup.LayoutParams layPar = child.getLayoutParams();
                addView(foldingNavigationLayout, i, layPar);
            }
        }
    }

    public BaseFoldingLayout getFoldingLayout(View drawerView) {
        if (!isDrawerView2(getRealDrawer(drawerView))) {
            throw new IllegalArgumentException("View " + drawerView
                    + " is not a sliding drawer");
        }

        return isFoldingLayout(getRealDrawer(drawerView)) ? (BaseFoldingLayout) getRealDrawer(drawerView) : null;
    }

    boolean isDrawerView2(View child) {
        final int gravity = ((LayoutParams) child.getLayoutParams()).gravity;
        final int absGravity = GravityCompat.getAbsoluteGravity(gravity,
                ViewCompat.getLayoutDirection(child));
        return (absGravity & (Gravity.LEFT | Gravity.RIGHT)) != 0;
    }

    /**
     * Close the specified drawer view by animating it into view.
     *
     * @param drawerView Drawer view to close
     */
    public void closeDrawer(View drawerView) {
        super.closeDrawer(getRealDrawer(drawerView));
    }

    private View getRealDrawer(View drawerView) {
        View drawerView2 = (View) drawerView.getParent();
        if (isFoldingLayout(drawerView2)) {
            return drawerView2;
        } else {
            return drawerView;
        }
    }

    private boolean isFoldingLayout(View drawerView) {
        return drawerView instanceof BaseFoldingLayout;
    }
}