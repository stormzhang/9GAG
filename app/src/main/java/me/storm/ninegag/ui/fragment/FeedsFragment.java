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
import android.widget.Toast;

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
import me.storm.ninegag.util.ListViewUtils;
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
    SwipeRefreshLayout mSwipeLayout;// 谷歌官方提供的下拉刷新
    @InjectView(R.id.grid_view)
    PageStaggeredGridView gridView;// 瀑布流

    private MenuItem mRefreshItem;

    private Category mCategory;
    private FeedsDataHelper mDataHelper;
    private FeedsAdapter mAdapter;
    private String mPage = "0";

    // 给外层调用，比如在MainActivity中
    public static FeedsFragment newInstance(Category category) {
        FeedsFragment fragment = new FeedsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CATEGORY, category.name());
        fragment.setArguments(bundle);// 传数据，目的地是本类的 onCreateView
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fragment通过实现onCreateOptionsMenu()方法给Activity的可选菜单（包括动作栏）提供菜单项，但是为了这个方法能够接受调用，必须在onCreate()方法中调用setHasOptionsMenu()方法来指示这个Fragment应该作为可选菜单的添加项（否则，这个Fragment不接受对onCreateOptionsMenu()方法的调用）。
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
        // FeedsAdapter封装一层动画
        AnimationAdapter animationAdapter = new CardsAnimationAdapter(mAdapter);
        animationAdapter.setAbsListView(gridView);
        gridView.setAdapter(animationAdapter);
        gridView.setLoadNextListener(new OnLoadNextListener() {
            @Override
            public void onLoadNext() {
                loadNext();
            }
        });

        // 点击条目进入 ImageViewActivity
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
        // 设置下拉刷新时候，ActionBar下面的闪烁颜色
        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // 谷歌官方guide的注释：
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
        loadFirst();
        return contentView;
    }

    // 取出 ActionBar，设置整个View的点击响应
    private void initActionBar() {
        View actionBarContainer = ActionBarUtils.findActionBarContainer(getActivity());
        actionBarContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ListViewUtils.smoothScrollListViewToTop(gridView); // 不起作用
                ToastUtils.showShort("actionBarContainer.setOnClickListener");
                //gridView.smoothScrollToPositionFromTop(0, 200);
                //gridView.resetToTop(); // 可以回到顶部，但是有一点问题
            }
        });
    }

    private void parseArgument() {
        Bundle bundle = getArguments();
        mCategory = Category.valueOf(bundle.getString(EXTRA_CATEGORY));
    }

    private void loadData(String next) {
        // 没在刷新，并且刚启动，实行刷新
        if (!mSwipeLayout.isRefreshing() && ("0".equals(next))) {
            setRefreshing(true);
        }
        // String.format()就像C语言的printf("%d%d", i, j)，连接字符串替换占位符，生成url
        // 封装的GsonRequest会把自动内部生成一个Feed.FeedRequestData对象，并且传入到 Listener 中的 response
        executeRequest(new GsonRequest(String.format(GagApi.LIST, mCategory.name(), next), Feed.FeedRequestData.class, responseListener(), errorListener()));
    }

    private Response.Listener<Feed.FeedRequestData> responseListener() {
        // 点击刷新，onRefresh()那里会导致 mPage = "0"
        final boolean isRefreshFromTop = ("0".equals(mPage));
        return new Response.Listener<Feed.FeedRequestData>() {
            @Override
            public void onResponse(final Feed.FeedRequestData response) {
                TaskUtils.executeAsyncTask(new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {
                        // 如果是刚启动，成功请求之后，删除所有旧缓存，载入新数据
                        // 不然如果是上拉加载，不删除
                        if (isRefreshFromTop) {
                            mDataHelper.deleteAll();
                        }

                        mPage = response.getPage();// 改变 mPage，以用于下一次刷新 next
                        ArrayList<Feed> feeds = response.data;
                        mDataHelper.bulkInsert(feeds);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        if (isRefreshFromTop) {
                            // 叫停
                            setRefreshing(false);
                        } else {
                            // 没看懂，可能需要去查一下瀑布流的使用
                            gridView.setState(LoadingFooter.State.Idle);
                        }
                    }
                });
            }
        };
    }

    @Override
    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtils.showShort(R.string.loading_failed);
                // 失败也叫停
                setRefreshing(false);
                // TODO 不理解
                gridView.setState(LoadingFooter.State.Idle, 3000);
            }
        };
    }

    // 用于刚启动，还有点击刷新按钮
    private void loadFirst() {
        mPage = "0";
        loadData(mPage);
    }

    private void loadNext() {
        loadData(mPage);
    }

    // 点击刷新会调用到这个方法
    public void loadFirstAndScrollToTop() {
        // TODO: gridView scroll to top
        // gridView.resetToTop();// 有问题
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
        mSwipeLayout.setRefreshing(refreshing); // 让下拉刷新组件动画也闪烁起来
        if (mRefreshItem == null) return;

        // 一直滚动，直到外部叫停
        if (refreshing)
            mRefreshItem.setActionView(R.layout.actionbar_refresh_progress);
        else
            mRefreshItem.setActionView(null);// 去掉ActionView那层，恢复
    }
}
