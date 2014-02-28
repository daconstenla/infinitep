package es.daconstenla.infinip.customcomponents.pieces.data;

import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class BarElementData {
	private int mWidth;
	private RectF mRect = null;
	private Paint mPaint = null;
	private Paint mPaintAlt = null;
	
	private boolean complete = false;
	private boolean haveAlt = false;
	
	/*public BarElementData(int width, int color){
		mWidth = width; 
		setColor(color);
	}*/
	public BarElementData(int width, int color,int colorAlt){
		mWidth = width;
		setColor(color);
		setAltColor(colorAlt);
	}
	public BarElementData(BarElementData b){
		mWidth = b.mWidth;
		mRect = new RectF(b.mRect);
		mPaint = new Paint(b.mPaint);
		mPaintAlt = new Paint(b.mPaintAlt);
		complete = b.complete;
		haveAlt = true;
	}
	private void setColor(int col){
		mPaint = new Paint();
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(col);
		
	}
	private void setAltColor(int col){
		mPaintAlt = new Paint();
		mPaintAlt.setStyle(Style.FILL);
		mPaintAlt.setColor(col);
		haveAlt = true;
	}
	public void setRect(float top,float left, float right,float bottom){mRect = new RectF(left,top,right,bottom);complete = true;}
	public void setRect(RectF rec){mRect = new RectF(rec);}

	public void setPaint(int color){setColor(color); }
	public void setPaint(int color,int altColor){ 
		setColor(color);
		setAltColor(altColor);
	}
	public void setPaint(Paint p){mPaint = new Paint(p); }
	public void setPaint(Paint p,Paint altP){
		mPaint = new Paint(p); 
		mPaintAlt = new Paint(altP);
		haveAlt = true;
	}
	
	public boolean haveAltColor(){return haveAlt;}
	public RectF getRect()throws NullPointerException
	{ if(mRect != null)return mRect; else throw new NullPointerException(); }
	public Paint getPaint()throws NullPointerException
	{ if(mPaint != null) return mPaint;else throw new NullPointerException();}
	public Paint getPaintAlt()throws NullPointerException
	{ if(mPaintAlt != null) return mPaintAlt;else throw new NullPointerException();}
	public boolean isComplete(){return complete;}
	public int getWidth(){return mWidth;}
}
