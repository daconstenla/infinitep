package es.daconstenla.infinip.customcomponents.pieces;

import java.util.ArrayList;
import java.util.List;

import es.daconstenla.infinip.customcomponents.pieces.data.ItemHorizontalBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;

// TODO TODO change to this external class, refactor!

public class HorGraphBar extends View{

	public static float mHourTextSize = 1.0f;
	public static float mTextMargin = 1.2f;
	public static float mGraphPosition = 0.0f;
	public boolean mDisplayHours = true;
	private List<ItemHorizontalBar> mData = new ArrayList<ItemHorizontalBar>();
	
	private RectF tRectB;
	private RectF tRectS;
	boolean shadowRight = false;
	

	public HorGraphBar(Context context) { 
		super(context); 
		tRectB = new RectF();
		tRectS = new RectF();
	}
	
	public void setPosition(float pos){
		mGraphPosition = pos;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		shadowRight = false;
		float shadowSize = 2;
		float textSizeWithMargin = mHourTextSize*mTextMargin; // TODO puede que necesite más
		
		boolean breakable = false;
		/*
		for(ItemHorizontalBar it : mData){
			
			tRectB.set(it.mRect);
			tRectB.left -= mGraphPosition;
			tRectB.right -= mGraphPosition;
			if(mDisplayHours) tRectB.bottom -= textSizeWithMargin;
			
			if(tRectB.right>mGraphContainer.right){
				breakable = true;
				shadowRight = true;
			}
			
			if( ( tRectB.left >= mGraphContainer.left && tRectB.right <= mGraphContainer.right ) 
					|| ( tRectB.left < mGraphContainer.left && tRectB.right>mGraphContainer.left )
					|| ( tRectB.right > mGraphContainer.right && tRectB.left < mGraphContainer.right ) ){
				if( tRectB.left < mGraphContainer.left && tRectB.right>mGraphContainer.left ){
					tRectB.left = mGraphContainer.left;
				}
				if( tRectB.right > mGraphContainer.right && tRectB.left < mGraphContainer.right ){
					tRectB.right = mGraphContainer.right;
				}
				canvas.drawRect(tRectB, it.mPaint);
				tRectS = tRectB;
				tRectS.top = tRectS.bottom-mHeightBorder;
				canvas.drawRect(tRectS, it.mPaintB);
				
				//Log.v("HorGraphBar > onDraw","r : "+tRectB.left+ " -> "+tRectB.right);
			}
		
			if(breakable) break;
		}
		if( shadowRight ){
			tRectS.set(mGraphContainer);
			tRectS.left = tRectS.right-shadowSize;
			canvas.drawRect(tRectS, mShadowMoreGraphAvailable);
		}
		if(getFirstItem().mRect.left < mGraphPosition){
			tRectS.set(mGraphContainer);
			tRectS.right = tRectS.left+shadowSize;
			canvas.drawRect(tRectS, mShadowMoreGraphAvailable);
		}
		
		if(mDisplayHours){

			for(int i = 0 ; i < mHourTextPosition.size() ; i++){
			
				if(mHourTextPosition.get(i)-mGraphPosition <= mGraphContainer.right && mHourTextPosition.get(i)-mGraphPosition >= mGraphContainer.left){
					canvas.drawText(mHourTextString.get(i),mHourTextPosition.get(i)-mGraphPosition,mGraphContainer.bottom,mHourTextPaint);
					//Log.v("HorGraphBar > onDraw","t : "+(mHourTextPosition.get(i)-mGraphPosition)+ "");
				}
				

				//TODO
				//TODO
				//TODO
				//TODO
				//TODO
			}
			
		}*/
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		/*super.onSizeChanged(w, h, oldw, oldh);
		if(mGraphContainer.right != w || mGraphContainer.bottom != h){
			mGraphContainer.right = w;
			mGraphContainer.bottom = h;
			onDataChanged();
		}*/
	}
			
}
