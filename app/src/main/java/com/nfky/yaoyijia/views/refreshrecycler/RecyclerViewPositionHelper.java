package com.nfky.yaoyijia.views.refreshrecycler;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Created by David on 9/21/15.
 */
public class RecyclerViewPositionHelper {

    /**
     * The Recycler view.
     */
    final RecyclerView recyclerView;
    /**
     * The Layout manager.
     */
    final RecyclerView.LayoutManager layoutManager;

    /**
     * Instantiates a new Recycler view position helper.
     *
     * @param recyclerView the recycler view
     */
    RecyclerViewPositionHelper(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.layoutManager = recyclerView.getLayoutManager();
    }

    /**
     * Create helper recycler view position helper.
     *
     * @param recyclerView the recycler view
     * @return the recycler view position helper
     */
    public static RecyclerViewPositionHelper createHelper(RecyclerView recyclerView) {
        if (recyclerView == null) {
            throw new NullPointerException("Recycler View is null");
        }
        return new RecyclerViewPositionHelper(recyclerView);
    }

    /**
     * Gets item count.
     *
     * @return the item count
     */
    public int getItemCount() {
        return layoutManager == null ? 0 : layoutManager.getItemCount();
    }

    /**
     * Find first visible item position int.
     *
     * @return the int
     */
    public int findFirstVisibleItemPosition() {
        final View child = findOneVisibleChild(0, layoutManager.getChildCount(), false, true);
        return child == null ? NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    /**
     * Find first completely visible item position int.
     *
     * @return the int
     */
    public int findFirstCompletelyVisibleItemPosition() {
        final View child = findOneVisibleChild(0, layoutManager.getChildCount(), true, false);
        return child == null ? NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    /**
     * Find last visible item position int.
     *
     * @return the int
     */
    public int findLastVisibleItemPosition() {
        final View child = findOneVisibleChild(layoutManager.getChildCount() - 1, -1, false, true);
        return child == null ? NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    /**
     * Find last completely visible item position int.
     *
     * @return the int
     */
    public int findLastCompletelyVisibleItemPosition() {
        final View child = findOneVisibleChild(layoutManager.getChildCount() - 1, -1, true, false);
        return child == null ? NO_POSITION : recyclerView.getChildAdapterPosition(child);
    }

    /**
     * Find one visible child view.
     *
     * @param fromIndex              the from index
     * @param toIndex                the to index
     * @param completelyVisible      the completely visible
     * @param acceptPartiallyVisible the accept partially visible
     * @return the view
     */
    public View findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible,
                                    boolean acceptPartiallyVisible) {
        OrientationHelper helper;
        if (layoutManager.canScrollVertically()) {
            helper = OrientationHelper.createVerticalHelper(layoutManager);
        } else {
            helper = OrientationHelper.createHorizontalHelper(layoutManager);
        }

        final int start = helper.getStartAfterPadding();
        final int end = helper.getEndAfterPadding();
        final int next = toIndex > fromIndex ? 1 : -1;
        View partiallyVisible = null;
        for (int i = fromIndex; i != toIndex; i += next) {
            final View child = layoutManager.getChildAt(i);
            final int childStart = helper.getDecoratedStart(child);
            final int childEnd = helper.getDecoratedEnd(child);
            if (childStart < end && childEnd > start) {
                if (completelyVisible) {
                    if (childStart >= start && childEnd <= end) {
                        return child;
                    } else if (acceptPartiallyVisible && partiallyVisible == null) {
                        partiallyVisible = child;
                    }
                } else {
                    return child;
                }
            }
        }
        return partiallyVisible;
    }
}
