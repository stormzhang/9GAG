package me.storm.ninegag.ui;

import android.os.Bundle;

import me.storm.ninegag.R;
import me.storm.ninegag.ui.fragment.LoginFragment;
import me.storm.ninegag.ui.fragment.PreferenceFragment;
import me.storm.ninegag.view.swipeback.SwipeBackActivity;

/**
 * Created by storm on 14-4-16.
 */
public class LoginActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.action_profile);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new LoginFragment())
                .commit();
    }
}
