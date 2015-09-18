package com.nfky.yaoyijia.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.nfky.yaoyijia.R;

/**
 *
 * Created by David on 8/27/15.
 *
 * SelectPicPopupWindow是一个提示板，用来给用户选择以一种获取图片的方式，由于黑幕的显示不够流畅，需要改进
 * 请不要修改此文件
 *
 **/

public class SelectPicPopupWindow extends PopupWindow {

	private Button btnTakePhoto, btnPickPhoto, btnCancel;
	private View mMenuView;

	/**
	 * 绘制提示板
	 *
	 * @param context 上下文
	 */
	public SelectPicPopupWindow(Context context) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.layout_select_pic_pop, null);
		btnTakePhoto = (Button) mMenuView.findViewById(R.id.btn_take_photo);
		btnPickPhoto = (Button) mMenuView.findViewById(R.id.btn_pick_photo);
		btnCancel = (Button) mMenuView.findViewById(R.id.btn_cancel);
		// cancel button
		btnCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
		this.setContentView(mMenuView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setAnimationStyle(R.style.PopupAnimation);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);
		// add OnTouchListener to mMenuView
		mMenuView.setOnTouchListener(new OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			public boolean onTouch(View v, MotionEvent event) {
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});
	}

	public void setOnClickListener(OnClickListener itemsOnClick) {
		btnPickPhoto.setOnClickListener(itemsOnClick);
		btnTakePhoto.setOnClickListener(itemsOnClick);
	}

}
