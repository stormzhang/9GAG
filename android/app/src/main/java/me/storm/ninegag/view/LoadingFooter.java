package me.storm.ninegag.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import me.storm.ninegag.R;
import me.storm.ninegag.view.titanic.Titanic;
import me.storm.ninegag.view.titanic.TitanicTextView;

/**
 * Created by storm on 14-4-12.
 */
public class LoadingFooter {
    protected View mLoadingFooter;

    TextView mLoadingText;

    TitanicTextView mTitanicText;

    private Titanic mTitanic;

    protected State mState = State.Idle;

    public static enum State {
        Idle, TheEnd, Loading
    }

    public LoadingFooter(Context context) {
        mLoadingFooter = LayoutInflater.from(context).inflate(R.layout.loading_footer, null);
        mLoadingFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 屏蔽点击
            }
        });
        mLoadingText = (TextView) mLoadingFooter.findViewById(R.id.textView);
        mTitanicText = (TitanicTextView) mLoadingFooter.findViewById(R.id.tv_titanic);
        mTitanic = new Titanic();
        mTitanic.start(mTitanicText);
        setState(State.Idle);
    }

    public View getView() {
        return mLoadingFooter;
    }

    public State getState() {
        return mState;
    }

    public void setState(final State state, long delay) {
        mLoadingFooter.postDelayed(new Runnable() {
            @Override
            public void run() {
                setState(state);
            }
        }, delay);
    }

    public void setState(State status) {
        if (mState == status) {
            return;
        }
        mState = status;

        mLoadingFooter.setVisibility(View.VISIBLE);
        switch (status) {
            case Loading:
                mLoadingText.setVisibility(View.GONE);
                mTitanicText.setVisibility(View.VISIBLE);
                break;
            case TheEnd:
                mLoadingText.setVisibility(View.VISIBLE);
                mTitanicText.setVisibility(View.GONE);
                break;
            default:
                mLoadingFooter.setVisibility(View.GONE);
                break;
        }
    }
}
