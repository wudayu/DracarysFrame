package com.nfky.yaoyijia.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by David on 5/24/15.
 * <p/>
 * ViewPagerAdapter是PagerAdapter的子类，它是View的PagerAdapter
 */
public class ViewPagerAdapter extends PagerAdapter {

	/**
	 * The Views.
	 */
	protected List<View> views;

	/**
	 * Instantiates a new View pager adapter.
	 */
	public ViewPagerAdapter() {
		this.views = new ArrayList<View>();
	}

	/**
	 * 替换所有当前Adapter中的View
	 *
	 * @param datas 要替换的View所在的List范型
	 */
	public void addAll(List<View> datas) {
		this.views.clear();
		this.views.addAll(datas);

		this.notifyDataSetChanged();
	}

	/**
	 * 添加单个View
	 *
	 * @param view 被添加的View
	 */
	public void addView(View view) {
		this.views.add(view);

		this.notifyDataSetChanged();
	}

	/**
	 * 删除所有View项
	 */
	public void removeAll() {
		this.views.clear();
		this.notifyDataSetChanged();
	}

    /**
     * 获取View总数
     *
     * @return View的总数
     */
	@Override
	public int getCount() {
		return this.views.size();
	}

	/**
	 * 获取指定位置的View
	 *
	 * @param position the position
	 * @return 指定位置的View view
	 */
	public View getView(int position) {
		return this.views.get(position);
	}

	/**
	 * 获取所有的View
	 *
	 * @return 所有的View views
	 */
	public List<View> getViews() {
		return this.views;
    }

    /**
     * 初始化项目，将实例化好的View放入Container中，必须实现
     *
     * @param container 具体承载的项目如Viewpager
     * @param position 实例化好的项目的位置
     * @return 返回添加的实例化好的项目
     */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(views.get(position));

		return this.views.get(position);
	}

    /**
     * 销毁实例化的组件，必须实现
     *
     * @param container 具体承载的项目如Viewpager
     * @param position 实例化好的项目的位置
     * @param object 对应的组件
     */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(this.views.get(position));
	}

    /**
     * 判断pager的一个view是否和instantiateItem方法返回的object有关联
     */
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

}
