package me.storm.ninegag.ui.fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.ButterKnife;
import me.storm.ninegag.R;
import me.storm.ninegag.model.Category;
import me.storm.ninegag.ui.MainActivity;
import me.storm.ninegag.ui.adapter.DrawerAdapter;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class LoginFragment extends android.app.Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, contentView);
        return contentView;
    }
}
