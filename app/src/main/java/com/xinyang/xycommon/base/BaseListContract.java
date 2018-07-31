package com.xinyang.xycommon.base;

import com.xinyang.xycommon.entity.LoadType;

import java.util.List;

/**
 * @author xinyang
 * @date 2018-7-31 00:16:14
 */
public interface BaseListContract {

    interface View extends BaseContract.View {
        void initSrl();

        void initRv();

        void showRefreshLoading();

        void hideRefreshLoading();

        void setData(List list, @LoadType.checker int loadType);
    }

    interface Presenter<V extends View> extends BaseContract.Presenter<V> {

        void getListData(boolean isLoadMore);

        void onRefresh();

        void loadMore();
    }

}
