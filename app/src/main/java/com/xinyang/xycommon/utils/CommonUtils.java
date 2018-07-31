package com.xinyang.xycommon.utils;

import android.view.View;

import com.xinyang.xycommon.constant.Constant;

import java.util.Calendar;

/**
 * 通用工具类
 *
 * @author xinyang
 * @date 2018-7-31 12:52:22
 */
public class CommonUtils {

    /**
     * 判断与上次点击时间间隔,小于规定时间则属于重复点击 不予响应
     *
     * @param v 被点击的view
     * @return true 重复点击 false 正常点击
     */
    public static boolean isDoubleClick(View v) {
        Object tag = v.getTag(v.getId());
        long beforeTimemiles = tag != null ? (long) tag : 0;
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        v.setTag(v.getId(), timeInMillis);
        return timeInMillis - beforeTimemiles < Constant.NO_DOUBLE_CLICK_TIME;
    }
}
