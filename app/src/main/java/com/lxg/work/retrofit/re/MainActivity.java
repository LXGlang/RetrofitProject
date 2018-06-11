package com.lxg.work.retrofit.re;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lxg.work.retrofit.R;
import com.lxg.work.retrofit.re.base.BaseActivity;
import com.lxg.work.retrofit.re.entity.response.Movie;
import com.lxg.work.retrofit.re.net.HttpUtils;
import com.lxg.work.retrofit.re.net.MyThrowableConsumer;
import com.lxg.work.retrofit.re.net.MyObserver;
import com.lxg.work.retrofit.re.util.LogUtils;
import com.lxg.work.retrofit.re.util.ToastUtils;
import com.lxg.work.retrofit.re.widget.MyDialogFragment;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    private TextView tv_test;
    private Disposable d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_test = findViewById(R.id.bt_test);
        Button bt_test1 = findViewById(R.id.bt_test1);
        Button bt_test2 = findViewById(R.id.bt_test2);
        Button bt_test3 = findViewById(R.id.bt_test3);
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
                    public void onStart(Disposable disposable) {
//                        cancelRequest();
                    }

                    @Override
                    public void onSuccess(Movie o) {
                        LogUtils.List(o.getSubjects());
                        tv_test.setText(o.toString());
                        o.getSubjects().get(101);
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        ToastUtils.showToast(errorMsg);
                    }

                }, 0, 1);
                break;
            case R.id.bt_test1:
                HttpUtils.getInstance().test1(this, new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        tv_test.setText(movie.getSubjects().toString());
                    }
                }, new MyThrowableConsumer() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                }, 0, 1);
                break;
            case R.id.bt_test2:
                HttpUtils.getInstance().test2(this, new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {

                    }
                }, 0, 1);
                break;
            case R.id.bt_test3:
                HttpUtils.getInstance().test2(this, new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
