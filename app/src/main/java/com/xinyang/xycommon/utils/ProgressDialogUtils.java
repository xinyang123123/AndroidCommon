package com.xinyang.xycommon.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.SpannableStringBuilder;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.SpanUtils;

/**
 * 网络请求进度条
 * <p>
 *
 * @author xinyang
 * @date 2017/8/25
 */

public class ProgressDialogUtils {

    public static ProgressDialog mDialog;

    public static void showDialog(final Activity context, String text) {
        if (mDialog == null) {
            mDialog = new ProgressDialog(context);
            mDialog.setMessage(getSpanText(text));
            mDialog.setCancelable(false);

            mDialog.show();
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    mDialog = null;
                }
            });
        } else {
            mDialog.setMessage(getSpanText(text));
        }
    }

    public static void dismissDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog = null;
            }
        } catch (Exception e) {

        }
    }

    /**
     * 获取富文本内容
     */
    private static SpannableStringBuilder getSpanText(String text) {
        return new SpanUtils().append(text)
                .setForegroundColor(Color.parseColor("#323232"))
                .setFontSize(SizeUtils.sp2px(16))
                .create();
    }
}
