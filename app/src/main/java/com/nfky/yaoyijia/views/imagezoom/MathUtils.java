package com.nfky.yaoyijia.views.imagezoom;

import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * The type Math utils.
 */
public class MathUtils {

	/**
	 * Distance float.
	 *
	 * @param event the event
	 * @return the float
	 */
	public static float distance(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Distance float.
	 *
	 * @param p1 the p 1
	 * @param p2 the p 2
	 * @return the float
	 */
	public static float distance(PointF p1, PointF p2) {
		float x = p1.x - p2.x;
		float y = p1.y - p2.y;
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Distance float.
	 *
	 * @param x1 the x 1
	 * @param y1 the y 1
	 * @param x2 the x 2
	 * @param y2 the y 2
	 * @return the float
	 */
	public static float distance(float x1, float y1, float x2, float y2) {
		float x = x1 - x2;
		float y = y1 - y2;
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Midpoint.
	 *
	 * @param event the event
	 * @param point the point
	 */
	public static void midpoint(MotionEvent event, PointF point) {
		float x1 = event.getX(0);
		float y1 = event.getY(0);
		float x2 = event.getX(1);
		float y2 = event.getY(1);
		midpoint(x1, y1, x2, y2, point);
	}

	/**
	 * Midpoint.
	 *
	 * @param x1    the x 1
	 * @param y1    the y 1
	 * @param x2    the x 2
	 * @param y2    the y 2
	 * @param point the point
	 */
	public static void midpoint(float x1, float y1, float x2, float y2, PointF point) {
		point.x = (x1 + x2) / 2.0f;
		point.y = (y1 + y2) / 2.0f;
	}

	/**
	 * Rotates p1 around p2 by angle degrees.
	 *
	 * @param p1    the p 1
	 * @param p2    the p 2
	 * @param angle the angle
	 */
	public void rotate(PointF p1, PointF p2, float angle) {
		float px = p1.x;
		float py = p1.y;
		float ox = p2.x;
		float oy = p2.y;
		p1.x = ((float) Math.cos(angle) * (px-ox) - (float) Math.sin(angle) * (py-oy) + ox);
		p1.y = ((float) Math.sin(angle) * (px-ox) + (float) Math.cos(angle) * (py-oy) + oy);
	}

	/**
	 * Angle float.
	 *
	 * @param p1 the p 1
	 * @param p2 the p 2
	 * @return the float
	 */
	public static float angle(PointF p1, PointF p2) {
		return angle(p1.x, p1.y, p2.x, p2.y);
	}

	/**
	 * Angle float.
	 *
	 * @param x1 the x 1
	 * @param y1 the y 1
	 * @param x2 the x 2
	 * @param y2 the y 2
	 * @return the float
	 */
	public static float angle(float x1, float y1, float x2, float y2) {
		return (float) Math.atan2(y2 - y1, x2 - x1);
	}
}
