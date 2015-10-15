package com.nfky.yaoyijia.views;

import java.util.List;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.nfky.yaoyijia.R;
import com.nfky.yaoyijia.adapter.ViewPagerAdapter;

/**
 * Created by David on 8/27/15.
 * <p/>
 * BannerView是用来展示广告的Banner控件，通过setRolling函数能够设置它的自动滚动机制
 */
public class BannerView extends RelativeLayout {

	private Context mContext = null;
	private SwitchViewPager mViewPager = null;
	private DotPageIndicator mIndicator = null;
	private ViewPagerAdapter mAdapter = null;

	/**
	 * Instantiates a new Banner view.
	 *
	 * @param context  the context
	 * @param attrs    the attrs
	 * @param defStyle the def style
	 */
	public BannerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		init();
	}

	/**
	 * Instantiates a new Banner view.
	 *
	 * @param context the context
	 * @param attrs   the attrs
	 */
	public BannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	/**
	 * Instantiates a new Banner view.
	 *
	 * @param context the context
	 */
	public BannerView(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	/**
	 * 初始化Banner中承载的控件
	 */
	private void init() {
		this.mAdapter = new ViewPagerAdapter();

		this.mViewPager = new SwitchViewPager(this.mContext);
		this.mIndicator = new DotPageIndicator(this.mContext);
		initLayouts();

		this.mViewPager.setAdapter(this.mAdapter);
		this.mIndicator.setViewPager(this.mViewPager);
	}

	/**
	 * 初始化布局
	 */
	private void initLayouts() {
		RelativeLayout.LayoutParams lpViewPager = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		this.addView(mViewPager, lpViewPager);

		RelativeLayout.LayoutParams lpIndicator = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mContext.getResources().getDimensionPixelSize(R.dimen.dp06));
		lpIndicator.bottomMargin = mContext.getResources().getDimensionPixelSize(R.dimen.dp04);
		lpIndicator.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		this.addView(mIndicator, lpIndicator);
	}

	/**
	 * 获取所有独立的View
	 *
	 * @return views list
	 */
	public List<View> getViews() {
		return this.mAdapter.getViews();
	}

	/**
	 * 添加多个View到BannerView
	 *
	 * @param views 添加的views
	 */
	public void setViews(List<View> views) {
		this.mAdapter.addAll(views);

		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 添加单个View到BannerView
	 *
	 * @param view 单个View
	 */
	public void addView(@NonNull View view) {
		this.mAdapter.addView(view);

		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 设置BannerView滚动效果
	 *
	 * @param timeGap 滚动间隙时间（毫秒）
	 */
	public void setRolling(int timeGap) {
		this.mViewPager.setTimeGap(timeGap);
		this.mViewPager.setRollable(true);
	}

}
