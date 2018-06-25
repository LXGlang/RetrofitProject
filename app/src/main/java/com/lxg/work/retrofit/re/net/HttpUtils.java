package com.lxg.work.retrofit.re.net;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lxg.work.retrofit.BuildConfig;
import com.lxg.work.retrofit.re.entity.response.Movie;
import com.lxg.work.retrofit.re.util.LogUtils;
import com.lxg.work.retrofit.re.util.ToastUtils;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.io.IOException;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by Lxg on 2018/5/24 0024.
 */
public class HttpUtils {

    @android.support.annotation.NonNull
    private static HttpUtils instance = new HttpUtils();
    private LHApi lhApi;
    private Interceptor interceptor;

    // 可重试次数
    private int maxConnectCount = 7;
    // 当前已重试次数
    private int currentRetryCount = 0;

    public static final String FINAL_URL = "https://api.douban.com/v2/movie/";

    @android.support.annotation.NonNull
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
     * @param myObserver
     */
    public void test(LifecycleProvider lifecycleProvider, @android.support.annotation.NonNull MyObserver<Movie> myObserver, int start, int count) {
        Observable processList = instance.lhApi.getTopMovie(start, count);
        to(processList, myObserver, new RxManager(lifecycleProvider).setIoManager());
    }

    /**
     * 测试方法
     *
     * @param lifecycleProvider
     * @param observable
     */
    public void test1(LifecycleProvider lifecycleProvider, @android.support.annotation.NonNull Consumer<Movie> observable, @android.support.annotation.NonNull MyThrowableConsumer throwableConsumer, int start, int count) {
        Observable processList = instance.lhApi.getTopMovie(start, count);
        to(observable, throwableConsumer, processList, new RxManager(lifecycleProvider).setIoManager());
    }

    /**
     * 测试方法
     *
     * @param lifecycleProvider
     * @param observable
     */
    public void test2(LifecycleProvider lifecycleProvider, @android.support.annotation.NonNull Consumer<Movie> observable, int start, int count) {
        Observable processList = instance.lhApi.getTopMovie(start, count);
        to(observable, processList, new RxManager(lifecycleProvider).setIoManager());
    }

    /**
     * 处理全部事件
     *
     * @param tObservable   处理全部事件
     * @param observer
     * @param <T>
     */
    private <T> void to(Observable<T> tObservable, @android.support.annotation.NonNull Observer<T> observer, @android.support.annotation.NonNull ObservableTransformer transformer) {
        tObservable
                .compose(transformer)
                .subscribe(observer);
    }

    /**
     * 仅处理成功事件
     *
     * @param tConsumer  成功事件的处理
     * @param observable
     * @param <T>
     */
    private <T> void to(@android.support.annotation.NonNull Consumer<T> tConsumer, Observable<T> observable, @android.support.annotation.NonNull ObservableTransformer transformer) {
        observable
                .compose(transformer)
                .subscribe(tConsumer);
    }

    /**
     * 仅处理成功和失败事件
     *
     * @param tConsumer         成功事件的处理
     * @param throwableConsumer 失败事件的处理
     * @param observable
     * @param <T>
     */
    private <T> void to(@android.support.annotation.NonNull Consumer<T> tConsumer, @android.support.annotation.NonNull MyThrowableConsumer throwableConsumer, Observable<T> observable, @android.support.annotation.NonNull ObservableTransformer transformer) {
        observable
                .compose(transformer)
                .subscribe(tConsumer, throwableConsumer);
    }

    /**
     * 请求级联嵌套,处理成功与失败逻辑
     *
     * @param observable1 第一个请求
     * @param consumer    第一个请求的成功处理
     * @param function    第二个请求
     * @param myObserver  第二次请求的处理和失败逻辑处理
     * @param transformer
     * @param <T>         第一个请求结果
     * @param <H>         第二个请求结果
     */
    private <T, H> void twoRequests(Observable<T> observable1, @android.support.annotation.NonNull Consumer<T> consumer, @android.support.annotation.NonNull Function<T, Observable<H>> function, @android.support.annotation.NonNull MyObserver<H> myObserver, @android.support.annotation.NonNull ObservableTransformer transformer) {
        observable1.compose(transformer)
                .doOnNext(consumer)
                .observeOn(Schedulers.io())
                .flatMap(function)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myObserver);
    }

    /**
     * 错误延时重连
     *
     * @param tObservable
     * @param observer
     * @param transformer
     * @param <T>
     */
    private <T> void errorto(Observable<T> tObservable, @android.support.annotation.NonNull MyObserver<T> observer, @android.support.annotation.NonNull ObservableTransformer transformer) {
        currentRetryCount = 0;
        tObservable.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@android.support.annotation.NonNull @NonNull Observable<Throwable> throwableObservable) throws Exception {
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                        if (throwable instanceof IOException || throwable instanceof HttpException) {
                            //当且仅当网络错误才处理
                            if (++currentRetryCount > maxConnectCount) {
                                //尝试次数已达最大
                                LogUtils.e(throwable.toString());
                                throwable.printStackTrace();
                                return Observable.error(throwable);
                            } else {
                                ToastUtils.showToast("网络异常,开始重新尝试,当前正在重试第" + currentRetryCount + "次");
//                                return Observable.just(0).delay(3,TimeUnit.SECONDS);
                                return Observable.timer(3, TimeUnit.SECONDS);
                            }
                        } else {
                            //普通异常不在尝试重新获取数据
                            LogUtils.e(throwable.toString());
                            throwable.printStackTrace();
                            return Observable.error(throwable);
                        }
                    }
                });

            }
        }).compose(transformer)
                .subscribe(observer);
    }

    /**
     * 用于进行参数的转换
     *
     * @param params
     * @return
     */
    @android.support.annotation.NonNull
    public static Map<String, String> map2StringEn(@Nullable String... params) {
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
