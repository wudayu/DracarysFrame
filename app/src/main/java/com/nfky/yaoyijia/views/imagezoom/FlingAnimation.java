package com.nfky.yaoyijia.views.imagezoom;

/**
 * The type Fling animation.
 */
public class FlingAnimation implements Animation {
	
	private float velocityX;
	private float velocityY;
	
	private float factor = 0.95f;
	
	private float threshold = 10;
	
	private FlingAnimationListener listener;
	
	/* (non-Javadoc)
	 * @see com.polites.android.Transformer#update(com.polites.android.GestureImageView, long)
	 */
	@Override
	public boolean update(GestureImageView view, long time) {
		float seconds = (float) time / 1000.0f;
		
		float dx = velocityX * seconds;
		float dy = velocityY * seconds;
		
		velocityX *= factor;
		velocityY *= factor;
		
		boolean active = (Math.abs(velocityX) > threshold && Math.abs(velocityY) > threshold);
		
		if(listener != null) {
			listener.onMove(dx, dy);
			
			if(!active) {
				listener.onComplete();
			}
		}
		
		return active;
	}

	/**
	 * Sets velocity x.
	 *
	 * @param velocityX the velocity x
	 */
	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}

	/**
	 * Sets velocity y.
	 *
	 * @param velocityY the velocity y
	 */
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}

	/**
	 * Sets factor.
	 *
	 * @param factor the factor
	 */
	public void setFactor(float factor) {
		this.factor = factor;
	}

	/**
	 * Sets listener.
	 *
	 * @param listener the listener
	 */
	public void setListener(FlingAnimationListener listener) {
		this.listener = listener;
	}
}
