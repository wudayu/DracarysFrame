package com.nfky.yaoyijia.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by David on 5/24/15.
 * <p/>
 * MainActivityPageAdapter是FragmentPagerAdapter的子类，用来管理MainActivity中的几个Fragments
 */
public class MainActivityPageAdapter extends FragmentPagerAdapter {
	/**
	 * The Fragments.
	 */
	List<Fragment> fragments = null;

	/**
	 * Instantiates a new Main activity page adapter.
	 *
	 * @param fm the fm
	 */
	public MainActivityPageAdapter(FragmentManager fm) {
		super(fm);
		fragments = new ArrayList<Fragment>();
	}

	/**
	 * 替换所有当前Adapter中的Fragment
	 *
	 * @param pages 要替换的Fragment所在的List范型
	 */
	public void addAll(List<Fragment> pages) {
		fragments.clear();
		fragments.addAll(pages);
		this.notifyDataSetChanged();
	}

	/**
	 * 获取对应位置的单个Fragment，必须实现
	 *
	 * @param arg0 Fragment的位置
	 * @return 此位置对应的Fragment
	 */
	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	/**
	 * 获取fragment的总数
	 *
	 * @return 总数
	 */
	@Override
	public int getCount() {
		return fragments.size();
	}

}
