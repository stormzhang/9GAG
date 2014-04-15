package me.storm.ninegag.util;

import android.os.Build;

/**
 * Created by storm on 14-4-15.
 */
public class VersionUtils {
    public static final boolean IS_JBMR2 = Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2;
    public static final boolean IS_ISC = Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    public static final boolean IS_GINGERBREAD_MR1 = Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD_MR1;
}
