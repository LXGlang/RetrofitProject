package com.lxg.work.retrofit.re;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lxg.work.retrofit.APP;
import com.lxg.work.retrofit.R;
import com.lxg.work.retrofit.re.base.BaseFragmentActivity;
import com.lxg.work.retrofit.re.base.ScreenAdaptationUtils;
import com.lxg.work.retrofit.re.entity.response.Movie;
import com.lxg.work.retrofit.re.entity.response.WanAndroidBean;
import com.lxg.work.retrofit.re.net.HttpUtils;
import com.lxg.work.retrofit.re.net.MyObserver;
import com.lxg.work.retrofit.re.net.MyThrowableConsumer;
import com.lxg.work.retrofit.re.util.LogUtils;
import com.lxg.work.retrofit.re.util.LoginInput;
import com.lxg.work.retrofit.re.util.ToastUtils;
import com.lxg.work.retrofit.re.widget.MyDialogFragment;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseFragmentActivity implements LoginInput {

    private TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ScreenAdaptationUtils.setCustomDensity(this, this.getApplication(), true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void getIntentData(Intent intent) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        Button bt_test = findViewById(R.id.bt_test);
        Button bt_test1 = findViewById(R.id.bt_test1);
        Button bt_test2 = findViewById(R.id.bt_test2);
        Button bt_test3 = findViewById(R.id.bt_test3);
        Button bt_test4 = findViewById(R.id.bt_test4);
        tv_test = findViewById(R.id.tv_test);
        bt_test.setOnClickListener(this);
        bt_test1.setOnClickListener(this);
        bt_test2.setOnClickListener(this);
        bt_test3.setOnClickListener(this);
        bt_test4.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_test:
                HttpUtils.getInstance().test(this, new MyObserver<Movie>() {
                    @Override
                    public void onStart(Disposable disposable) {
                    }

                    @Override
                    public void onSuccess(Movie o) {
                        LogUtils.List(o.getSubjects());
                        tv_test.setText(o.toString());
                        o.getSubjects().get(101);
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        MyDialogFragment myDialogFragment = new MyDialogFragment();
//                        myDialogFragment.show(getSupportFragmentManager(), "测试1");
//                        myDialogFragment.setinfo("出错啦!",errorMsg,null,null);
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
                LogUtils.e("测试dialog");
//                new MyDialogFragment().show(getSupportFragmentManager(), "login", "我是标题", "我是提示文本");
                startActivity(new Intent(MainActivity.this, MainTestActivity.class));
                break;
            case R.id.bt_test4:
                HttpUtils.getInstance().testandroid(this, new MyObserver<WanAndroidBean>() {
                    @Override
                    public void onStart(Disposable d) {

                    }

                    @Override
                    public void onSuccess(WanAndroidBean o) throws Exception {
                        LogUtils.e("获取到的数据为" + o);
                    }

                    @Override
                    public void onFailure(String errorMsg) {

                    }
                });
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

    @Override
    public void userLogin(String username, String password) {
        ToastUtils.showToast("当前登陆的用户为" + username + "使用的登录密码是" + password);
    }
}
