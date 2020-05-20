package com.lxg.work.retrofit;

import android.app.Application;
import android.content.Context;

import androidx.annotation.StringRes;

/**
 * Created by Lxg on 2018/5/25 0025.
 */
public class APP extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 返回自定义的String串
     *
     * @param resId
     * @return
     */
    public static String getAPPString(@StringRes int resId) {
        return context.getString(resId, "");
    }
}
