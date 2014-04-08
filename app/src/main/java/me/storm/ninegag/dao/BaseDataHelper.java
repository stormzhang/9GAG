package me.storm.ninegag.dao;

import android.content.Context;

/**
 * Created by storm on 14-3-28.
 */
public abstract class BaseDataHelper {
    private Context mContext;

    public BaseDataHelper(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }
}
