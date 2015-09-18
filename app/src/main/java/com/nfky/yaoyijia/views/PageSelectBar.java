package com.nfky.yaoyijia.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nfky.yaoyijia.R;

/**
 *
 * Created by David on 8/24/15.
 *
 * PageSelectBar是主页面下方控制不同标签页的按钮，它可以用来帮助ViewPager来选择相应的页面
 *
 **/

public class PageSelectBar extends LinearLayout {

	private Context mContext;
	private List<View> mItems;
	// 图片激活状态资源数组
	private int[] imgActived = new int[]{R.drawable.page_select_01_on, R.drawable.page_select_02_on, R.drawable.page_select_03_on, R.drawable.page_select_04_on};
	// 图片非激活状态资源数组
	private int[] imgInActived = new int[]{R.drawable.page_select_01_off, R.drawable.page_select_02_off, R.drawable.page_select_03_off, R.drawable.page_select_04_off};
	// 文字资源数组
	private int[] texts = new int[]{R.string.page_select_01, R.string.page_select_02, R.string.page_select_03, R.string.page_select_04};
	// 文字激活状态下颜色
	private int textsColorActived = R.color.col_app;
	// 文字非激活状态下文字
	private int textsColorInActived = R.color.letter_grey_deep_6;
	// 背景激活状态资源数组
	private int[] bgActived = null;
	// 背景非激活状态资源数组
	private int[] bgInActived = null;

    // 界面被选中的监听器
	private PageSelectBarOnPageSelectedListener mPageSelectedListener = null;

	public PageSelectBar(Context context) {
		super(context);
		init(context);
	}

	public PageSelectBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

    /**
     * 初始化
     *
     * @param context 上下文
     */
	private void init(Context context) {
		this.mContext = context;
		this.mItems = new ArrayList<>();

		renderPageSelectBar();
		selectItem(0);
		selectItemUI(0);
	}

    /**
     * 绘制整个控件
     */
	private void renderPageSelectBar() {
		for (int i = 0; i < texts.length; ++i) {
			View item = LayoutInflater.from(mContext).inflate(R.layout.item_page_select_bar, this, false);
			this.addView(item);
			mItems.add(item);
			item.setOnClickListener(new PageOnClickListener(i));
		}
	}

    /**
     * 设置每个项目
     *
     * @param position 第position个项目
     */
	public void selectItemUI(int position) {
		for (int i = 0; i < mItems.size(); ++i) {
			View item = mItems.get(i);
			RelativeLayout rl = (RelativeLayout) item.findViewById(R.id.rl_page_select_bar_item);
			ImageView iv = (ImageView) item.findViewById(R.id.iv_page_select_bar_item);
			TextView tv = (TextView) item.findViewById(R.id.tv_page_select_bar_item);

			if (i == position) {
				if (texts != null)
					tv.setText(texts[i]);
				if (textsColorActived > 0)
					tv.setTextColor(mContext.getResources().getColor(textsColorActived));
				if (imgActived != null)
					iv.setImageResource(imgActived[i]);
				if (bgActived != null)
					rl.setBackgroundResource(bgActived[i]);
			} else {
				if (texts != null)
					tv.setText(texts[i]);
				if (textsColorInActived > 0)
					tv.setTextColor(mContext.getResources().getColor(textsColorInActived));
				if (imgInActived != null)
					iv.setImageResource(imgInActived[i]);
				if (bgInActived != null)
					rl.setBackgroundResource(bgInActived[i]);
			}
		}
	}

    /**
     * 回调某个项目
     *
     * @param position 第position个项目
     *
     */
	private void selectItem(int position) {
		// Delay ui transformation
		// selectItemUI(position);

		if (this.mPageSelectedListener != null)
			this.mPageSelectedListener.onPageSelected(position);
	}

	private class PageOnClickListener implements OnClickListener {
		private int position;

		public PageOnClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			selectItem(this.position);
		}
	}

	public interface PageSelectBarOnPageSelectedListener {
		void onPageSelected(int position);
	}

	public void setPageSelectBarOnPageSelectedListener(PageSelectBarOnPageSelectedListener pageSelectedListener) {
		this.mPageSelectedListener = pageSelectedListener;
	}
	
}
