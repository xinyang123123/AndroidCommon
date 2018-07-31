package com.xinyang.xycommon.base;

import com.xinyang.xycommon.http.ApiService;
import com.xinyang.xycommon.http.CacheService;
import com.xinyang.xycommon.http.RetrofitManager;

public class BaseModule {

    protected ApiService mApiService = RetrofitManager.create(ApiService.class);
    protected CacheService mCacheService = RetrofitManager.createCache(CacheService.class);

}
