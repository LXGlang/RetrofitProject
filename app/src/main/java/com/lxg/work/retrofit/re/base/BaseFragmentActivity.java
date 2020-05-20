package com.lxg.work.retrofit.re.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.trello.rxlifecycle3.components.support.RxFragmentActivity;


/**
 * Created by Lxg on 2018/5/24 0024.
 */
public abstract class BaseFragmentActivity extends RxFragmentActivity implements View.OnClickListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntentData(getIntent());
        initView();
        loadData();
    }

    /**
     * 获取上个页面传递过来的数据
     *
     * @param intent
     */
    public abstract void getIntentData(Intent intent);

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 加载数据
     */
    public abstract void loadData();

    @Override
    public abstract void onClick(View v);
}
