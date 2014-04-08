package me.storm.ninegag.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import me.storm.ninegag.R;
import me.storm.ninegag.data.RequestManager;
import me.storm.ninegag.model.Section;

/**
 * Created by storm on 14-3-28.
 */
public class TestSectionAdapter extends BaseAdapter {
    private Context mContext;

    private ArrayList<Section> mSections;

    private Drawable mDefaultImageDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));

    public TestSectionAdapter(Context context, ArrayList<Section> sections) {
        this.mContext = context;
        this.mSections = sections;
    }

    @Override
    public int getCount() {
        if (mSections != null)
            return mSections.size();
        return 0;
    }

    @Override
    public Section getItem(int position) {
        return mSections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.listitem_section, null);
        Holder holder = getHolder(convertView);
        Section section = getItem(position);
        holder.imageRequest = RequestManager.loadImage(section.images.normal, RequestManager
                .getImageListener(holder.image, mDefaultImageDrawable, mDefaultImageDrawable));
        holder.caption.setText(section.caption);
        return convertView;
    }

    private Holder getHolder(final View view) {
        Holder holder = (Holder) view.getTag();
        if (holder == null) {
            holder = new Holder(view);
            view.setTag(holder);
        }
        return holder;
    }

    private class Holder {
        public ImageView image;

        public TextView caption;

        public ImageLoader.ImageContainer imageRequest;

        public Holder(View view) {
            image = (ImageView) view.findViewById(R.id.iv_normal);
            caption = (TextView) view.findViewById(R.id.tv_caption);
        }
    }
}
