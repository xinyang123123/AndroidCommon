package com.xinyang.xycommon.http.transformer;


import com.xinyang.xycommon.entity.HttpResult;
import com.xinyang.xycommon.http.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * 业务是否成功的检查转换器
 *
 * @author xinyang
 * @date 2018-7-29 23:45:23
 */
public class ErrorCheckTransformer<T extends HttpResult> implements ObservableTransformer<T, T> {

    @Override
    public Observable<T> apply(Observable<T> httpResultObservable) {
        return httpResultObservable.map(new Function<T, T>() {
            @Override
            public T apply(T httpResult) {
                //判断业务是否成功 不成功则抛出异常
                if (!httpResult.isSecceed()) {
                    throw new ApiException(httpResult.getErrorCode(), httpResult.getErrorMsg());
                }
                return httpResult;
            }
        });
    }
}
