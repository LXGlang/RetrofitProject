package com.lxg.work.retrofit.net;

import android.content.Context;
import android.support.annotation.CallSuper;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lxg.work.retrofit.util.LogUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


public abstract class CuObserver<T> implements Observer<HttpResult<T>> {
    private final int RESPONSE_CODE_OK = 200;       //自定义的业务逻辑，成功返回积极数据
    private final int RESPONSE_CODE_FAILED = -1;  //返回数据失败,严重的错误
    private Context mContext;
    private static Gson gson = new Gson();
    private int errorCode;
    private String errorMsg = "未知的错误！";
    private boolean showProgress;

    /**
     * 根据具体的Api 业务逻辑去重写 onSuccess 方法！Error 是选择重写，but 必须Super ！
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * @param mContext
     * @param showProgress 默认需要显示进程，不要的话请传 false
     */
    public CuObserver(Context mContext, boolean showProgress) {
        this.mContext = mContext;
        this.showProgress = showProgress;
        // TODO: 2018/5/25 0025 判断是否显示dialog 
    }


    @Override
    public final void onSubscribe(Disposable d) {
    }

    @Override
    public final void onNext(HttpResult<T> response) {
        if (response.getReturnCode() == RESPONSE_CODE_OK/* || response.getReturnCode() == RESPONSE_CODE_NULL*/) {
            onSuccess(response.getReturnData());
        } else {
            onFailure(response.getReturnCode(), response.getReturnMsg());
        }
    }

    @Override
    public final void onError(Throwable t) {
        LogUtils.i("网络访问错误" + t.toString());
        t.printStackTrace();
        LogUtils.i("网络错误信息:" + t.toString());

        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            errorCode = httpException.code();
            errorMsg = httpException.getMessage();
            LogUtils.i("当属于HttpException时:错误代码为:" + httpException.code() + "错误描述为:" + httpException.message());
            getErrorMsg(httpException);
        } else if (t instanceof SocketTimeoutException) {  //VPN open
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "服务器响应超时";
        } else if (t instanceof ConnectException) {
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "网络连接异常，请检查网络";
        } else if (t instanceof JsonSyntaxException) {
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "数据返回格式错误";
            t.printStackTrace();
        } else if (t instanceof NullPointerException) {
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "网络传输信息丢失";
        } else if (t instanceof RuntimeException) {
            if (errorCode != 401) {
                errorCode = RESPONSE_CODE_FAILED;
                errorMsg = "运行时错误";
            }
        } else if (t instanceof UnknownHostException) {
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "无法解析主机，请检查网络连接";
        } else if (t instanceof UnknownServiceException) {
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "未知的服务器错误";
        } else if (t instanceof IOException) {  //飞行模式等
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "没有网络，请检查网络连接";
        }
        LogUtils.i("报错内容:" + errorCode + errorMsg);
        onFailure(errorCode, errorMsg);
    }

    /**
     * 简单的把Dialog 处理掉
     */
    @Override
    public final void onComplete() {

    }

    /**
     * Default error dispose!
     * 一般的就是 AlertDialog 或 SnackBar
     *
     * @param code
     * @param message
     */
    @CallSuper  //if overwrite,you should let it run.
    public void onFailure(int code, String message) {
        // TODO: 2018/5/25 0025 错误提示
    }

    /**
     * 获取详细的错误的信息 errorCode,errorMsg ,   这里和Api 结构有关，这里和Api 结构有关 ！
     * 以登录的时候的Grant_type 故意写错为例子,http 应该是直接的返回401=httpException.code()
     * 但是是怎么导致401的？我们的服务器会在respose.errorBody 中的content 中说明
     */

    private final void getErrorMsg(HttpException h) {
        String errorBodyStr = "";
        LogUtils.i("错误信息----------" + h.response().errorBody());
        if (h != null) {
            try {
                errorBodyStr = h.response().errorBody().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                HttpResult errorResponse = gson.fromJson(errorBodyStr, HttpResult.class);
                if (null != errorResponse) {
                    errorCode = errorResponse.getReturnCode();
                    errorMsg = errorResponse.getReturnMsg();
                } else {
                    errorCode = h.code();
                    errorMsg = "网络访问错误!";
                }
            } catch (Exception jsonException) {
                errorCode = RESPONSE_CODE_FAILED;
                errorMsg = "http请求错误Json 信息异常";
                jsonException.printStackTrace();
            }

        } else {
            errorCode = RESPONSE_CODE_FAILED;
            errorMsg = "http请求错误Json 信息异常";
        }
    }
}