package com.lxg.work.retrofit.re.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxg.work.retrofit.R;
import com.lxg.work.retrofit.re.entity.response.WanAndroidBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @ProjectName: RetrofitProject
 * @Package: com.lxg.work.retrofit.re.base
 * @ClassName: BaseRecyclerAdapter
 * @Description:
 * @Author: lxg
 * @CreateDate: 2020/5/21 10:19
 */
public class BaseRecyclerAdapter extends BaseQuickAdapter<WanAndroidBean.DataBean, BaseViewHolder> {
    public BaseRecyclerAdapter(int layoutResId, @Nullable List<WanAndroidBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, WanAndroidBean.DataBean dataBean) {
        baseViewHolder.setText(R.id.tv_name, dataBean.getName());
    }
}
