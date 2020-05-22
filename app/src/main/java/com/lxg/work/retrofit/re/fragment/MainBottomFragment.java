package com.lxg.work.retrofit.re.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxg.work.retrofit.R;
import com.lxg.work.retrofit.re.base.BaseFragment;
import com.lxg.work.retrofit.re.net.MyObserver;
import com.lxg.work.retrofit.re.rxbean.TestBean;
import com.lxg.work.retrofit.re.util.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * @ProjectName: RetrofitProject
 * @Package: com.lxg.work.retrofit.re.fragment
 * @ClassName: MainBottomFragment
 * @Description:
 * @Author: lxg
 * @CreateDate: 2020/5/21 14:44
 */
public class MainBottomFragment extends BaseFragment {
    private static String ARG_PARAM = "BottomFragment";
    private static MainBottomFragment bottomFragment;

    @BindView(R.id.tv_botton_test)
    TextView tvBottonTest;

    public static MainBottomFragment newInstance(String str) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM, str);
        if (bottomFragment == null) {
            bottomFragment = new MainBottomFragment();
        }
        bottomFragment.setArguments(bundle);   //设置参数
        return bottomFragment;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainbotton, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void loadData() {
        RxBus.getDefault().toObservable(TestBean.class)
                .subscribe(testBean ->
                                tvBottonTest.setText(testBean.getMessage()),
                        throwable -> {
                            throwable.printStackTrace();
                        });
    }

    @Override
    public void onClick(View v) {

    }
}
