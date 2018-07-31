package com.xinyang.xycommon.http;

import com.xinyang.xycommon.entity.HttpResult;
import com.xinyang.xycommon.entity.home.HomeArticleList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {


    /**
     * 首页文章列表
     * http://www.wanandroid.com/article/list/0/json
     *
     * @param page 页码 从0开始
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<HttpResult<HomeArticleList>> getHomeArticle(@Path("page") int page);

}
