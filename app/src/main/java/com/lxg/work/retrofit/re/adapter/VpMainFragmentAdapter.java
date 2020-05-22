package com.lxg.work.retrofit.re.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.lxg.work.retrofit.re.base.BaseFragment;

import java.util.List;

/**
 * @ProjectName: RetrofitProject
 * @Package: com.lxg.work.retrofit.re.adapter
 * @ClassName: VpMainFragmentAdapter
 * @Description:
 * @Author: lxg
 * @CreateDate: 2020/5/21 15:21
 */
public class VpMainFragmentAdapter extends FragmentStateAdapter {
    List<BaseFragment> baseFragments;

    public VpMainFragmentAdapter(@NonNull FragmentActivity fragmentActivity, List<BaseFragment> fragments) {
        super(fragmentActivity);
        baseFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return baseFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return baseFragments.size();
    }
}
