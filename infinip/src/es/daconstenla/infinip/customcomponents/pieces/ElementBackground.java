package es.daconstenla.infinip.customcomponents.pieces;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import es.daconstenla.infinip.R;

public class ElementBackground extends View{

	private int mPaddingTop = 15;
	private int mPaddingLeft = 15;
	private int mPaddingRight = 15;
	private int mPaddingBottom = 15;
	
	//private RectF mExternalContainer = new RectF();
	private RectF mInternalContainer = new RectF();
	private RectF mInternalContainerBorder = new RectF();
	
	//private Paint mExternalContainerFill;
	private Paint mInternalContainerFill;
	private Paint mInternalContainerBorderStroke;
	private int mShadowSize = 3;
	
	public ElementBackground(Context context, AttributeSet attrs) { 
		super(context,attrs); 
		__init__();
	}
	public ElementBackground(Context context) { 
		super(context); 
		__init__();
	}
	
	private void __init__() {
		Resources res = getResources();
		
		//mExternalContainerFill = new Paint();
		//mExternalContainerFill.setStyle(Style.FILL);
		//mExternalContainerFill.setColor(res.getColor(R.color.customGreyLight2));
		
		mInternalContainerBorderStroke = new Paint();
		mInternalContainerBorderStroke.setStyle(Style.FILL);
		mInternalContainerBorderStroke.setColor(res.getColor(R.color.backgroundGraphBorder));
		
		mInternalContainerFill = new Paint();
		mInternalContainerFill.setStyle(Style.FILL);
		mInternalContainerFill.setColor(res.getColor(R.color.backgroundGraph)); 
		
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	    super.onSizeChanged(w, h, oldw, oldh);
	    
	    int heh = h-mPaddingBottom;
	    int wiw = w-mPaddingRight;
	    
	    //mExternalContainer = new RectF(0,0,w,h);
	    mInternalContainer = new RectF(mPaddingLeft,mPaddingTop,wiw,heh);
	    mInternalContainerBorder = new RectF(mPaddingLeft,mPaddingTop,wiw+mShadowSize,heh+mShadowSize );
	    
	    this.layout(0, 0, w, h);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//if(mExternalContainer!=null && mInternalContainerBorder!=null && mInternalContainer !=null){
		//canvas.drawRect(mExternalContainer, mExternalContainerFill);
		canvas.drawRect(mInternalContainerBorder, mInternalContainerBorderStroke);
		canvas.drawRect(mInternalContainer, mInternalContainerFill);
		//}
	}
	
	public void setPadding(int left, int top, int right, int bottom){
		mPaddingLeft = left;
		mPaddingBottom = bottom;
		mPaddingRight = right;
		mPaddingTop = top;
	}
	public void setShadowSize(int size){
		mShadowSize = size;
	}
	
	public RectF getRectInternalAvailable(){
		RectF ret = new RectF(
				mInternalContainer.left+mPaddingLeft, 
				mInternalContainer.top+mPaddingTop, 
				mInternalContainer.right-mPaddingRight, 
				mInternalContainer.bottom-mPaddingBottom);
		return ret;
	}
}
