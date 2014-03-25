package me.storm.ninegag.model;

/**
 * Created by storm on 14-3-25.
 */
public enum Category {
    hot("Hot"), trending("Trending"), fresh("Fresh");
    private String mDisplayName;

    Category(String displayName) {
        mDisplayName = displayName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }
}
