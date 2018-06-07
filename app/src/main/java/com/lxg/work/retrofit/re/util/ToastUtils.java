package com.lxg.work.retrofit.re.util;

import android.widget.Toast;

import com.lxg.work.retrofit.APP;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by Lxg on 2017/5/17.
 * 吐司工具类
 */

public class ToastUtils {
    public static Toast toast;

    public static void showToast(String string) {
        Observable.just(string)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (toast == null) {
                            toast = Toast.makeText(APP.getContext(), s, Toast.LENGTH_SHORT);
                        } else {
                            toast.cancel();
                            toast = Toast.makeText(APP.getContext(), s, Toast.LENGTH_SHORT);
                            toast.setText(s);
                            toast.setDuration(Toast.LENGTH_SHORT);
                        }
                        toast.show();

                    }
                });
    }

}
