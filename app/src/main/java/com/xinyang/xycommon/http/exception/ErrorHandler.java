package com.xinyang.xycommon.http.exception;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.xinyang.xycommon.base.BaseContract;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;


/**
 * 网络请求异常处理
 *
 * @author xinshiwi
 */
public class ErrorHandler {

    @SuppressLint("CheckResult")
    public static void handleError(final Throwable e, final BaseContract.View view) {
        Activity context = null;
        if (view instanceof Activity) {
            context = (Activity) view;
        }
        if (view instanceof Fragment) {
            context = ((Fragment) view).getActivity();
        }
        if (e instanceof IApiException) {
            final Activity finalContext = context;
            view.showTip(e.getMessage());
            //api异常处理 踢下线等操作

        } else {
            if (e instanceof SocketTimeoutException) {
                Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        view.showTip("网络请求超时，请检查您的网络状态");
                    }
                });
            } else if (e instanceof ConnectException) {
                Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        view.showTip("网络中断，请检查您的网络状态");
                    }
                });
            } else if (e instanceof UnknownHostException) {
                Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        view.showTip("网络异常，请检查您的网络状态");
                    }
                });
            } else {
                Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        view.showTip("出现了未知错误，请尝试从新打开App或者向我们反馈");
                    }
                });
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转到登录页面，同时清空之前的任务栈
     *
     * @param context    context
     * @param loginClazz 登录页面Activity的Class类
     */
    private static void startLoginActivity(Context context, Class loginClazz) {
        context.startActivity(new Intent(context, loginClazz).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }


}
