package com.nfky.yaoyijia.views.refreshrecycler;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.nfky.yaoyijia.R;

/**
 * Created by David on 9/21/15.
 * <p/>
 * 带有下拉刷新以及上划加载功能的RecyclerView集合
 */
public class PullToLoadView  extends FrameLayout {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private PullCallBack mPullCallBack;
    private RecyclerViewPositionHelper mRecyclerViewHelper;
    /**
     * The M cur scrolling direction.
     */
    protected ScrollDirection mCurScrollingDirection;
    /**
     * The M prev first visible item.
     */
    protected int mPrevFirstVisibleItem = 0;
    private int mLoadMoreOffset = 5;
    private boolean mIsLoadMoreEnabled = false;

    /**
     * Instantiates a new Pull to load view.
     *
     * @param context the context
     */
    public PullToLoadView(Context context) {
        this(context, null);
    }

    /**
     * Instantiates a new Pull to load view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public PullToLoadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Instantiates a new Pull to load view.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public PullToLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.layout_pull_to_load_view, this, true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        init();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(mRecyclerView);
    }

    private void init() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (null != mPullCallBack) {
                    mPullCallBack.onRefresh();
                }
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mCurScrollingDirection = null;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mCurScrollingDirection == null) { //User has just started a scrolling motion
                    mCurScrollingDirection = ScrollDirection.SAME;
                    mPrevFirstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                } else {
                    final int firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                    if (firstVisibleItem > mPrevFirstVisibleItem) {
                        //User is scrolling up
                        mCurScrollingDirection = ScrollDirection.UP;
                    } else if (firstVisibleItem < mPrevFirstVisibleItem) {
                        //User is scrolling down
                        mCurScrollingDirection = ScrollDirection.DOWN;
                    } else {
                        mCurScrollingDirection = ScrollDirection.SAME;
                    }
                    mPrevFirstVisibleItem = firstVisibleItem;
                }


                if (mIsLoadMoreEnabled && (mCurScrollingDirection == ScrollDirection.UP)) {
                    // We only need to paginate if user scrolling near the end of the list
                    if (!mPullCallBack.isLoading() && !mPullCallBack.hasLoadedAllItems()) {
                        //Only trigger a load more if a load operation is NOT happening AND all the items have not been loaded
                        final int totalItemCount = mRecyclerViewHelper.getItemCount();
                        final int firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
                        final int visibleItemCount = Math.abs(mRecyclerViewHelper.findLastVisibleItemPosition() - firstVisibleItem);
                        final int lastAdapterPosition = totalItemCount - 1;
                        final int lastVisiblePosition = (firstVisibleItem + visibleItemCount) - 1;
                        if (lastVisiblePosition >= (lastAdapterPosition - mLoadMoreOffset)) {
                            if (null != mPullCallBack) {
                                mProgressBar.setVisibility(VISIBLE);
                                mPullCallBack.onLoadMore();
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * Sets complete.
     */
    public void setComplete() {
        mProgressBar.setVisibility(GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * Init load.
     */
    public void initLoad() {
        if (null != mPullCallBack) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
            mPullCallBack.onRefresh();
        }
    }

    /**
     * Sets color scheme resources.
     *
     * @param colorResIds the color res ids
     */
    public void setColorSchemeResources(int... colorResIds) {
        mSwipeRefreshLayout.setColorSchemeResources(colorResIds);
    }

    /**
     * Gets recycler view.
     *
     * @return the recycler view
     */
    public RecyclerView getRecyclerView() {
        return this.mRecyclerView;
    }

    /**
     * Sets pull call back.
     *
     * @param mPullCallBack the m pull call back
     */
    public void setPullCallBack(PullCallBack mPullCallBack) {
        this.mPullCallBack = mPullCallBack;
    }

    /**
     * Sets load more offset.
     *
     * @param mLoadMoreOffset the m load more offset
     */
    public void setLoadMoreOffset(int mLoadMoreOffset) {
        this.mLoadMoreOffset = mLoadMoreOffset;
    }

    /**
     * Is load more enabled.
     *
     * @param mIsLoadMoreEnabled the m is load more enabled
     */
    public void isLoadMoreEnabled(boolean mIsLoadMoreEnabled) {
        this.mIsLoadMoreEnabled = mIsLoadMoreEnabled;
    }
}

