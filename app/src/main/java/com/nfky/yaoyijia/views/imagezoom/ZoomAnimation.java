package com.nfky.yaoyijia.views.imagezoom;

import android.graphics.PointF;

/**
 * The type Zoom animation.
 */
public class ZoomAnimation implements Animation {

	private boolean firstFrame = true;
	
	private float touchX;
	private float touchY;
	
	private float zoom;
	
	private float startX;
	private float startY;
	private float startScale;
	
	private float xDiff;
	private float yDiff;
	private float scaleDiff;
	
	private long animationLengthMS = 200;
	private long totalTime = 0;
	
	private ZoomAnimationListener zoomAnimationListener;

	/* (non-Javadoc)
	 * @see com.polites.android.Animation#update(com.polites.android.GestureImageView, long)
	 */
	@Override
	public boolean update(GestureImageView view, long time) {
		if(firstFrame) {
			firstFrame = false;
			
			startX = view.getImageX();
			startY = view.getImageY();
			startScale = view.getScale();
			scaleDiff = (zoom * startScale) - startScale;
			
			if(scaleDiff > 0) {
				// Calculate destination for midpoint
				VectorF vector = new VectorF();
				
				// Set the touch point as start because we want to move the end				
				vector.setStart(new PointF(touchX, touchY));
				vector.setEnd(new PointF(startX, startY));				
			
				vector.calculateAngle();
				
				// Get the current length
				float length = vector.calculateLength();
				
				// Multiply length by zoom to get the new length
				vector.length = length*zoom;
				
				// Now deduce the new endpoint
				vector.calculateEndPoint();
				
				xDiff = vector.end.x - startX;
				yDiff = vector.end.y - startY;
			}
			else {
				// Zoom out to center
				xDiff = view.getCenterX() - startX;
				yDiff = view.getCenterY() - startY;
			}
		}
		
		totalTime += time;
		
		float ratio = (float) totalTime / (float) animationLengthMS;
		
		if(ratio < 1) {
			
			if(ratio > 0) {
				// we still have time left
				float newScale = (ratio * scaleDiff) + startScale;
				float newX = (ratio * xDiff) + startX;
				float newY = (ratio * yDiff) + startY;
				
				if(zoomAnimationListener != null) {
					zoomAnimationListener.onZoom(newScale, newX, newY);
				}
			}

			return true;
		}
		else {
			
			float newScale = scaleDiff + startScale;
			float newX = xDiff + startX;
			float newY = yDiff + startY;

			if(zoomAnimationListener != null) {
				zoomAnimationListener.onZoom(newScale, newX, newY);
				zoomAnimationListener.onComplete();
			}
			
			return false;
		}
	}

	/**
	 * Reset.
	 */
	public void reset() {
		firstFrame = true;
		totalTime = 0;
	}

	/**
	 * Gets zoom.
	 *
	 * @return the zoom
	 */
	public float getZoom() {
		return zoom;
	}

	/**
	 * Sets zoom.
	 *
	 * @param zoom the zoom
	 */
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}

	/**
	 * Gets touch x.
	 *
	 * @return the touch x
	 */
	public float getTouchX() {
		return touchX;
	}

	/**
	 * Sets touch x.
	 *
	 * @param touchX the touch x
	 */
	public void setTouchX(float touchX) {
		this.touchX = touchX;
	}

	/**
	 * Gets touch y.
	 *
	 * @return the touch y
	 */
	public float getTouchY() {
		return touchY;
	}

	/**
	 * Sets touch y.
	 *
	 * @param touchY the touch y
	 */
	public void setTouchY(float touchY) {
		this.touchY = touchY;
	}

	/**
	 * Gets animation length ms.
	 *
	 * @return the animation length ms
	 */
	public long getAnimationLengthMS() {
		return animationLengthMS;
	}

	/**
	 * Sets animation length ms.
	 *
	 * @param animationLengthMS the animation length ms
	 */
	public void setAnimationLengthMS(long animationLengthMS) {
		this.animationLengthMS = animationLengthMS;
	}

	/**
	 * Gets zoom animation listener.
	 *
	 * @return the zoom animation listener
	 */
	public ZoomAnimationListener getZoomAnimationListener() {
		return zoomAnimationListener;
	}

	/**
	 * Sets zoom animation listener.
	 *
	 * @param zoomAnimationListener the zoom animation listener
	 */
	public void setZoomAnimationListener(ZoomAnimationListener zoomAnimationListener) {
		this.zoomAnimationListener = zoomAnimationListener;
	}
}
