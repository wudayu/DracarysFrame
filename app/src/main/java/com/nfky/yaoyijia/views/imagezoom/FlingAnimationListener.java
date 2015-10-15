package com.nfky.yaoyijia.views.imagezoom;

/**
 * The interface Fling animation listener.
 */
public interface FlingAnimationListener {

	/**
	 * On move.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void onMove(float x, float y);

	/**
	 * On complete.
	 */
	public void onComplete();
	
}
