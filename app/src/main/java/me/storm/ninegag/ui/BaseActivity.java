package me.storm.ninegag.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

/**
 * Created by storm on 14-3-24.
 */
public abstract class BaseActivity extends Activity {
    protected ActionBar actionBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
