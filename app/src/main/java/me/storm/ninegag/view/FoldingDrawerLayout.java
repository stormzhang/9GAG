/*
 * Copyright (C) 2013 Priboi Tiberiu
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
 * FoldingDrawerLayout change the sliding effect with folding effect of
 * DrawerLayout
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
        setDrawerListener(new DrawerListener() {

            @Override
            public void onDrawerStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                if (drawerView instanceof BaseFoldingLayout) {
                    ((BaseFoldingLayout) drawerView).setFoldFactor(1 - slideOffset);
                }

            }

            @Override
            public void onDrawerOpened(View arg0) {

            }

            @Override
            public void onDrawerClosed(View arg0) {

            }
        });

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