package com.lxg.work.retrofit.re.net;

import com.lxg.work.retrofit.re.base.BaseActivity;
import com.lxg.work.retrofit.re.base.BaseFragment;
import com.lxg.work.retrofit.re.base.BaseFragmentActivity;
import com.trello.rxlifecycle3.LifecycleProvider;
import com.trello.rxlifecycle3.components.RxActivity;
import com.trello.rxlifecycle3.components.RxFragment;
import com.trello.rxlifecycle3.components.support.RxFragmentActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Lxg on 2018/5/24 0024.
 */
public class RxManager {

    private BaseActivity rxActivity;
    private BaseFragmentActivity rxFragmentActivity;
    private BaseFragment rxFragment;

    public RxManager(LifecycleProvider lifecycleProvider) {
        //判断顺序严禁修改!
        if (lifecycleProvider instanceof BaseFragmentActivity) {
            this.rxFragmentActivity = (BaseFragmentActivity) lifecycleProvider;
        } else if (lifecycleProvider instanceof BaseActivity) {
            this.rxActivity = (BaseActivity) lifecycleProvider;
        } else if (lifecycleProvider instanceof BaseFragment) {
            this.rxFragment = (BaseFragment) lifecycleProvider;
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
        return upstream -> {
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
        };
    }
}
