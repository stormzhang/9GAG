package me.storm.ninegag.api;

/**
 * Created by storm on 14-3-25.
 */
public class GisterApi {
    private static final String HOST = "http://104.196.147.66/api/gists/";

    public static String buildRequest(String category, String page) {
        String queryString = "?";
        category = category.isEmpty() ? "" : "category=" + category;
        page = page.isEmpty() ? "" : "page=" + page;
        queryString += !category.isEmpty() && !page.isEmpty() ? category + "&" + page : category + page;
        return HOST + (("?".equals(queryString)) ? "" : queryString);
    }
}
