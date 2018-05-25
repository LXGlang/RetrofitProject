package com.lxg.work.retrofit.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lxg.work.retrofit.util.LogUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 自定义gson 解析
 * 主要防止服务器出错。出现非json 的数据
 * <p>
 * Created by lxg on 2017/2/6.
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

        /**
         * 由于服务器传递过来的错误信息直接给用户看的话，
         * 用户未必能够理解
         * 需要根据错误码对错误信息进行一个转换，
         * 在显示给用户
         *
         * @return
         */
        @Override
        public T convert(ResponseBody value) {
            String response = null;
            try {
                response = value.string();
                if (TextUtils.isEmpty(response)) {
                    throw new ApiException(ApiException.SERVICE_ERROR, "返回数据错误");
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new ApiException(ApiException.SERVICE_ERROR, "返回数据错误");
            }
            T t = gson.fromJson(response, type);
//        LogUtils.JSON(response);
        return t;

    }
}
