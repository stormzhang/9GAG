package me.storm.ninegag.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.gson.Gson;

import me.storm.ninegag.App;
import me.storm.ninegag.R;
import me.storm.ninegag.api.GagApi;
import me.storm.ninegag.data.GsonRequest;
import me.storm.ninegag.model.Category;
import me.storm.ninegag.model.Section;
import me.storm.ninegag.ui.adapter.SectionAdapter;
import me.storm.ninegag.ui.adapter.TestSectionAdapter;

/**
 * Created by storm on 14-3-25.
 */
public class SectionFragment extends BaseFragment {
    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";

    private Category mCategory;

    private TestSectionAdapter mAdapter;

    private ListView mListView;

    public static SectionFragment newInstance(Category category) {
        SectionFragment fragment = new SectionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CATEGORY, category.name());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_section, null);
        mListView = (ListView) contentView.findViewById(R.id.listView);
        parseArgument();
        loadData(0);
        return contentView;
    }

    private void parseArgument() {
        Bundle bundle = getArguments();
        mCategory = Category.valueOf(bundle.getString(EXTRA_CATEGORY));
    }

    private void loadData(final int page) {
        executeRequest(new GsonRequest(String.format(GagApi.LIST, "hot", page), Section.SectionRequestData.class, responseListener(), errorListener()));
    }

    private Response.Listener<Section.SectionRequestData> responseListener() {
        return new Response.Listener<Section.SectionRequestData>() {
            @Override
            public void onResponse(Section.SectionRequestData response) {
                Toast.makeText(App.getContext(), new Gson().toJson(response), Toast.LENGTH_LONG).show();
                mAdapter = new TestSectionAdapter(getActivity(), response.data);
                mListView.setAdapter(mAdapter);
            }
        };
    }
}
