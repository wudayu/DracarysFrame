package com.nfky.yaoyijia.views.imagezoom;

import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * The type Vector f.
 */
public class VectorF {

	/**
	 * The Angle.
	 */
	public float angle;
	/**
	 * The Length.
	 */
	public float length;

	/**
	 * The Start.
	 */
	public final PointF start = new PointF();
	/**
	 * The End.
	 */
	public final PointF end = new PointF();

	/**
	 * Calculate end point.
	 */
	public void calculateEndPoint() {
		end.x = (float) Math.cos(angle) * length + start.x;
		end.y = (float) Math.sin(angle) * length + start.y;
	}

	/**
	 * Sets start.
	 *
	 * @param p the p
	 */
	public void setStart(PointF p) {
		this.start.x = p.x;
		this.start.y = p.y;
	}

	/**
	 * Sets end.
	 *
	 * @param p the p
	 */
	public void setEnd(PointF p) {
		this.end.x = p.x;
		this.end.y = p.y;
	}

	/**
	 * Set.
	 *
	 * @param event the event
	 */
	public void set(MotionEvent event) {
		this.start.x = event.getX(0);
		this.start.y = event.getY(0);
		this.end.x = event.getX(1);
		this.end.y = event.getY(1);
	}

	/**
	 * Calculate length float.
	 *
	 * @return the float
	 */
	public float calculateLength() {
		length = MathUtils.distance(start, end);
		return length;
	}

	/**
	 * Calculate angle float.
	 *
	 * @return the float
	 */
	public float calculateAngle() {
		angle = MathUtils.angle(start, end);
		return angle;
	}
	
}
