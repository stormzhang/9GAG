package me.storm.ninegag.ui.fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.storm.ninegag.App;
import me.storm.ninegag.R;
import me.storm.ninegag.api.GagApi;
import me.storm.ninegag.dao.FeedsDataHelper;
import me.storm.ninegag.data.GsonRequest;
import me.storm.ninegag.model.Category;
import me.storm.ninegag.model.Feed;
import me.storm.ninegag.ui.adapter.FeedsAdapter;
import me.storm.ninegag.util.TaskUtils;

/**
 * Created by storm on 14-3-25.
 */
public class FeedsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";

    private Category mCategory;

    private FeedsDataHelper mDataHelper;

    private FeedsAdapter mAdapter;

    @InjectView(R.id.listView)
    ListView mListView;

    private int mPage = 0;

    public static FeedsFragment newInstance(Category category) {
        FeedsFragment fragment = new FeedsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CATEGORY, category.name());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_section, container, false);
        ButterKnife.inject(this, contentView);

        parseArgument();
        mDataHelper = new FeedsDataHelper(App.getContext(), mCategory);
        mAdapter = new FeedsAdapter(getActivity(), mListView);
        mListView.setAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
        return contentView;
    }

    private void parseArgument() {
        Bundle bundle = getArguments();
        mCategory = Category.valueOf(bundle.getString(EXTRA_CATEGORY));
    }

    private void loadData(final int page) {
        executeRequest(new GsonRequest(String.format(GagApi.LIST, "hot", page), Feed.FeedRequestData.class, responseListener(), errorListener()));
    }

    private Response.Listener<Feed.FeedRequestData> responseListener() {
        return new Response.Listener<Feed.FeedRequestData>() {
            @Override
            public void onResponse(final Feed.FeedRequestData response) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        if (mPage == 1) {
                            mDataHelper.deleteAll();
                        }
                        ArrayList<Feed> feeds = response.data;
                        mDataHelper.bulkInsert(feeds);
                        return null;
                    }
                });
            }
        };
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mDataHelper.getCursorLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
        if (data != null && data.getCount() == 0) {
            loadData(0);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }
}
