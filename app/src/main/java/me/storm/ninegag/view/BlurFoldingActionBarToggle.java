package me.storm.ninegag.view;

import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;

/**
 * Created by storm on 14-4-19.
 *
 * 自定义的ActionBarDrawerToggle,同时实现折叠和毛玻璃效果
 */
public class BlurFoldingActionBarToggle extends ActionBarDrawerToggle {
//    protected DrawerLayout drawerLayout;

    public BlurFoldingActionBarToggle(Activity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    public void onDrawerSlide(android.view.View drawerView, float slideOffset) {
        if (drawerView instanceof BaseFoldingLayout) {
            ((BaseFoldingLayout) drawerView).setFoldFactor(1 - slideOffset);
        }
    }
}
