package com.nfky.yaoyijia.views.refreshrecycler;

/**
 * Created by David on 9/21/15.
 */
public interface PullCallBack {

    /**
     * On load more.
     */
    void onLoadMore();

    /**
     * On refresh.
     */
    void onRefresh();

    /**
     * Is loading boolean.
     *
     * @return the boolean
     */
    boolean isLoading();

    /**
     * Has loaded all items boolean.
     *
     * @return the boolean
     */
    boolean hasLoadedAllItems();

}
