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

import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

/**
 * 异常处理
 * Created by Lxg on 2018/5/24 0024.
 */
public class MyThrowableConsumer implements Consumer<Throwable> {
    private String errorMsg = "未知的错误！";

    @Override
    public void accept(Throwable t) throws Exception {
        LogUtils.i("网络访问错误" + t.toString());
        t.printStackTrace();
        LogUtils.i("网络错误信息:" + t.toString());

        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            errorMsg = httpException.getMessage();
            LogUtils.i("当属于HttpException时:错误代码为:" + httpException.code() + "错误描述为:" + httpException.message());
        } else if (t instanceof SocketTimeoutException) {  //VPN open
            errorMsg = "服务器响应超时";
        } else if (t instanceof ConnectException) {
            errorMsg = "网络连接异常，请检查网络";
        } else if (t instanceof JsonSyntaxException) {
            errorMsg = "数据返回格式错误";
            t.printStackTrace();
        } else if (t instanceof RuntimeException) {
            errorMsg = "运行时错误";
        } else if (t instanceof NullPointerException) {
            errorMsg = "网络传输信息丢失";
        } else if (t instanceof UnknownHostException) {
            errorMsg = "无法解析主机，请检查网络连接";
        } else if (t instanceof UnknownServiceException) {
            errorMsg = "未知的服务器错误";
        } else if (t instanceof IOException) {  //飞行模式等
            errorMsg = "没有网络，请检查网络连接";
        }
        t.printStackTrace();
        LogUtils.i("报错内容:" + errorMsg);
        ToastUtils.showToast(errorMsg);
    }
}
