package com.xinyang.xycommon.base;

import com.kennyc.view.MultiStateView;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @author xinyang
 * @date 2018-7-29 13:13:02
 */

public interface BaseContract {

    interface Presenter<V extends View> {
        /**
         * 绑定view
         *
         * @param View view层实现 不是android的view类
         */
        void attachView(V View);

        /**
         * 解绑view
         */
        void detachView();
    }

    interface View {
        /**
         * 获取状态控制view
         *
         * @return 状态控制view
         */
        MultiStateView getStateView();

        /**
         * 设置布局状态
         *
         * @param state public static final int VIEW_STATE_UNKNOWN = -1;
         *              public static final int VIEW_STATE_CONTENT = 0;
         *              public static final int VIEW_STATE_ERROR = 1;
         *              public static final int VIEW_STATE_EMPTY = 2;
         *              public static final int VIEW_STATE_LOADING = 3;
         */
        void setViewState(int state);

        /**
         * 显示加载中进度条
         */
        void showLoading();

        /**
         * 隐藏加载中进度条
         */
        void hideLoading();

        /**
         * 获取数据错误时重试点击事件
         */
        void onRetry();

        /**
         * 显示提示信息
         * @param msg 提示信息
         */
        void showTip(String msg);


        /**
         * 设置状态栏
         */
        void setStateBar();


        /**
         * 绑定activity/fragment生命周期 防止rxJava内存泄漏
         * @param <T>
         * @return
         */
        <T> LifecycleTransformer<T> bindToLife();

    }
}
