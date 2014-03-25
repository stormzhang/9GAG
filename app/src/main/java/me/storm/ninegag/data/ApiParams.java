package me.storm.ninegag.data;

import java.util.HashMap;

/**
 * Created by storm on 14-3-25.
 */
public class ApiParams extends HashMap<String, String> {
    private static final long serialVersionUID = 8112047472727256876L;

    public ApiParams with(String key, String value) {
        put(key, value);
        return this;
    }
}
