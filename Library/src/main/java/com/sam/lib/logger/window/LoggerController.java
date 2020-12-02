package com.sam.lib.logger.window;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;

public class LoggerController {

    private static boolean mWrite = false;
    private static File mFile;
    private static HttpLoggingInterceptor.Level defLevel = HttpLoggingInterceptor.Level.NONE;

    public static void init(Context context) {
        File dir = new File(context.getExternalCacheDir(), "http");
        if (!dir.exists()) dir.mkdir();

        File[] ls = dir.listFiles();
        if (ls != null && ls.length > 0) {
            for (File l : ls) {
                l.delete();
            }
        }
        mFile = new File(dir, "log_" + System.currentTimeMillis() + ".txt");
        defLevel = getInterceptor().getLevel();
    }

    public static void log(String log) {
        if (mWrite && mFile != null) {
            FileUtil.INSTANCE.appendText(log + "\n", mFile);
        }
    }

    public static void setWriteAble(boolean writeAble) {
        mWrite = writeAble;
        getInterceptor().setLevel(writeAble ? HttpLoggingInterceptor.Level.BODY : defLevel);
    }

    public static boolean getWriteAble() {
        return mWrite;
    }

    public static void cleanLog() {
        if (mFile != null) {
            FileUtil.INSTANCE.clear(mFile);
        }
    }

    public static String readLog() {
        if (mFile != null) {
            return FileUtil.INSTANCE.readFile(mFile);
        }
        return null;
    }

    private static HttpLoggingInterceptor mInterceptor = new HttpLoggingInterceptor(new WriterLogger());

    public static HttpLoggingInterceptor getInterceptor() {
        return mInterceptor;
    }

    static class WriterLogger implements HttpLoggingInterceptor.Logger {

        @Override
        public void log(@NotNull String message) {
            Platform.get().log(message, Platform.INFO, null);
            LoggerController.log(message);
        }
    }

}
