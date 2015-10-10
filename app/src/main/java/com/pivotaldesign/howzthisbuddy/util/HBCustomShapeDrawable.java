/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;

/**
 * @author Satish Kolawale
 *
 */
public class HBCustomShapeDrawable extends ShapeDrawable {
	private final Paint fillpaint, strokepaint;
	 
    public HBCustomShapeDrawable(Shape s, int fill, int stroke, int strokeWidth) {
        super(s);
        fillpaint = new Paint(this.getPaint());
        fillpaint.setColor(fill);
        strokepaint = new Paint(fillpaint);
        strokepaint.setStyle(Paint.Style.STROKE);
        strokepaint.setStrokeWidth(strokeWidth);
        strokepaint.setColor(stroke);
    }
 
    @Override
    protected void onDraw(Shape shape, Canvas canvas, Paint paint) {
        shape.draw(canvas, fillpaint);
        shape.draw(canvas, strokepaint);
    }
}
