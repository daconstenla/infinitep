package es.daconstenla.infinip.customcomponents.pieces.data;

import android.graphics.Paint;
import android.graphics.RectF;

/**
 *  Store the state for a data item.
 */
public class ItemHorizontalBar {
		
    public String mLabel;
    public float mWidth;
    
    // computed values
    public RectF mRect = new RectF();
    public Paint mPaint;
    public Paint mPaintB;    
}
