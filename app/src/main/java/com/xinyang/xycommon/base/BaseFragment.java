package com.xinyang.xycommon.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kennyc.view.MultiStateView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.xinyang.xycommon.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BaseContract.Presenter> extends RxFragment implements BaseContract.View {

    protected abstract int getLayoutId();

    protected abstract P getPresenter();

    protected abstract void init();

    protected P mPresenter;
    protected Unbinder mUnBinder;
    protected MultiStateView mBaseSv;
    protected Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        //设置Presenter
        mPresenter = getPresenter();
        //依附View
        attachView();
        //设置错误状态下重试点击事件
        setErrorClick();
    }

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        //注入
       View rootView =  inflaterView(inflater, container);
        mUnBinder = ButterKnife.bind(this, rootView);

        init();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        //分离view
        detachView();
    }

    /**
     * 注入view
     *
     * @param inflater  注入器
     * @param container
     */
    private View inflaterView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_base, null);
        //获取存放内容的控件
        FrameLayout flContent = rootView.findViewById(R.id.base_content);
        //获取子类setContentView设置的布局
        View childView = inflater.inflate(getLayoutId(), null);
        //将子类需要展示的内容放到flContent中
        flContent.addView(childView);
        //切换状态
        mBaseSv = rootView.findViewById(R.id.base_sv);
        return rootView;
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
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setStateBar() {

    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }
}
