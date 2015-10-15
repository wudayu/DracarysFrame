package com.nfky.yaoyijia.views.imagezoom;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

/**
 * The type Fling listener.
 */
public class FlingListener extends SimpleOnGestureListener {
	
	private float velocityX;
	private float velocityY;
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		return true;
	}

	/**
	 * Gets velocity x.
	 *
	 * @return the velocity x
	 */
	public float getVelocityX() {
		return velocityX;
	}

	/**
	 * Gets velocity y.
	 *
	 * @return the velocity y
	 */
	public float getVelocityY() {
		return velocityY;
	}
}
