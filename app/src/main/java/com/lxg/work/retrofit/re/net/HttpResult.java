package com.lxg.work.retrofit.re.net;

import java.io.IOException;

import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lxg on 17/5/24.
 */

public class HttpResult<T> implements Call<T> {

    /**
     * returnCode : 200
     * returnMsg : 获取工程列表成功！
     * returnData : {}
     */

    private int returnCode;
    private String returnMsg;
    private T returnData;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public T getReturnData() {
        return returnData;
    }

    public void setReturnData(T returnData) {
        this.returnData = returnData;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "returnCode=" + returnCode +
                ", returnMsg='" + returnMsg + '\'' +
                ", returnData=" + returnData +
                '}';
    }

    @Override
    public Response<T> execute() throws IOException {
        return null;
    }

    @Override
    public void enqueue(Callback<T> callback) {

    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public Call<T> clone() {
        return null;
    }

    @Override
    public Request request() {
        return null;
    }

    @Override
    public Timeout timeout() {
        return null;
    }
}
