package me.storm.ninegag.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;

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
import me.storm.ninegag.ui.ImageViewActivity;
import me.storm.ninegag.ui.adapter.CardsAnimationAdapter;
import me.storm.ninegag.ui.adapter.FeedsAdapter;
import me.storm.ninegag.util.ActionBarUtils;
import me.storm.ninegag.util.TaskUtils;
import me.storm.ninegag.util.ToastUtils;
import me.storm.ninegag.view.LoadingFooter;
import me.storm.ninegag.view.OnLoadNextListener;
import me.storm.ninegag.view.PageStaggeredGridView;

/**
 * Created by storm on 14-3-25.
 */
public class FeedsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>, SwipeRefreshLayout.OnRefreshListener {
    public static final String EXTRA_CATEGORY = "extra_category";

    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeLayout;

    @InjectView(R.id.grid_view)
    PageStaggeredGridView gridView;

    private MenuItem mRefreshItem;

    private Category mCategory;
    private FeedsDataHelper mDataHelper;
    private FeedsAdapter mAdapter;
    private String mPage = "0";

    public static FeedsFragment newInstance(Category category) {
        FeedsFragment fragment = new FeedsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CATEGORY, category.name());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.inject(this, contentView);

        parseArgument();
        mDataHelper = new FeedsDataHelper(App.getContext(), mCategory);
        mAdapter = new FeedsAdapter(getActivity(), gridView);
        View header = new View(getActivity());
        gridView.addHeaderView(header);
        AnimationAdapter animationAdapter = new CardsAnimationAdapter(mAdapter);
        animationAdapter.setAbsListView(gridView);
        gridView.setAdapter(animationAdapter);
        gridView.setLoadNextListener(new OnLoadNextListener() {
            @Override
            public void onLoadNext() {
                loadNext();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imageUrl = mAdapter.getItem(position - gridView.getHeaderViewsCount()).images.large;
                Intent intent = new Intent(getActivity(), ImageViewActivity.class);
                intent.putExtra(ImageViewActivity.IMAGE_URL, imageUrl);
                startActivity(intent);
            }
        });

        initActionBar();
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        getLoaderManager().initLoader(0, null, this);
        loadFirst();
        return contentView;
    }

    private void initActionBar() {
        View actionBarContainer = ActionBarUtils.findActionBarContainer(getActivity());
        actionBarContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ListViewUtils.smoothScrollListViewToTop(gridView);
                gridView.smoothScrollToPositionFromTop(0, 0);
            }
        });
    }

    private void parseArgument() {
        Bundle bundle = getArguments();
        mCategory = Category.valueOf(bundle.getString(EXTRA_CATEGORY));
    }

    private void loadData(String next) {
        if (!mSwipeLayout.isRefreshing() && ("0".equals(next))) {
            setRefreshing(true);
        }
        executeRequest(new GsonRequest(String.format(GagApi.LIST, mCategory.name(), next), Feed.FeedRequestData.class, responseListener(), errorListener()));
    }

    private Response.Listener<Feed.FeedRequestData> responseListener() {
        final boolean isRefreshFromTop = ("0".equals(mPage));
        return new Response.Listener<Feed.FeedRequestData>() {
            @Override
            public void onResponse(final Feed.FeedRequestData response) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        if (isRefreshFromTop) {
                            mDataHelper.deleteAll();
                        }
                        mPage = response.getPage();
                        ArrayList<Feed> feeds = response.data;
                        mDataHelper.bulkInsert(feeds);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        if (isRefreshFromTop) {
                            setRefreshing(false);
                        } else {
                            gridView.setState(LoadingFooter.State.Idle);
                        }
                    }
                });
            }
        };
    }

    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showShort(R.string.loading_failed);
                setRefreshing(false);
                gridView.setState(LoadingFooter.State.Idle, 3000);
            }
        };
    }

    private void loadFirst() {
        mPage = "0";
        loadData(mPage);
    }

    private void loadNext() {
        loadData(mPage);
    }

    public void loadFirstAndScrollToTop() {
        // TODO: gridView scroll to top
        loadFirst();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mDataHelper.getCursorLoader();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
        if (data != null && data.getCount() == 0) {
            loadFirst();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }

    @Override
    public void onRefresh() {
        loadFirst();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        mRefreshItem = menu.findItem(R.id.action_refresh);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setRefreshing(boolean refreshing) {
        mSwipeLayout.setRefreshing(refreshing);
        if (mRefreshItem == null) return;

        if (refreshing)
            mRefreshItem.setActionView(R.layout.actionbar_refresh_progress);
        else
            mRefreshItem.setActionView(null);
    }
}
