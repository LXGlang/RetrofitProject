package com.lxg.work.retrofit.re.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.annotations.Nullable;
import io.reactivex.rxjava3.annotations.NonNull;

/**
 * Created by Lxg on 2018/5/24 0024.
 */
public abstract class BaseFragment extends SupperBaseFragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(@androidx.annotation.Nullable Bundle savedInstanceState) {
        loadData();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 初始化控件
     */
    public abstract View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 加载数据
     */
    public abstract void loadData();

}
