package com.lxg.work.retrofit.net;

import com.lxg.work.retrofit.BuildConfig;
import com.lxg.work.retrofit.entity.response.Movie;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Lxg on 2018/5/24 0024.
 */
public class HttpUtils {

    private static HttpUtils instance = new HttpUtils();
    private LHApi lhApi;
    private Interceptor interceptor;

    public static final String FINAL_URL = "https://api.douban.com/v2/movie/";

    public static HttpUtils getInstance() {
        return instance;
    }

    private HttpUtils() {
        OkHttpClient builder;
        interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder builder;
                builder = request.newBuilder();
                Request newrequest = builder.build();
                return chain.proceed(newrequest);
            }
        };

        OkHttpClient.Builder newBuilder = new OkHttpClient().newBuilder();
        //非测试环境则关闭日志输出
        if (BuildConfig.DEBUG) {
            newBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        builder = newBuilder
                .addInterceptor(interceptor)
                .build();

        //转换为实体类
        lhApi = new Retrofit.Builder()
                .baseUrl(FINAL_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(CuConvertFactory.create())
                .client(builder)
                .build()
                .create(LHApi.class);
        /*
        //转换为基本类型
        lhApi = new Retrofit.Builder()
                .baseUrl(FINAL_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(builder)
                .build()
                .create(LHApi.class);*/
    }

    /**
     * 测试方法
     *
     * @param lifecycleProvider
     * @param observable
     */
    public void test(LifecycleProvider lifecycleProvider, MyObserver<Movie> observable, int start, int count) {
        Observable processList = instance.lhApi.getTopMovie(start, count);
        to(processList, observable, new RxManager(lifecycleProvider).setIoManager());
    }

    /**
     * 测试方法
     *
     * @param lifecycleProvider
     * @param observable
     */
    public void test1(LifecycleProvider lifecycleProvider, Consumer<Movie> observable, Consumer<Throwable> throwableConsumer, int start, int count) {
        Observable processList = instance.lhApi.getTopMovie(start, count);
        to(observable, throwableConsumer, processList, new RxManager(lifecycleProvider).setIoManager());
    }

    /**
     * 测试方法
     *
     * @param lifecycleProvider
     * @param observable
     */
    public void test2(LifecycleProvider lifecycleProvider, Consumer<Movie> observable, int start, int count) {
        Observable processList = instance.lhApi.getTopMovie(start, count);
        to(observable, processList, new RxManager(lifecycleProvider).setIoManager());
    }

    /**
     * 处理全部事件
     *
     * @param observer   处理全部事件
     * @param observable
     * @param <T>
     */
    private <T> void to(Observable<T> observer, Observer<T> observable, ObservableTransformer transformer) {
        observer
                .compose(transformer)
                .subscribe(observable);
    }

    /**
     * 仅处理成功事件
     *
     * @param tConsumer  成功事件的处理
     * @param observable
     * @param <T>
     */
    private <T> void to(Consumer<T> tConsumer, Observable<T> observable, ObservableTransformer transformer) {
        observable
                .compose(transformer)
                .subscribe(tConsumer, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    /**
     * 仅处理成功和失败事件
     *
     * @param tConsumer         成功事件的处理
     * @param throwableConsumer 失败事件的处理
     * @param observable
     * @param <T>
     */
    private <T> void to(Consumer<T> tConsumer, Consumer<Throwable> throwableConsumer, Observable<T> observable, ObservableTransformer transformer) {
        observable
                .compose(transformer)
                .subscribe(tConsumer, throwableConsumer);
    }

    /**
     * 用于进行参数的转换
     *
     * @param params
     * @return
     */
    public static Map<String, String> map2StringEn(String... params) {
        if (params == null) {
            throw new NullPointerException("参数不可为空");
        }
        if (params.length % 2 != 0) {
            throw new RuntimeException("传参异常,请检查参数拼接");
        }
        Map<String, String> map = new HashMap();
        for (int i = 0; i < params.length; i = i + 2) {
            map.put(params[i], params[1 + i]);
        }
        return map;
    }
}
