package me.storm.ninegag.ui;

import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import me.storm.ninegag.App;
import me.storm.ninegag.R;
import me.storm.ninegag.data.RequestManager;
import me.storm.ninegag.ui.fragment.BaseFragment;
import me.storm.ninegag.ui.fragment.DrawerFragment;

/**
 * Created by storm on 14-3-24.
 */
public abstract class BaseActivity extends FragmentActivity {
    protected ActionBar actionBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getActionBar();
    }

    @Override
    public void onStop() {
        super.onStop();
        RequestManager.cancelAll(this);
    }

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    protected void replaceFragment(int viewId, BaseFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(App.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }
}
