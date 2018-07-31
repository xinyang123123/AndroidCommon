package com.xinyang.xycommon.ui;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xinyang.xycommon.R;
import com.xinyang.xycommon.entity.home.HomeArticleBean;

import java.util.List;

public class HomeArticleAdapter extends BaseQuickAdapter<HomeArticleBean, BaseViewHolder> {

    public HomeArticleAdapter(int layoutResId, @Nullable List<HomeArticleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeArticleBean item) {
        //标题
        helper.setText(R.id.tv_title, item.getTitle());
        //描述
        helper.setText(R.id.tv_desc, item.getDesc());
    }
}
