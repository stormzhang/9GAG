package me.storm.ninegag.ui;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import me.storm.ninegag.R;
import me.storm.ninegag.data.RequestManager;
import me.storm.ninegag.ui.Receivers.GistLikeReceiver;
import me.storm.ninegag.util.ToastUtils;

/**
 * Created by storm on 14-3-24.
 */
public abstract class BaseActivity extends FragmentActivity {
    protected ActionBar actionBar;
    private ShimmerTextView mActionBarTitle;
    public  AlarmManager alarmMgr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    private void initActionBar() {
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        View view = View.inflate(this, R.layout.actionbar_title, null);
        mActionBarTitle = (ShimmerTextView) view.findViewById(R.id.tv_shimmer);
        new Shimmer().start(mActionBarTitle);
        actionBar.setCustomView(view);
    }

    public void setTitle(int resId) {
        mActionBarTitle.setText(resId);
    }

    public void setTitle(CharSequence text) {
        mActionBarTitle.setText(text);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_profile:
                startActivity(new Intent(this, PreferenceActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
    }

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showLong(error.getMessage());
            }
        };
    }

    public void setTimeNotification(String gist_id) {
        int half_hour_millis=10000;
        Intent intent = new Intent(this, GistLikeReceiver.class);
        int notificationId=1;
        intent.putExtra("GIST_ID", gist_id);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, notificationId, intent, 0);
        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        half_hour_millis, alarmIntent);
    }

}
