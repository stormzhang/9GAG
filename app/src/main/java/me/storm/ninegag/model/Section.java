package me.storm.ninegag.model;

import android.media.Image;

/**
 * Created by storm on 14-3-25.
 */
public class Section extends BaseModel {
    private String id;
    private String caption;
    private String link;
    private Image images;
    private Vote votes;

    private class Image {
        private String small;
        private String normal;
        private String large;
    }

    private class Vote {
        private int count;
    }
}
