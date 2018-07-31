package com.xinyang.xycommon.ui;

import com.blankj.utilcode.util.NetworkUtils;
import com.xinyang.xycommon.base.BaseModule;
import com.xinyang.xycommon.entity.HttpResult;
import com.xinyang.xycommon.entity.home.HomeArticleList;
import com.xinyang.xycommon.http.transformer.DefaultTransformer;
import com.xinyang.xycommon.utils.RxSchedulers;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

public class HomeModule extends BaseModule {

    public Observable<HttpResult<HomeArticleList>> getHomeArticle(int page) {
        return mCacheService.getHomeArticleCache(
                mApiService.getHomeArticle(page).compose(new DefaultTransformer<HttpResult<HomeArticleList>>()),
                new DynamicKey(page),
                new EvictDynamicKey(NetworkUtils.isConnected()))
                .compose(RxSchedulers.<HttpResult<HomeArticleList>>applySchedulers());
    }


}
