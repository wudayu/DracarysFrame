package com.nfky.yaoyijia.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nfky.yaoyijia.R;
import com.nfky.yaoyijia.adapter.MainActivityPageAdapter;
import com.nfky.yaoyijia.constant.Constant;
import com.nfky.yaoyijia.fragment.HomeFragment;
import com.nfky.yaoyijia.fragment.CircleFragment;
import com.nfky.yaoyijia.fragment.MineFragment;
import com.nfky.yaoyijia.fragment.PurchaseFragment;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.handler.interfaces.ILogHandler;
import com.nfky.yaoyijia.handler.LogHandler;
import com.nfky.yaoyijia.service.PushService;
import com.nfky.yaoyijia.views.PageSelectBar;
import com.nfky.yaoyijia.views.SwitchViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by David on 5/24/15.
 * <p/>
 * Description: MainActivity是项目的真正主界面当所有数据加载完成后，用户看到的就是这个页面。他包括了一个ViewPager和一个PageSelectBar
 */
public class MainActivity extends BaseActivity {

	private static final int PAGE_COUNT = 3;

	/**
	 * The Log handler.
	 */
	ILogHandler logHandler = null;

	/**
	 * The Vp main.
	 */
	SwitchViewPager vpMain = null;
	/**
	 * The Psb main.
	 */
	PageSelectBar psbMain = null;
	/**
	 * The Iv back.
	 */
	ImageView ivBack = null;
	/**
	 * The Tv title.
	 */
	TextView tvTitle = null;

	/**
	 * The Home fragment.
	 */
	HomeFragment homeFragment = null;
	/**
	 * The Circle fragment.
	 */
	CircleFragment circleFragment = null;
	/**
	 * The Purchase fragment.
	 */
	PurchaseFragment purchaseFragment = null;
	/**
	 * The Mine fragment.
	 */
	MineFragment mineFragment = null;


	@Override
	protected void initContainer() {
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void initComponents() {
		vpMain = (SwitchViewPager) findViewById(R.id.vp_main);
		psbMain = (PageSelectBar) findViewById(R.id.psb_main);
		ivBack = (ImageView) findViewById(R.id.iv_back);
		tvTitle = (TextView) findViewById(R.id.tv_title);

		MainActivityPageAdapter adapter = new MainActivityPageAdapter(getSupportFragmentManager());

		List<Fragment> fragments = new ArrayList<>();
		homeFragment = new HomeFragment();
		circleFragment = new CircleFragment();
		purchaseFragment = new PurchaseFragment();
		mineFragment = new MineFragment();

		fragments.add(homeFragment);
		fragments.add(circleFragment);
		fragments.add(purchaseFragment);
		fragments.add(mineFragment);

		adapter.addAll(fragments);
		vpMain.setAdapter(adapter);
		vpMain.setOffscreenPageLimit(PAGE_COUNT - 1);

        logHandler = LogHandler.getInstance(this);
	}

	@Override
	protected void initEvents() {
		vpMain.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int pos) {
				psbMain.selectItemUI(pos);
				setTitles(pos);

				if (pos == 3)
					startActivity(new Intent(MainActivity.this, LoginActivity.class));
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			@Override
			public void onPageScrollStateChanged(int arg0) {}
		});
		psbMain.setPageSelectBarOnPageSelectedListener(new PageSelectBar.PageSelectBarOnPageSelectedListener() {
			@Override
			public void onPageSelected(int position) {
				vpMain.setCurrentItem(position);
			}
		});
	}

	@Override
	protected void initData() {
		ivBack.setVisibility(View.INVISIBLE);
		setTitles(0);
	}

	@Override
	protected void afterAllSet() {
		if (Constant.needPush) {
			Intent pushServiceIntent = new Intent(this, PushService.class);
			startService(pushServiceIntent);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		homeFragment.onActivityResult(requestCode, resultCode, data);
		circleFragment.onActivityResult(requestCode, resultCode, data);
		purchaseFragment.onActivityResult(requestCode, resultCode, data);
		mineFragment.onActivityResult(requestCode, resultCode, data);

		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 双击后退键退出
	 */
	private static long back_pressed = 0;
	@Override
	public void onBackPressed() {
		if (back_pressed + 2000 > System.currentTimeMillis()) {
			logHandler.writeLogTag();
			super.closeAllActivity();
			super.onBackPressed();
		} else {
			Utils.toastMessage(this, getString(R.string.str_double_close));
		}

		back_pressed = System.currentTimeMillis();
	}

	private void setTitles(int pos) {
		Utils.debug("pos = " + pos);
		int titleResource = -1;
		switch (pos) {
			case 0: titleResource = R.string.str_title_home;
				break;
			case 1: titleResource = R.string.str_title_circle;
				break;
			case 2: titleResource = R.string.str_title_purchase;
				break;
			case 3: titleResource = R.string.str_title_mine;
				break;
		}

		if (titleResource > 0)
			tvTitle.setText(titleResource);
	}

}
