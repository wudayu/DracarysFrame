package com.nfky.yaoyijia.views;

import android.support.v4.view.ViewPager;

/**
 *
 * Created by David on 8/25/15.
 *
 * PageIndicator是DotPageIndicator的接口
 *
 **/

public interface PageIndicator extends SwitchViewPager.OnPageChangeListener {
    /**
     * Bind the indicator to a ViewPager.
     */
    void setViewPager(ViewPager view);

    /**
     * Bind the indicator to a ViewPager.
     */
    void setViewPager(ViewPager view, int initialPosition);

    /**
     * <p>Set the current page of both the ViewPager and indicator.</p>
     *
     * <p>This <strong>must</strong> be used if you need to set the page before
     * the views are drawn on screen (e.g., default start page).</p>
     */
    void setCurrentItem(int item);

    /**
     * Set a page change listener which will receive forwarded events.
     */
    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    /**
     * Notify the indicator that the fragment list has changed.
     */
    void notifyDataSetChanged();
}
