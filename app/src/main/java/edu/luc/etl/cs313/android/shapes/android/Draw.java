package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

	// TODO entirely your job (except onCircle)

	private final Canvas canvas;

	private final Paint paint;

	public Draw(final Canvas canvas, final Paint paint) {
		this.canvas = canvas;
		this.paint = paint;
		paint.setStyle(Style.STROKE);
	}

	@Override
	public Void onCircle(final Circle c) {
		canvas.drawCircle(0, 0, c.getRadius(), paint);
		return null;
	}

	@Override
	public Void onStroke(final Stroke c) {
	c.getShape().accept(this);
        paint.setColor(c.getColor());

        if (paint.getStyle()== Style.FILL) {
            paint.setStyle(Style.FILL_AND_STROKE);
        }
        else {paint.setStyle(Style.STROKE);}
		return null;
	}

	@Override
	public Void onFill(final Fill f) {
        f.getShape().accept(this);
        if (paint.getStyle()== Style.STROKE) {
            paint.setStyle(Style.FILL_AND_STROKE);
        }
        else {paint.setStyle(Style.FILL);}

		return null;
	}

	@Override
	public Void onGroup(final Group g) {
		for(Shape s: g.getShapes()){
			s.accept(this);
		}
		return null;
	}

	@Override
	public Void onLocation(final Location l) {
		canvas.translate(l.getX(),l.getY());
		l.getShape().accept(this);
		canvas.translate(-l.getX(),-l.getY());
		return null;
	}

	@Override
	public Void onRectangle(final Rectangle r) {
		canvas.drawRect(0,0, r.getWidth(), r.getHeight(), paint);
		return null;
	}

	@Override
	public Void onOutline(Outline o) {
		o.getShape().accept(this);
		return null;
	}

	@Override
	public Void onPolygon(final Polygon s) {
		final float[] pts = new float[(4*s.getPoints().size())+2];
		int i=0;

		for (Point p: s.getPoints()){
			pts[i]= p.getX();
			pts[i+1]=p.getY();
			pts[i+2]=p.getX();
			pts[i+3]=p.getY();
			i=i+4;
		}

		pts[i]=pts[0];
		pts[i+1]=pts[1];


		canvas.drawLines(pts, paint);
		return null;
	}
}
