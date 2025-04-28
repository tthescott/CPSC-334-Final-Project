package edu.gonzaga;

import java.awt.Point;

import org.dyn4j.geometry.Vector2;

/**
 * Stores the zoom and panning state of the camera.
 * @author William Bittle
 * @version 4.1.1
 * @since 4.1.1
 */
public class Camera {
	/** The scale (zoom) in pixels per meter */
	public double scale;
	
	/** The pan-x in pixels */
	public double offsetX;
	
	/** The pan-y in pixels */
	public double offsetY;

	/**
	 * Returns World coordinates for the given point given the width/height of the viewport.
	 * @param width the viewport width
	 * @param height the viewport height
	 * @param p the point
	 * @return Vector2
	 */
	public final Vector2 toWorldCoordinates(double width, double height, Point p) {
 		if (p != null) {
 			Vector2 v = new Vector2();
 			// convert the screen space point to world space
 			v.x =  (p.getX() - width * 0.5 - this.offsetX) / this.scale;
 			v.y = -(p.getY() - height * 0.5 + this.offsetY) / this.scale;
 			return v;
 		}
 		
 		return null;
	}
}
