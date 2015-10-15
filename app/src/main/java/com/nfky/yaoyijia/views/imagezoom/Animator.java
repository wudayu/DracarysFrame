package com.nfky.yaoyijia.views.imagezoom;

/**
 * The type Animator.
 */
public class Animator extends Thread {
	
	private GestureImageView view;
	private Animation animation;
	private boolean running = false;
	private boolean active = false;
	private long lastTime = -1L;

	/**
	 * Instantiates a new Animator.
	 *
	 * @param view       the view
	 * @param threadName the thread name
	 */
	public Animator(GestureImageView view, String threadName) {
		super(threadName);
		this.view = view;
	}
	
	@Override
	public void run() {
		
		running = true;
		
		while(running) {
				
			while(active && animation != null) {
				long time = System.currentTimeMillis();
				active = animation.update(view, time - lastTime);
				view.redraw();
				lastTime = time;					
				
				while(active) {
					try {
						if(view.waitForDraw(32)) { // 30Htz
							break;
						}
					}
					catch (InterruptedException ignore) {
						active = false;
					}
				}
			}
			
			synchronized(this) {
				if(running) {
					try {
						wait();
					}
					catch (InterruptedException ignore) {}
				}
			}
		}
	}

	/**
	 * Finish.
	 */
	public synchronized void finish() {
		running = false;
		active = false;
		notifyAll();
	}

	/**
	 * Play.
	 *
	 * @param transformer the transformer
	 */
	public void play(Animation transformer) {
		if(active) {
			cancel();
		}
 		this.animation = transformer;
 		
 		activate();
	}

	/**
	 * Activate.
	 */
	public synchronized void activate() {
		lastTime = System.currentTimeMillis();
		active = true;
		notifyAll();
	}

	/**
	 * Cancel.
	 */
	public void cancel() {
		active = false;
	}
}
