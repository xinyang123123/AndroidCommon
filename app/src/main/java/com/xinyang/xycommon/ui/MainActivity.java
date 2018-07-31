package com.xinyang.xycommon.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xinyang.xycommon.R;
import com.xinyang.xycommon.base.BaseActivity;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<HomePresenter> implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    private HomeArticleAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void init() {
        initSrl();
        initRv();
        mPresenter.getListData(false);
    }


    @Override
    public void initSrl() {
        mSrl.setOnRefreshListener(this);
    }

    @Override
    public void initRv() {
        mAdapter = new HomeArticleAdapter(R.layout.item_home_article, null);
        mAdapter.setOnLoadMoreListener(this, mRv);

        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void showRefreshLoading() {
        mSrl.setRefreshing(true);
    }

    @Override
    public void hideRefreshLoading() {
        mSrl.setRefreshing(false);
    }

    @Override
    public void setData(List list, int loadType) {
        setLoadDataResult(mAdapter, mSrl, list, loadType);
    }


    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getListData(true);
    }
}
