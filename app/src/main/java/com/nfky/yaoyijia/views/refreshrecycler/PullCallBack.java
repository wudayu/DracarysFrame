package com.nfky.yaoyijia.views.refreshrecycler;

/**
 * Created by David on 9/21/15.
 */

public interface PullCallBack {

    void onLoadMore();

    void onRefresh();

    boolean isLoading();

    boolean hasLoadedAllItems();

}
