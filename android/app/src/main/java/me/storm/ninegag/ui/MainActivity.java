package me.storm.ninegag.ui;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.storm.ninegag.R;
import me.storm.ninegag.model.Category;
import me.storm.ninegag.ui.fragment.BaseFragment;
import me.storm.ninegag.ui.fragment.DrawerFragment;
import me.storm.ninegag.ui.fragment.FeedsFragment;
import me.storm.ninegag.view.BlurFoldingActionBarToggle;
import me.storm.ninegag.view.FoldingDrawerLayout;

/**
 * Created by storm on 14-3-24.
 */
public class MainActivity extends BaseActivity {

    // ButterKnife annotation system http://www.vogella.com/tutorials/AndroidButterknife/article.html
    @InjectView(R.id.drawer_layout)
    FoldingDrawerLayout mDrawerLayout;

    @InjectView(R.id.content_frame)
    FrameLayout contentLayout;

    @InjectView(R.id.blur_image)
    ImageView blurImage;

    // Drawer
    private BlurFoldingActionBarToggle mDrawerToggle;

    //Fragment
    private FeedsFragment mContentFragment;

    // The current chosen category
    private Category mCategory;

    // Basic top right setting.
    private Menu mMenu;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ButterKnife init (automates the views system)
        ButterKnife.inject(this);

        // setting the icon
        actionBar.setIcon(R.drawable.ic_actionbar);

        // setting the drawer color to the layout
        mDrawerLayout.setScrimColor(Color.argb(100, 255, 255, 255));

        // setting the drawer
        mDrawerToggle = new BlurFoldingActionBarToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                // setting the title to our name (the categories are in the drawer)
                setTitle(R.string.app_name);

                // toggle the action_refresh
                mMenu.findItem(R.id.action_refresh).setVisible(false);
            }

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // setting the correct category name to the title
                setTitle(mCategory.getDisplayName());
                // toggle the action_refresh
                mMenu.findItem(R.id.action_refresh).setVisible(true);

                // ?
                blurImage.setVisibility(View.GONE);
                blurImage.setImageBitmap(null);
            }
        };

        // animating the background blur
        mDrawerToggle.setBlurImageAndView(blurImage, contentLayout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //  start the page. (where the magic happens)
        setCategory(Category.hot);
        replaceFragment(R.id.left_drawer, new DrawerFragment());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    protected void replaceFragment(int viewId, BaseFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mContentFragment.loadFirstAndScrollToTop();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setCategory(Category category) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        if (mCategory == category) {
            return;
        }
        mCategory = category;
        setTitle(mCategory.getDisplayName());
        mContentFragment = FeedsFragment.newInstance(category);
        replaceFragment(R.id.content_frame, mContentFragment);
    }
}
