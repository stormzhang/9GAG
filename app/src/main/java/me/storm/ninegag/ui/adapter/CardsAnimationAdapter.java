package me.storm.ninegag.ui.adapter;

import android.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import me.storm.ninegag.App;

/**
 * Created by storm on 14-4-15.
 */
public class CardsAnimationAdapter extends AnimationAdapter {
    private float mTranslationY = 400;

    private float mRotationX = 8;

    private long mDuration;

    public CardsAnimationAdapter(BaseAdapter baseAdapter) {
        super(baseAdapter);
        mDuration = App.getContext().getResources().getInteger(R.integer.config_mediumAnimTime);
    }

    @Override
    protected long getAnimationDelayMillis() {
        return 30;
    }

    @Override
    protected long getAnimationDurationMillis() {
        return mDuration;
    }

    @Override
    public Animator[] getAnimators(ViewGroup parent, View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "translationY", mTranslationY, 0),
                ObjectAnimator.ofFloat(view, "rotationX", mRotationX, 0)
        };
    }

    protected void prepareAnimation(View view) {
        ViewHelper.setTranslationY(view, mTranslationY);
        ViewHelper.setRotationX(view, mRotationX);
    }
}
