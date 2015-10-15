package com.nfky.yaoyijia.views.imagezoom;

/**
 * The interface Gesture image view listener.
 */
public interface GestureImageViewListener {

	/**
	 * On touch.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void onTouch(float x, float y);

	/**
	 * On scale.
	 *
	 * @param scale the scale
	 */
	public void onScale(float scale);

	/**
	 * On position.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void onPosition(float x, float y);
	
}
