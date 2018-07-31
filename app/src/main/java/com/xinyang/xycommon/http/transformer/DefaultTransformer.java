package com.xinyang.xycommon.http.transformer;


import com.xinyang.xycommon.entity.HttpResult;
import com.xinyang.xycommon.utils.RxSchedulers;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * 默认的转换器
 *
 * @author xinyang
 * @date 2018-7-29 23:45:13
 */
public class DefaultTransformer<T extends HttpResult> implements ObservableTransformer<T, T> {

    @Override
    public Observable<T> apply(Observable<T> observable) {
        return observable.compose(RxSchedulers.<T>applySchedulers())
                .compose(new ErrorCheckTransformer<T>());
        //token过期的重试机制
//                .retryWhen(new TokenFunction());
    }
}