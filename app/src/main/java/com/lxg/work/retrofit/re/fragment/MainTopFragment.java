package com.lxg.work.retrofit.re.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxg.work.retrofit.R;
import com.lxg.work.retrofit.re.MainActivity;
import com.lxg.work.retrofit.re.adapter.BaseRecyclerAdapter;
import com.lxg.work.retrofit.re.base.BaseFragment;
import com.lxg.work.retrofit.re.entity.response.WanAndroidBean;
import com.lxg.work.retrofit.re.net.HttpUtils;
import com.lxg.work.retrofit.re.net.MyObserver;
import com.lxg.work.retrofit.re.rxbean.TestBean;
import com.lxg.work.retrofit.re.util.RxBus;
import com.lxg.work.retrofit.re.view.RecyclerViewAtViewPager2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;

/**
 * @ProjectName: RetrofitProject
 * @Package: com.lxg.work.retrofit.re.fragment
 * @ClassName: MainLeftFragment
 * @Description:
 * @Author: lxg
 * @CreateDate: 2020/5/21 14:42
 */
public class MainTopFragment extends BaseFragment {
    private static String ARG_PARAM = "TopFragment";
    private static MainTopFragment topFragment;
    @BindView(R.id.bt_test)
    Button btTest;
    @BindView(R.id.rlv_test)
    RecyclerViewAtViewPager2 rlvTest;

    public static MainTopFragment newInstance(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM, str);
        if (topFragment == null) {
            topFragment = new MainTopFragment();
        }
        topFragment.setArguments(bundle);   //设置参数
        return topFragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintop, container, false);
        ButterKnife.bind(this, view);
        btTest.setOnClickListener(this);
        return view;
    }

    @Override
    public void loadData() {
        HttpUtils.getInstance().testandroid(this, new MyObserver<WanAndroidBean>() {
            @Override
            public void onStart(Disposable d) {

            }

            @Override
            public void onSuccess(WanAndroidBean o) throws Exception {
                List<WanAndroidBean.DataBean> data = o.getData();
                BaseRecyclerAdapter baseRecyclerAdapter = new BaseRecyclerAdapter(R.layout.item_main_rl, data);
                rlvTest.setLayoutManager(new LinearLayoutManager(getActivity()));
                rlvTest.setAdapter(baseRecyclerAdapter);
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_test:
                RxBus.getDefault().post(new TestBean("这是一条从TopFragment发送的数据,请收好~"));
                ((MainActivity) getActivity()).setVpItem(1);
                break;
        }
    }
}
