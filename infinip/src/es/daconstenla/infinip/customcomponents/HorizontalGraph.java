package es.daconstenla.infinip.customcomponents;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import es.daconstenla.infinip.R;
import es.daconstenla.infinip.customcomponents.pieces.ElementBackground;
import es.daconstenla.infinip.customcomponents.pieces.data.ColorMyCustom;
import es.daconstenla.infinip.customcomponents.pieces.data.ItemHorizontalBar;

public class HorizontalGraph extends ViewGroup {

	private float mMinWidthVisible;
	private int mNumberOfVisibleHours;
	private float mSpaceBetweenBars;
	private boolean mSingleViewMode;
	private boolean mDisplayHours;
	private float mHighlightBorderStrength;
	private float mHeightBorder;
	
	private RectF mGraphContainer = new RectF();
	private Paint mShadowMoreGraphAvailable;
	
	private int mInternalPadding = 0;
	private int mGraphContainerPadding = 15;
	
	private HorGraphBar mGraph;
	private ElementBackground mBackground;
	private float mTotalWidthGraphBar;
	
	private List<ItemHorizontalBar> mData = new ArrayList<ItemHorizontalBar>();
	private float mGraphPosition;
	
	private Paint mHourTextPaint;
	private List<Integer> mHourTextPosition = new ArrayList<Integer>();
	private List<String> mHourTextString = new ArrayList<String>();
	private float mHoursSpaceBetween;
	//private float mHoursPadding = 40;
		
	// TODO private GestureDetector mDetector;
	private float mHourTextSize = 20;

	public HorizontalGraph(Context context) {
		super(context);
		init();
	}
	public HorizontalGraph(Context context, AttributeSet attrs) {
		super(context,attrs);
        
		TypedArray a = context.getTheme().obtainStyledAttributes(
				attrs,
				R.styleable.HorizontalBarActivityGraph,
				0, 0
		);
		
		try {
			mMinWidthVisible = a.getFloat(R.styleable.HorizontalBarActivityGraph_minWithVisible, 0.0f);
			mNumberOfVisibleHours = a.getInt(R.styleable.HorizontalBarActivityGraph_numberOfVisibleHours, 10);
			mSpaceBetweenBars = a.getFloat(R.styleable.HorizontalBarActivityGraph_spaceBetweenBars, 2.0f);
			mSingleViewMode = a.getBoolean(R.styleable.HorizontalBarActivityGraph_singleViewMode, false);
			mHighlightBorderStrength = a.getFloat(R.styleable.HorizontalBarActivityGraph_highlightStrength, 0.9f);
			mHeightBorder = a.getFloat(R.styleable.HorizontalBarActivityGraph_HeightBarBorder, 20.0f);
			mDisplayHours = a.getBoolean(R.styleable.HorizontalBarActivityGraph_displayHours,false);
		} finally {
			// release the TypedArray so that it can be reused.
			a.recycle();
		}
		
		init();
	}
		
	public float getGraphPosition(){
		return mGraphPosition;
	}
	public void setGraphPosition(float position){
		mGraphPosition = position;
		mGraph.invalidate();
	}
	
	public boolean onEvent(MotionEvent event) {
		//TODO boolean result = mDetector.onTouchEvent(event);
		
        //TODO return result;
		return true;
	}
	
	public void addItem(String name,float value,int color){
		ItemHorizontalBar tItem = new ItemHorizontalBar();
		
		tItem.mWidth = value;
		tItem.mLabel = name;
		tItem.mPaint = new Paint();
		tItem.mPaint.setStyle(Style.FILL);
		tItem.mPaint.setColor(getResources().getColor(color));
		
		tItem.mPaintB = new Paint();
		tItem.mPaintB.setStyle(Style.FILL);
		//tItem.mPaint.setMaskFilter(new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.5f, 4.0f, 10.0f));
		tItem.mPaintB.setColor(
		Color.argb(
				0xff,
	            Math.min((int) (mHighlightBorderStrength  * (float) Color.red(getResources().getColor(color))), 0xff),
	            Math.min((int) (mHighlightBorderStrength * (float) Color.green(getResources().getColor(color))), 0xff),
	            Math.min((int) (mHighlightBorderStrength * (float) Color.blue(getResources().getColor(color))), 0xff)
	    ));
		
		mData.add(tItem);
		
		onDataChanged();
	}
	
	public void addHour(String hour){
	    mHourTextString.add(hour);
	    onHoursChanged();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	    super.onSizeChanged(w, h, oldw, oldh);
	    //Log.v("HorizontalGraph > onSizeChanged","w : "+w+" h : "+h+"");

	    // Get the display size
	    Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
	    
	    //int MeasuredHeight = 900;
	    Point size = new Point();
	    
	    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
          display.getSize(size); 
        }
	    
	    int newH = h;
	    
	    // TODO Revisar measured, es demasiado
	    //Log.v("HorizontalGraph > onSizeChanged", "h : "+h+" measured H : "+MeasuredHeight);
	    
