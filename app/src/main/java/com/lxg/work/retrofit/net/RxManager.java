package com.lxg.work.retrofit.net;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.components.RxActivity;
import com.trello.rxlifecycle2.components.RxFragment;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Lxg on 2018/5/24 0024.
 */
public class RxManager {

    private RxActivity rxActivity;
    private RxFragmentActivity rxFragmentActivity;
    private RxFragment rxFragment;

    public RxManager(LifecycleProvider lifecycleProvider) {
        //判断顺序严禁修改!
        if (lifecycleProvider instanceof RxFragmentActivity) {
            this.rxFragmentActivity = (RxFragmentActivity) lifecycleProvider;
        } else if (lifecycleProvider instanceof RxActivity) {
            this.rxActivity = (RxActivity) lifecycleProvider;
        } else if (lifecycleProvider instanceof RxFragment) {
            this.rxFragment = (RxFragment) lifecycleProvider;
        } else {
            throw new RuntimeException("你咋这么牛!重新传值!");
        }
    }



    /**
     * 对请求进行生命周期绑定,解决因页面关闭造成的内存泄漏
     *
     * @return
     */
    public ObservableTransformer setIoManager() {
        return new ObservableTransformer() {
            @Override
            public ObservableSource apply(Observable upstream) {
                if (rxFragmentActivity != null) {
                    return upstream.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .compose(rxFragmentActivity.bindToLifecycle());
                } else if (rxActivity != null) {
                    return upstream.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .compose(rxActivity.bindToLifecycle());
                } else if (rxFragment != null) {
                    return upstream.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .compose(rxFragment.bindToLifecycle());
                } else {
                    throw new RuntimeException("你咋这么牛!重新传值!");
                }
            }
        };
    }
}
