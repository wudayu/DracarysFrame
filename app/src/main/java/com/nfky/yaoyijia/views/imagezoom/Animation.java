package com.nfky.yaoyijia.views.imagezoom;

/**
 * The interface Animation.
 */
public interface Animation {

	/**
	 * Transforms the view.
	 *
	 * @param view the view
	 * @param time the time
	 * @return true if this animation should remain active.  False otherwise.
	 */
	public boolean update(GestureImageView view, long time);
	
}
