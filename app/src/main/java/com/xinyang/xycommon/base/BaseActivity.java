package com.xinyang.xycommon.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.xinyang.xycommon.R;
import com.xinyang.xycommon.constant.Constant;
import com.xinyang.xycommon.entity.LoadType;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends BaseContract.Presenter> extends RxAppCompatActivity
        implements BaseContract.View {

    protected abstract int getLayoutId();

    protected abstract P getPresenter();

    protected abstract void init();


    protected P mPresenter;
    protected Unbinder mUnBinder;
    protected MultiStateView mBaseSv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局
        int layoutId = getLayoutId();
        setContentView(layoutId);

        //黄油刀
        mUnBinder = ButterKnife.bind(this);
        //设置Presenter
        mPresenter = getPresenter();
        //依附View
        attachView();
        //初始化
        init();
        //设置错误状态下重试点击事件
        setErrorClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }

        //分离View
        detachView();
    }

    @Override
    public void setContentView(int layoutResId) {
        View baseView = getLayoutInflater().inflate(R.layout.activity_base, null);
        //获取存放内容的控件
        FrameLayout flContent = baseView.findViewById(R.id.base_content);
        //获取子类setContentView设置的布局
        View childView = getLayoutInflater().inflate(layoutResId, null);
        //将子类需要展示的内容放到flContent中
        flContent.addView(childView);
        //切换状态
        mBaseSv = baseView.findViewById(R.id.base_sv);

        super.setContentView(baseView);
    }

    /**
     * 设置错误状态下重试点击事件
     */
    private void setErrorClick() {
        View errorView = getStateView().getView(MultiStateView.VIEW_STATE_ERROR);

        if (errorView == null) {
            return;
        }
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRetry();
            }
        });
    }

    /**
     * 绑定View层实现
     */
    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public MultiStateView getStateView() {
        return mBaseSv;
    }

    @Override
    public void setViewState(int state) {
        getStateView().setViewState(state);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onRetry() {
        init();
    }

    @Override
    public void showTip(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setStateBar() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    /**
     * 设置加载数据结果
     *
     * @param adapter
     * @param refreshLayout
     * @param list
     * @param loadType
     */
    protected void setLoadDataResult(BaseQuickAdapter adapter, SwipeRefreshLayout refreshLayout,
                                     List list, @LoadType.checker int loadType) {
        if (adapter == null) {
            return;
        }
        switch (loadType) {
            case LoadType.REFRESH_SUCCESS:
                adapter.setNewData(list);
                if (refreshLayout != null) {
                    refreshLayout.setRefreshing(false);
                }
                break;
            case LoadType.REFRESH_ERROR:
                if (refreshLayout != null) {
                    refreshLayout.setRefreshing(false);
                }
                break;
            case LoadType.LOAD_MORE_SUCCESS:
                if (list != null) {
                    adapter.addData(list);
                }
                break;
            case LoadType.LOAD_MORE_ERROR:
                adapter.loadMoreFail();
                break;
            default:
        }
        //设置加载更多状态
        if (list == null || list.isEmpty() || list.size() < Constant.PAGE_SIZE) {
            adapter.loadMoreEnd(true);
        } else {
            adapter.loadMoreComplete();
        }

        //设置布局状态
        if (getStateView() != null) {
            if (loadType == LoadType.REFRESH_SUCCESS || loadType == LoadType.REFRESH_ERROR) {
                if (list == null) {
                    getStateView().setViewState(MultiStateView.VIEW_STATE_ERROR);
                    return;
                }
                if (list.isEmpty()) {
                    getStateView().setViewState(MultiStateView.VIEW_STATE_EMPTY);
                    return;
                }
                getStateView().setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }
        }
    }
}
