package com.xinyang.xycommon.ui;

import android.annotation.SuppressLint;

import com.xinyang.xycommon.base.BasePresenter;
import com.xinyang.xycommon.constant.Constant;
import com.xinyang.xycommon.entity.HttpResult;
import com.xinyang.xycommon.entity.LoadType;
import com.xinyang.xycommon.entity.home.HomeArticleList;
import com.xinyang.xycommon.http.exception.ErrorConsumer;

import io.reactivex.functions.Consumer;

public class HomePresenter extends BasePresenter<HomeContract.View> implements
        HomeContract.Presenter {

    private int page = Constant.PAGE_START;
    private HomeModule mModule;

    public HomePresenter() {
        mModule = new HomeModule();
    }


    @SuppressLint("CheckResult")
    public void getListData(final boolean isLoadMore) {
        mModule.getHomeArticle(page)
                .compose(mView.<HttpResult<HomeArticleList>>bindToLife())
                .subscribe(new Consumer<HttpResult<HomeArticleList>>() {
                    @Override
                    public void accept(HttpResult<HomeArticleList> result) {
                        HomeArticleList data = result.getData();
                        if (data == null){
                            mView.setData(null, isLoadMore ? LoadType.LOAD_MORE_ERROR : LoadType.REFRESH_ERROR);
                            return;
                        }
                        page++;
                        mView.setData(data.getDatas(), isLoadMore ? LoadType.LOAD_MORE_SUCCESS : LoadType.REFRESH_SUCCESS);
                    }
                }, new ErrorConsumer(mView) {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        super.accept(throwable);
                        mView.setData(null, isLoadMore ? LoadType.LOAD_MORE_ERROR : LoadType.REFRESH_ERROR);
                    }
                });
    }



    @Override
    public void onRefresh() {
        page = Constant.PAGE_START;
        getListData(false);
    }

    @Override
    public void loadMore() {
        getListData(true);
    }
}
