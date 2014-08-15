package me.storm.ninegag.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;

import me.storm.ninegag.R;
import me.storm.ninegag.ui.fragment.PreferenceFragment;
import me.storm.ninegag.view.swipeback.SwipeBackActivity;

/**
 *
 * Created by storm on 14-4-16.
 */
public class PreferenceActivity extends SwipeBackActivity { //滑动返回，这个SwipeBackActivity有继承BaseActivity的
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        getFragmentManager().beginTransaction().replace(R.id.container, new PreferenceFragment())
                .commit();
        setTitle(R.string.action_settings);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //不是基类已经有这个代码了吗？为什么这边还要再写一遍呢？没理解。（猜测，这是自动生成的，作者没删
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
