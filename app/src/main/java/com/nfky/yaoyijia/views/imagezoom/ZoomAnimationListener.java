package com.nfky.yaoyijia.views.imagezoom;

/**
 * The interface Zoom animation listener.
 */
public interface ZoomAnimationListener {
	/**
	 * On zoom.
	 *
	 * @param scale the scale
	 * @param x     the x
	 * @param y     the y
	 */
	public void onZoom(float scale, float x, float y);

	/**
	 * On complete.
	 */
	public void onComplete();
}
