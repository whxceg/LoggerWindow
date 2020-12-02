package com.sam.lib.logger.window.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MoveLinearLayout extends LinearLayout {
    public MoveLinearLayout(Context context) {
        super(context);
    }

    public MoveLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    float sx, sy;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sx = ev.getX();
                sy = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                processMove(ev.getX(), ev.getY());
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    long time;

    private void processMove(float x, float y) {

        if (mMoveListener != null && System.currentTimeMillis() - time > 90) {
            time = System.currentTimeMillis();
            int dx = (int) (x - sx);
            int dy = (int) (y - sy);
            if (Math.abs(dx) > 10 || Math.abs(dy) > 10) {
                mMoveListener.onMove(dx, dy);
            }
        }
    }

    OnMoveListener mMoveListener;

    public void setMoveListener(OnMoveListener mMoveListener) {
        this.mMoveListener = mMoveListener;
    }

    public interface OnMoveListener {
        void onMove(int dx, int dy);

    }
}
