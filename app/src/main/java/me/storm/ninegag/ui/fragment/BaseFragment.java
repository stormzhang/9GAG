package me.storm.ninegag.ui.fragment;

import me.storm.ninegag.App;
import me.storm.ninegag.data.RequestManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by storm on 14-3-25.
 */
public class BaseFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
    }

    protected void executeRequest(Request request) {
        RequestManager.addRequest(request, this);
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
