package com.xinyang.xycommon.http;

import com.xinyang.xycommon.entity.HttpResult;
import com.xinyang.xycommon.entity.home.HomeArticleList;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.LifeCache;

public interface CacheService {

    @LifeCache(duration = 3, timeUnit = TimeUnit.MINUTES)
    Observable<HttpResult<HomeArticleList>> getHomeArticleCache(Observable<HttpResult<HomeArticleList>> observable
            , DynamicKey dynamicKey, EvictDynamicKey evictDynamicKey);
}
