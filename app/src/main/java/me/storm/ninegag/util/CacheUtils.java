package me.storm.ninegag.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * Created by storm on 14-4-8.
 */
public class CacheUtils {
    /**
     * Get the external app cache directory.
     *
     * @param context The context to use
     * @return The external cache dir
     */
    public static File getExternalCacheDir(final Context context) {
        if (hasExternalCacheDir())
            return context.getExternalCacheDir();

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }
}
