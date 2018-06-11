package com.lxg.work.retrofit.re.net;

import com.google.gson.JsonSyntaxException;
import com.lxg.work.retrofit.APP;
import com.lxg.work.retrofit.re.util.LogUtils;
import com.lxg.work.retrofit.re.util.ToastUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Lxg on 2018/5/24 0024.
 */
public abstract class MyObserver<T> implements Observer<T> {

    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        onStart(d);
    }

    /**
     * 开始网络访问
     */
    public abstract void onStart(Disposable d);

    @Override
    public void onNext(T o) {
        onSuccess(o);
        cancelRequest();
    }

    /**
     * 请求成功
     *
     * @param o
     */
    public abstract void onSuccess(T o);

    @Override
    public void onError(Throwable e) {
        String errorMsg;
        //不要随便修改判断位置
        if (e instanceof HttpException) {
            errorMsg = "网络开小差了呢";
        } else if (e instanceof SocketTimeoutException) {
            errorMsg = "服务器响应超时";
        } else if (e instanceof ConnectException) {
            errorMsg = "网络连接异常，请检查网络";
        } else if (e instanceof JsonSyntaxException) {
            errorMsg = "数据返回格式错误";
        } else if (e instanceof NullPointerException) {
            errorMsg = "网络传输信息丢失";
        } else if (e instanceof RuntimeException) {
            errorMsg = "运行时错误";
        } else if (e instanceof UnknownHostException) {
            errorMsg = "无法解析主机，请检查网络连接";
        } else if (e instanceof UnknownServiceException) {
            errorMsg = "未知的服务器错误";
        } else if (e instanceof IOException) {  //其他情况
            errorMsg = "没有网络，请检查网络连接";
        } else {
            errorMsg = "未提前预料到的错误";
        }
        ToastUtils.showToast(errorMsg);
        LogUtils.e(e.toString());
        e.printStackTrace();
        onFailure(errorMsg);
        cancelRequest();
    }

    /**
     * 请求失败处理
     *
     * @param errorMsg
     */
    public abstract void onFailure(String errorMsg);

    @Override
    public void onComplete() {

    }

    /**
     * 取消请求
     */
    public void cancelRequest() {
        if (disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
