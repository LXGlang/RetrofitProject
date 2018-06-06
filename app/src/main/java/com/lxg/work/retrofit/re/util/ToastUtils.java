package com.lxg.work.retrofit.re.util;

import android.widget.Toast;

import com.lxg.work.retrofit.APP;

/**
 * Created by Lxg on 2017/5/17.
 * 吐司工具类
 */

public class ToastUtils {
    public static Toast toast;

    public static void showToast(String string) {
        if (toast == null) {
            toast = Toast.makeText(APP.getContext(), string, Toast.LENGTH_SHORT);
        } else {
            toast.cancel();
            toast = Toast.makeText(APP.getContext(), string, Toast.LENGTH_SHORT);
            toast.setText(string);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

}
