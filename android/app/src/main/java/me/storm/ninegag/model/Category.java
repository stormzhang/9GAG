package me.storm.ninegag.model;

/**
 * Created by storm on 14-3-25.
 */
public enum Category {
    hot("hot"), trending("trending"), fresh("fresh");
    private String mName;

    Category(String displayName) {
        mName = displayName;
    }

    public String getDisplayName() {
        return mName;
    }
}
