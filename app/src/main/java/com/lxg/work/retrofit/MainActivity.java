package com.lxg.work.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lxg.work.retrofit.base.BaseActivity;
import com.lxg.work.retrofit.entity.response.Movie;
import com.lxg.work.retrofit.net.HttpUtils;
import com.lxg.work.retrofit.net.MyObserver;
import com.lxg.work.retrofit.util.LogUtils;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    private TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_test = findViewById(R.id.bt_test);
        Button bt_test1 = findViewById(R.id.bt_test1);
        Button bt_test2 = findViewById(R.id.bt_test2);
        tv_test = findViewById(R.id.tv_test);
        bt_test.setOnClickListener(this);
        bt_test1.setOnClickListener(this);
        bt_test2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_test:
                HttpUtils.getInstance().test(this, new MyObserver<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        MainActivity.this.finish();
                        /*if (d.isDisposed()) {
                            d.dispose();
                        }*/
                    }

                    @Override
                    public void onNext(Movie o) {
                        LogUtils.List(o.getSubjects());
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e(HttpUtils.map2StringEn("测试","1","啊啊啊","耶耶耶").toString());
                    }
                }, 0, 1);
                break;
            case R.id.bt_test1:
                HttpUtils.getInstance().test1(this, new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        tv_test.setText(movie.getSubjects().toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, 0, 1);
                break;
            case R.id.bt_test2:
                HttpUtils.getInstance().test2(this, new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        tv_test.setText(movie.getSubjects().toString());
                    }
                }, 0, 1);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }
}
