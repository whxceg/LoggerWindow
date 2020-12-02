package com.sam.lib.logger.window.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sam.lib.logger.window.LoggerController;
import com.sam.lib.window.R;

public class LoggerWindow implements View.OnClickListener {

    private static LoggerWindow mLoggerWindow;

    private View mRootView;
    private Context mAppContext;
    private boolean isShow;
    private WindowManager mWindowManager;

    WindowManager.LayoutParams mParams;

    int mScreenHeight;
    private ImageView mImageView;

    public static LoggerWindow getInstance(Context context) {
        if (mLoggerWindow == null) {
            mLoggerWindow = new LoggerWindow(context);
        }
        return mLoggerWindow;
    }

    private LoggerWindow(Context context) {
        mAppContext = context.getApplicationContext();
        LoggerController.init(context);
        mRootView = LayoutInflater.from(context).inflate(R.layout.log_float_window, null, false);
        mRootView.measure(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mImageView = mRootView.findViewById(R.id.start);
        mImageView.setOnClickListener(this);
        mRootView.findViewById(R.id.clear).setOnClickListener(this);
        mRootView.findViewById(R.id.open).setOnClickListener(this);
        mRootView.findViewById(R.id.close).setOnClickListener(this);
        mWindowManager = (WindowManager) mAppContext.getSystemService(Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        if (Build.VERSION.SDK_INT > 25) {
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        }
        mParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParams.gravity = Gravity.START | Gravity.TOP;
        DisplayMetrics dm = mAppContext.getResources().getDisplayMetrics();
        mScreenHeight = dm.heightPixels;

        mParams.height = mRootView.getMeasuredHeight();
        mParams.width = mRootView.getMeasuredWidth();

        mParams.x = 50;
        mParams.y = dm.heightPixels - mRootView.getMeasuredHeight() - 200;

        MoveLinearLayout linearLayout = mRootView.findViewById(R.id.container);
        linearLayout.setMoveListener((dx, dy) -> {
            mParams.y = mParams.y + dy;
            mWindowManager.updateViewLayout(mRootView, mParams);
        });
        mImageView.setImageResource(LoggerController.getWriteAble() ? R.drawable.log_pause : R.drawable.log_start);
    }

    public void show() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(mAppContext)) {
                mAppContext.startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + mAppContext.getPackageName())));
                return;
            }
        }

        if (!isShow) {
            isShow = true;
            try {
                if (mRootView.getParent() != null)
                    mWindowManager.removeView(mRootView);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                mWindowManager.addView(mRootView, mParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start) {
            LoggerController.setWriteAble(!LoggerController.getWriteAble());
            mImageView.setImageResource(LoggerController.getWriteAble() ? R.drawable.log_pause : R.drawable.log_start);
        } else if (v.getId() == R.id.clear) {
            LoggerController.cleanLog();
        } else if (v.getId() == R.id.open) {
            v.getContext().startActivity(new Intent(v.getContext(), LoggerActivity.class));
        } else if (v.getId() == R.id.close) {
            hide();
        }
    }

    public void hide() {
        try {
            isShow = false;
            if (mRootView.getParent() != null)
                mWindowManager.removeView(mRootView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
