package com.lxg.work.retrofit.re;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.lxg.work.retrofit.R;
import com.lxg.work.retrofit.re.adapter.VpMainFragmentAdapter;
import com.lxg.work.retrofit.re.base.BaseFragment;
import com.lxg.work.retrofit.re.base.BaseFragmentActivity;
import com.lxg.work.retrofit.re.fragment.MainBottomFragment;
import com.lxg.work.retrofit.re.fragment.MainTopFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.vp2_main)
    ViewPager2 vp2Main;

    @Override
    public void getIntentData(Intent intent) {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        List<BaseFragment> mFragments = new ArrayList<>();
        mFragments.add(MainTopFragment.newInstance("top_1"));
        mFragments.add(MainBottomFragment.newInstance("top_2"));
        VpMainFragmentAdapter mAdapter = new VpMainFragmentAdapter(this, mFragments);
        vp2Main.setAdapter(mAdapter);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 切换页面
     *
     * @param item
     */
    public void setVpItem(int item) {
        vp2Main.setCurrentItem(item);
    }
}