	    int smallDim = Math.min(w, newH);
	    int tempPadding = (int) (smallDim*0.04f);
	    mGraphContainerPadding = mInternalPadding = tempPadding;
	    if(mInternalPadding == 0) mGraphContainerPadding = mInternalPadding = 10;
	    
	    mHourTextSize = mGraphContainerPadding*1.5f;
	    mHourTextPaint.setTextSize(mHourTextSize);
	    
	    float intPadd = mInternalPadding;
	    float intContW = w - intPadd;
	    float intContH = newH - intPadd;
	    
	    float graphPadd = mGraphContainerPadding ;
	    float graphContW = intContW - graphPadd;
	    float graphContH = intContH - graphPadd;
	    
	    
	    mGraphContainer = new RectF(graphPadd,graphPadd,graphContW,graphContH);
	    
	    mGraph.layout((int)mGraphContainer.top, (int)mGraphContainer.left, (int)mGraphContainer.right, (int)mGraphContainer.bottom);
	    mBackground.layout(0, 0, w, h);
	    
	    //Log.v("HorizontalGraph > onMeasure","w : "+w+" h : "+h);
	    
	    onDataChanged();
	}
	
	private void onHoursChanged(){
		
		if(mGraphContainer.right>0){
			mHoursSpaceBetween = 
					mTotalWidthGraphBar / (mHourTextString.size()-1);
			
			int tInt;
			mHourTextPosition.clear();
			mHourTextPosition.add(-100);
			for(int i = 1 ; i < mHourTextString.size() ; i++){
				tInt = (int)((i*mHoursSpaceBetween));
				//Log.v("onHoursChanged",i+" : "+(int)((i*mHoursSpaceBetween)));
				mHourTextPosition.add(tInt);
			}
		}
	}
	
	private void onDataChanged() {
		float totalWidth = 0;
	    float tWidthAcumulated = 0;
    	float tWidhtAcumulated2 = 0;
    	
    	if(mGraphContainer.right>0){
    	
    		if(mNumberOfVisibleHours > 0 || mMinWidthVisible > 0){
    			//Log.v("onDataChanged","blabla mNumberOfVisibleHours | mMinWidthVisible");
    		}
    		// TODO keep in mind of this previous variables
    		
    		
		    if(mSingleViewMode){
		    	for(ItemHorizontalBar it : mData){ totalWidth += it.mWidth; }
		    	
		    	float availableSpaceInGraph = ( mGraphContainer.right - mGraphContainer.left ) - ((mData.size()-1)*mSpaceBetweenBars);
		    	
		    	for(ItemHorizontalBar it : mData){
		    		tWidhtAcumulated2 = tWidthAcumulated + ( ( it.mWidth / totalWidth ) * availableSpaceInGraph );
		    		it.mRect = new RectF(tWidthAcumulated, mGraphContainer.top, tWidhtAcumulated2, mGraphContainer.bottom);
		    		
		    		//Log.v("onDataChanged singleViewMode", it.mLabel+" size :"+it.mRect);
		    		
		    		tWidthAcumulated = tWidhtAcumulated2 + mSpaceBetweenBars;
		    	}
		    }else{
		    	
		    	tWidhtAcumulated2 = tWidthAcumulated = 0;
		    	
		    	for(ItemHorizontalBar it : mData){
		    		
		    		tWidhtAcumulated2 = tWidthAcumulated;
		    		tWidhtAcumulated2 += it.mWidth;
		    		
		    		//Log.v("onDataChanged (no singleViewMode)",it.mLabel+
		    		//		" iniPos: "+tWidthAcumulated+" endPos: "+tWidhtAcumulated2);
		    		
		    		it.mRect = new RectF(tWidthAcumulated,mGraphContainer.top,tWidhtAcumulated2,mGraphContainer.bottom);
		    		
		    		tWidthAcumulated = tWidhtAcumulated2 + mSpaceBetweenBars;
		    		
		    		// TODO verify if this is correct
		    		mTotalWidthGraphBar = tWidhtAcumulated2;
		    	}
		    }
		    //Log.v("onDataChanged"," : "+tWidhtAcumulated2);
		    onHoursChanged();
		    mGraph.invalidate();
    	}
	}
	
	private ItemHorizontalBar getFirstItem(){
		if(mData.size()>0)
			return mData.get(0);
		return new ItemHorizontalBar();
	}
	private ItemHorizontalBar getLastItem(){
		if(mData.size()>0)
			return mData.get(mData.size()-1);
		return new ItemHorizontalBar();
	}
	
	private void init() {
		Resources res = getResources();
		mGraphPosition = 0;
		
		
		
		mShadowMoreGraphAvailable = new Paint();
		mShadowMoreGraphAvailable.setStyle(Style.FILL);
		mShadowMoreGraphAvailable.setColor(res.getColor(R.color.customGreyDark2));	
		
		mHourTextPaint = new Paint();
		mHourTextPaint.setColor(Color.BLACK);
		mHourTextPaint.setTextSize(mHourTextSize);
	    
		mHourTextPaint.setAntiAlias(true);
		mHourTextPaint.setTextAlign(Align.LEFT);
		
		// TODO new Class mExternalBox = new GraphBarBackgrounds(getContext());
		mGraph = new HorGraphBar(getContext());
		mBackground = new ElementBackground(getContext());
		//Log.v("init ","Adding childs...");
		
		// TODO new Class addView(mExternalBox);
		addView(mBackground);
		addView(mGraph);
		
        //TODO mDetector = new GestureDetector(HorizontalGraph.this.getContext(), new GestureListener());
        //TODO mDetector.setIsLongpressEnabled(false);
		
		
		if(isInEditMode()){
						
			int tempMax = 5000;
	        float tempVal = 0;
	        int tempVal2 = 0;
	        int tempVal2_a = 0;
	        Random r = new Random();
			while( tempMax > 0 ){
	        	
				tempVal = r.nextInt(500);
	        	tempMax -= tempVal;
	        	tempVal2_a = tempVal2;
	        	while( tempVal2 == tempVal2_a ) tempVal2 = r.nextInt(6);
	        	
	        	switch(tempVal2){
	        		case 1: addItem(String.valueOf(tempVal+tempMax),tempVal,ColorMyCustom.COLOR_SLEEP_DEEP); break;
	        		case 2: addItem(String.valueOf(tempVal+tempMax),tempVal,ColorMyCustom.COLOR_SLEEP_LIGHT); break;
	        		case 3: addItem(String.valueOf(tempVal+tempMax),tempVal,ColorMyCustom.COLOR_EXERCISE_HEAVY); break;
	        		case 4: addItem(String.valueOf(tempVal+tempMax),tempVal,ColorMyCustom.COLOR_EXERCISE_LIGHT); break;
	        		case 5: addItem(String.valueOf(tempVal+tempMax),tempVal,ColorMyCustom.COLOR_NOT_MOVING_COMPUTER); break;
	        		case 6: addItem(String.valueOf(tempVal+tempMax),tempVal,ColorMyCustom.COLOR_NOT_MOVING); break;
	        	}
	        }
			
			for(int i = 0 ; i < 25 ; i++){
	        	if(i<10) addHour("0"+String.valueOf(i));
	        	else addHour(String.valueOf(i));
	        }	
			
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {}
	    
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
    	@Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
    		//Log.v("GestureListener > scroll ", " distanceX "+distanceX);
    		
    		
			if(mData.size()>0){
    			ItemHorizontalBar hor = getLastItem();
    			//boolean graphMoving = false;
    			
    			//Log.v("onScroll"," right "+hor.mRect.right+" pos "+mPositionGraph+" = "+(hor.mRect.right-mPositionGraph));
    			if(mGraphPosition >= 0 ){
    				float distanceXP = distanceX*1.5f;
    				float tPositionGraph = mGraphPosition + distanceXP;
    				
    				/*if(tPositionGraph != mGraphPosition){
    					graphMoving = true;
    				}*/
	    			
    				if(tPositionGraph<0)mGraphPosition = 0;
    				else mGraphPosition = tPositionGraph;

	    			if((hor.mRect.right-mGraphPosition)<mGraphContainer.right){
	    				mGraphPosition = hor.mRect.right-mGraphContainer.right;
	    			}
	    			

	    		}else{
	    			mGraphPosition = 0;
	    		}
    			
    			
    			onDataChanged();
        		//Log.v("HorizontalGraph > GestureListener > onScroll"," x : "+distanceX+" y : "+distanceY+" horizontal : "+(distanceX*2>distanceY)+" moving : "+graphMoving);
        		               
			}
			return true;    		
        }

        @Override
        public boolean onDown(MotionEvent e) {
            /*if (!mScroller.isFinished()) {
            	mScroller.forceFinished(true);
            }*/
        	//Log.v("GestureListener > down ", " only down :) ");
            return true;
        }
    }
    /*private class GraphBarBackgrounds extends View{

		public GraphBarBackgrounds(Context context) { super(context); }
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawRect(mExternalContainer, mExternalContainerFill);
			canvas.drawRect(mInternalContainerBorder, mInternalContainerBorderStroke);
			canvas.drawRect(mInternalContainer, mInternalContainerFill);
		}
		
	}*/
    
	private class HorGraphBar extends View{

		private RectF tRectB;
		private RectF tRectS;
		boolean shadowRight = false;

		public HorGraphBar(Context context) { 
			super(context); 
			tRectB = new RectF();
			tRectS = new RectF();
		}
		
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			
			shadowRight = false;
			float shadowSize = 2;
			float textSizeWithMargin = mHourTextSize*1.0f; // TODO puede que necesite más
			
			boolean breakable = false;
			
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
				
			}
		}
		
		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			// TODO Auto-generated method stub
			super.onSizeChanged(w, h, oldw, oldh);
			if(mGraphContainer.right != w || mGraphContainer.bottom != h){
				mGraphContainer.right = w;
				mGraphContainer.bottom = h;
				onDataChanged();
			}
		}
				
	}
	

}
