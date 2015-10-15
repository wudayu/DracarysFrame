package com.nfky.yaoyijia.views.imagezoom;

/**
 * The type Move animation.
 */
public class MoveAnimation implements Animation {

	private boolean firstFrame = true;
	
	private float startX;
	private float startY;
	
	private float targetX;
	private float targetY;
	private long animationTimeMS = 100;
	private long totalTime = 0;
	
	private MoveAnimationListener moveAnimationListener;
	
	/* (non-Javadoc)
	 * @see com.polites.android.Animation#update(com.polites.android.GestureImageView, long)
	 */
	@Override
	public boolean update(GestureImageView view, long time) {
		totalTime += time;
		
		if(firstFrame) {
			firstFrame = false;
			startX = view.getImageX();
			startY = view.getImageY();
		}
		
		if(totalTime < animationTimeMS) {
			
			float ratio = (float) totalTime / animationTimeMS;
			
			float newX = ((targetX - startX) * ratio) + startX;
			float newY = ((targetY - startY) * ratio) + startY;
			
			if(moveAnimationListener != null) {
				moveAnimationListener.onMove(newX, newY);
			}
			
			return true;
		}
		else {
			if(moveAnimationListener != null) {
				moveAnimationListener.onMove(targetX, targetY);
			}
		}
		
		return false;
	}

	/**
	 * Reset.
	 */
	public void reset() {
		firstFrame = true;
		totalTime = 0;
	}


	/**
	 * Gets target x.
	 *
	 * @return the target x
	 */
	public float getTargetX() {
		return targetX;
	}


	/**
	 * Sets target x.
	 *
	 * @param targetX the target x
	 */
	public void setTargetX(float targetX) {
		this.targetX = targetX;
	}


	/**
	 * Gets target y.
	 *
	 * @return the target y
	 */
	public float getTargetY() {
		return targetY;
	}

	/**
	 * Sets target y.
	 *
	 * @param targetY the target y
	 */
	public void setTargetY(float targetY) {
		this.targetY = targetY;
	}

	/**
	 * Gets animation time ms.
	 *
	 * @return the animation time ms
	 */
	public long getAnimationTimeMS() {
		return animationTimeMS;
	}

	/**
	 * Sets animation time ms.
	 *
	 * @param animationTimeMS the animation time ms
	 */
	public void setAnimationTimeMS(long animationTimeMS) {
		this.animationTimeMS = animationTimeMS;
	}

	/**
	 * Sets move animation listener.
	 *
	 * @param moveAnimationListener the move animation listener
	 */
	public void setMoveAnimationListener(MoveAnimationListener moveAnimationListener) {
		this.moveAnimationListener = moveAnimationListener;
	}
}
