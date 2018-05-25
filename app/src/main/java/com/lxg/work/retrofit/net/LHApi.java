package com.lxg.work.retrofit.net;


import com.lxg.work.retrofit.entity.response.Movie;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Lxg on 2018/5/24 0024.
 */
public interface LHApi {

    @GET("top250")
    Observable<HttpResult<Movie>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
