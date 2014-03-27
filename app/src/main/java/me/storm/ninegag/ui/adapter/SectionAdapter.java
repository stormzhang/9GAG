package me.storm.ninegag.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import me.storm.ninegag.data.RequestManager;
import me.storm.ninegag.model.Section;
import me.storm.ninegag.R;

/**
 * Created by storm on 14-3-26.
 */
public class SectionAdapter extends CursorAdapter {
    private LayoutInflater mLayoutInflater;

    private ListView mListView;

    private Drawable mDefaultImageDrawable = new ColorDrawable(Color.argb(255, 201, 201, 201));

    public SectionAdapter(Context context, ListView listView) {
        super(context, null, false);
        mLayoutInflater = ((Activity) context).getLayoutInflater();
        mListView = listView;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return mLayoutInflater.inflate(R.layout.listitem_section, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder holder = getHolder(view);
        if (holder.imageRequest != null) {
            holder.imageRequest.cancelRequest();
        }

        view.setEnabled(!mListView.isItemChecked(cursor.getPosition()
                + mListView.getHeaderViewsCount()));

        Section section = Section.fromCursor(cursor);
        holder.imageRequest = RequestManager.loadImage(section.images.normal, RequestManager
                .getImageListener(holder.image, mDefaultImageDrawable, mDefaultImageDrawable));
        holder.caption.setText(section.caption);
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
            image = (ImageView) view.findViewById(R.id.image);
            caption = (TextView) view.findViewById(R.id.title);
        }
    }
}
