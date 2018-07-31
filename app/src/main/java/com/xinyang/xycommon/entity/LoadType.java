package com.xinyang.xycommon.entity;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 列表刷新类型
 *
 * @author xinyang
 * @date 2018-7-31 12:47:57
 */

public class LoadType {
    public static final int REFRESH_SUCCESS = 1;
    public static final int REFRESH_ERROR = 2;
    public static final int LOAD_MORE_SUCCESS = 3;
    public static final int LOAD_MORE_ERROR = 4;

    @IntDef({REFRESH_SUCCESS, REFRESH_ERROR, LOAD_MORE_SUCCESS, LOAD_MORE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface checker {
    }
}
