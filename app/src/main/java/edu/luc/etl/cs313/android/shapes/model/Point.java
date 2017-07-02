package edu.luc.etl.cs313.android.shapes.model;

/**
 * A point, implemented as a location without a shape.
 */
public class Point extends Location {

	// TODO your job
	// HINT: use a circle with radius 0 as the shape!

	private static final Shape dot = new Circle(0);

	public Point(final int x, final int y) {
		super(x, y, dot);
		assert x >= 0;
		assert y >= 0;
	}
}
