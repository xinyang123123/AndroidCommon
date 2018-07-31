package com.xinyang.xycommon.http.exception;


import com.xinyang.xycommon.base.BaseContract;
import com.xinyang.xycommon.utils.ProgressDialogUtils;

import io.reactivex.functions.Consumer;

/**
 * 请求异常的
 *
 * @author xinshiwi
 * @date 2018/3/29
 */

public abstract class ErrorConsumer implements Consumer<Throwable> {

    private BaseContract.View mView;

    public ErrorConsumer(BaseContract.View view) {
        mView = view;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        mView.hideLoading();
        ProgressDialogUtils.dismissDialog();
        ErrorHandler.handleError(throwable, mView);
    }
}
