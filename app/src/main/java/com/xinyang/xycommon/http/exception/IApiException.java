package com.xinyang.xycommon.http.exception;

/**
 * Created by blackflagbin on 2017/11/2.
 */

public interface IApiException {
    int getResultCode();

    String getMessage();
}
