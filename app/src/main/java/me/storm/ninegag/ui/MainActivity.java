package me.storm.ninegag.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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
    @InjectView(R.id.drawer_layout)
    FoldingDrawerLayout mDrawerLayout;

    private BlurFoldingActionBarToggle mDrawerToggle;

    private FeedsFragment mContentFragment;

    private Category mCategory;

    private Menu mMenu;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mDrawerToggle = new BlurFoldingActionBarToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View view) {
                setTitle(R.string.app_name);
                mMenu.findItem(R.id.action_refresh).setVisible(false);
            }

            @Override
            public void onDrawerClosed(View view) {
                setTitle(mCategory.getDisplayName());
                mMenu.findItem(R.id.action_refresh).setVisible(true);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

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
