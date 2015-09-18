package com.nfky.yaoyijia.listener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 *
 * Created by David on 8/25/15.
 *
 * BannerViewOnItemClickListener是显示各种滚动广告的控件的监听器，可以对不同类型的广告进行不同的处理
 *
 **/

public class BannerViewOnItemClickListener implements OnClickListener {

	private Context mContext;
	private int mPageIdentifier;

	// Banner的种类
	public static final int IDENTIFIER_TEST_FIRST = 101;
	public static final int IDENTIFIER_TEST_SECOND = 102;
	public static final int IDENTIFIER_TEST_THIRD = 103;
	public static final int IDENTIFIER_TEST_FOURTH = 104;
	public static final int IDENTIFIER_TEST_FIFTH = 105;
	public static final int IDENTIFIER_TEST_SIXTH = 106;

	public BannerViewOnItemClickListener(Context context, int pageIdentifier) {
		this.mContext = context;
		this.mPageIdentifier = pageIdentifier;
	}

	/**
	 * 更具不同的Banner种类定义不同的操作
	 */
	@Override
	public void onClick(View v) {
		switch (mPageIdentifier) {
		case IDENTIFIER_TEST_FIRST:
			Toast.makeText(mContext, "Banner first", Toast.LENGTH_SHORT).show();
			break;
		case IDENTIFIER_TEST_SECOND:
			Toast.makeText(mContext, "Banner second", Toast.LENGTH_SHORT).show();
			break;
		case IDENTIFIER_TEST_THIRD:
			Toast.makeText(mContext, "Banner third", Toast.LENGTH_SHORT).show();
			break;
		case IDENTIFIER_TEST_FOURTH:
			Toast.makeText(mContext, "Banner fourth", Toast.LENGTH_SHORT).show();
			break;
		case IDENTIFIER_TEST_FIFTH:
			Toast.makeText(mContext, "Banner fifth", Toast.LENGTH_SHORT).show();
			break;
		case IDENTIFIER_TEST_SIXTH:
			Toast.makeText(mContext, "Banner sixth", Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
