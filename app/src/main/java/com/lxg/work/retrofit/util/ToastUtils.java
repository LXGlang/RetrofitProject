package com.lxg.work.retrofit.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Lxg on 2017/5/17.
 * 吐司工具类
 */

public class ToastUtils {
    public static Toast toast;

    public static void showToast(Context context, String string) {
        if (context == null) {
            return;
        } else {
            if (toast == null) {
                toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
            } else {
                toast.cancel();
                toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
                toast.setText(string);
                toast.setDuration(Toast.LENGTH_SHORT);
            }
        }
        toast.show();
    }

}
