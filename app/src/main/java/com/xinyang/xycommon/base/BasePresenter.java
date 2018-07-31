package com.xinyang.xycommon.base;

public class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    protected V mView;


    @Override
    public void attachView(V View) {
        this.mView = View;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}
