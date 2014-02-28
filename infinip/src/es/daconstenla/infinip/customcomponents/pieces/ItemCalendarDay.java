package es.daconstenla.infinip.customcomponents.pieces;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import es.daconstenla.infinip.R;
import es.daconstenla.infinip.customcomponents.pieces.data.BarElementData;
import es.daconstenla.infinip.customcomponents.pieces.data.ColorMyCustom;
import es.daconstenla.infinip.database.utils.Utils;

public class ItemCalendarDay extends ViewGroup{

	// In common to all Items
	private static int mPaddingTop = 5;
	private static int mPaddingBottom = 5;
	private static int mPaddingLeft = 5;
	private static int mPaddingRight = 5;
	private static int mWidth = 380;
	private static int mHeigh = 100;
	private static int mHeightWOPadding = 470;
	private static int mBackgroundColor = android.R.color.white;
	private static int mBackgroundColorOtherMonth = android.R.color.white;
	// Bar vars
	private static int mHeighBar = 10;
	private static int mHeighBarShadow = 2;
	private static int mWidthBar = 15;
	// Text
	private static int mFontDayNumberSize = 100;
	private static int mFontCurrentColor = R.color.calendarTextColorCurrentDay;
	private static int mFontPrevNextColor = R.color.calendarTextColorPrevNextMonth;
	private String mNumberOfDay = "0";
	// Container
	private static Paint mBackgroundPaint = null;
	private static Paint mBackgroundPaintOtherMonth = null;
	private static RectF mBackgroundContainer = null;
	private static RectF mBackgroundContainerFull = null;
	private static Paint mBackgroundPaintFull = null;
	private static int mBackgroundContainerFullColor = R.color.calendarBackgroundSelectedDay;

	// uncommon vars

	private boolean mIsThisMonthDay = true;

	// Alternative modes values
	private boolean mIsBackgroundAlternative = false;
	private int mBackgroundAlternativeColor = android.R.color.background_dark;
	private Paint mBackgroundAlternativePaint = null;

	// Views
	private DayBackground backgroundDay;
	private SelectedBackground backgroundSelection;
	private HorizontalBarData infoBar;
	private TextView textDay;

	// Use interface
	private boolean isSelected;
	private boolean isCurrentDay;
	
	public ItemCalendarDay(Context context, AttributeSet attrs) {
		super(context, attrs);
		_init_();
	}
	public ItemCalendarDay(Context context) {
		super(context);
		_init_();
	}

	private void _init_(){

		if(mBackgroundPaint == null){
			mBackgroundPaint = new Paint();
			mBackgroundPaint.setStyle(Style.FILL_AND_STROKE);
			mBackgroundPaint.setColor(getResources().getColor(mBackgroundColor));
		}
		if(mIsBackgroundAlternative){
			mBackgroundAlternativePaint = new Paint();
			mBackgroundAlternativePaint.setStyle(Style.FILL_AND_STROKE);
			mBackgroundAlternativePaint.setColor(getResources().getColor(mBackgroundAlternativeColor));
		}
		if(mBackgroundPaintOtherMonth == null){ 
			mBackgroundPaintOtherMonth = new Paint();
			mBackgroundPaintOtherMonth.setStyle(Style.FILL_AND_STROKE);
			mBackgroundPaintOtherMonth.setColor(getResources().getColor(mBackgroundColorOtherMonth));
		}
		if(mBackgroundPaintFull == null){
			mBackgroundPaintFull = new Paint();
			mBackgroundPaintFull.setStyle(Style.FILL_AND_STROKE);
			mBackgroundPaintFull.setColor(getResources().getColor(mBackgroundContainerFullColor));
		}

		// Views
		backgroundDay = new DayBackground(getContext());
		backgroundSelection = new SelectedBackground(getContext());
		infoBar = new HorizontalBarData(getContext());
		textDay = new TextView(getContext());
		
		textDay.setGravity(Gravity.RIGHT);
		textDay.setTextSize(mFontDayNumberSize);
		textDay.setText(mNumberOfDay);
		
		//if(isInEditMode()) textDay.setBackgroundColor(Color.parseColor("#e7e7e7"));		

		if(!mIsThisMonthDay){
			textDay.setTextColor(getResources().getColor(mFontPrevNextColor));
		}else{
			textDay.setTextColor(getResources().getColor(mFontCurrentColor));
		}


		addView(backgroundSelection);
		addView(backgroundDay);
		addView(infoBar);
		addView(textDay);
			
	
		if(isInEditMode()){

			addElemToBarInfo(new BarElementData(10,getResources().getColor(ColorMyCustom.COLOR_SLEEP_LIGHT),getResources().getColor(ColorMyCustom.COLOR_SLEEP_LIGHT_shadow)));
			addElemToBarInfo(new BarElementData(20,getResources().getColor(ColorMyCustom.COLOR_SLEEP_DEEP),getResources().getColor(ColorMyCustom.COLOR_SLEEP_DEEP_shadow)));
			addElemToBarInfo(new BarElementData(30,getResources().getColor(ColorMyCustom.COLOR_EXERCISE_LIGHT),getResources().getColor(ColorMyCustom.COLOR_EXERCISE_LIGHT_shadow)));
			addElemToBarInfo(new BarElementData(50,getResources().getColor(ColorMyCustom.COLOR_EXERCISE_HEAVY),getResources().getColor(ColorMyCustom.COLOR_EXERCISE_HEAVY_shadow)));
			addElemToBarInfo(new BarElementData(40,getResources().getColor(ColorMyCustom.COLOR_NOT_MOVING),getResources().getColor(ColorMyCustom.COLOR_NOT_MOVING_shadow)));
			addElemToBarInfo(new BarElementData(50,getResources().getColor(ColorMyCustom.COLOR_NOT_MOVING_COMPUTER),getResources().getColor(ColorMyCustom.COLOR_NOT_MOVING_COMPUTER_shadow)));
		}

	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		if(changed){
			int h = b-t;
			int w = r-l;
			
			mWidth = w;
			mHeigh = h;
			
			int tB = h - (mHeighBar+mPaddingTop+mPaddingBottom*2); // Temporal Top Position
			int tR = (w - (mPaddingRight+mPaddingLeft+mPaddingRight*2));
			int tL = (int)(tR/1.6);
			int tT = tB/2;
			
			if(tR-tL<tB-tT){
				tT=tB-(int)((tR-tL)*1.5);
			}
	
			// Container
			if(mBackgroundContainerFull == null){
				mBackgroundContainerFull = new RectF(0,0,mWidth,mHeigh);
			}
			if(mBackgroundContainer == null) 
				mBackgroundContainer = new RectF(mPaddingLeft*4, 0, mWidth-mPaddingRight*2, mHeigh);
			
			// Fit text in space available?
			
			textDay.layout(tL, tT, tR, tB);
			Utils.resizeTextToFitInLayout(textDay,mFontDayNumberSize);	
			textDay.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			while(textDay.getMeasuredWidth()>(tR-tL)){
				
				tL-=10;
				textDay.layout(tR-textDay.getMeasuredWidth(), tT, tR, tB);
				textDay.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
				Log.d("P["+(textDay.getMeasuredWidth()>tR-tL)+"]"+textDay.getMeasuredWidth(),"("+textDay.getText()+")l : "+(tR-textDay.getMeasuredWidth())+" r : "+tR+" t : "+tT+" b : "+tB+" ");
				
			}
			
			// Force measure without Specs
			//textDay.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			resetComponentesView(true);
		}
	}
	
	/**
	 * Force components from the viewGroup to redraw
	 * @param layout if set to true will calculate the position and dimension of the components, 
	 * if false only will redraw with the same position/dimension
	 */
	private void resetComponentesView(boolean layout){

		// l t r b
		if(layout){
			mHeightWOPadding = (mHeigh-(mPaddingBottom+mPaddingTop));
			if(mBackgroundContainer!=null)
				mBackgroundContainer.set(mPaddingLeft, 0, mWidth-mPaddingRight*4, mHeigh);
			if(mBackgroundContainerFull !=null)
				mBackgroundContainerFull.set(0,0,mWidth,mHeigh);
		}

		if(layout){
			backgroundDay.layout(
					(int) mPaddingLeft, 
					(int) mPaddingTop,
					(int) (mWidth-mPaddingRight), 
					(int) (mHeigh-mPaddingRight));		
		}
		
		if(layout){
			backgroundSelection.layout(
					(int) 0, 
					(int) 0,
					(int) mWidth, 
					(int) mHeigh);
		}

		mWidthBar = (mWidth-(mPaddingRight));
		if(layout){
			infoBar.layout(
					(int) (backgroundDay.getLeft()+mPaddingLeft),
					(int) (mHeightWOPadding-mHeighBar), 
					(int) (mWidthBar-mPaddingRight), 
					(int) (mHeightWOPadding));
		}

		infoBar.reCalculateSize();
		infoBar.invalidate();
		
		if(layout){
			textDay.setText(mNumberOfDay);
		}
	}
	
	public void setHeightBar(int height){
		mHeighBar = height;
	}
	public void setFontSize(int size){mFontDayNumberSize = size;resetComponentesView(true);}
	public void setWidth(int width){mWidth = width;resetComponentesView(true);}
	public void setHeight(int height){mHeigh = height;resetComponentesView(true);}
	public void setHeightDataBar(int height){mHeighBar = height;resetComponentesView(true);}
	public void setPadding(int top,int right,int bottom,int left){mPaddingTop = top; mPaddingBottom = bottom; mPaddingLeft = left; mPaddingRight = right;resetComponentesView(true);}

	public void setNumberOfDay(int number){
		mNumberOfDay = String.valueOf(number);
		textDay.setText(mNumberOfDay);
	}
	public void addElemToBarInfo(BarElementData elem){ infoBar.addElementToBarInfo(elem); }

	public void setIsOtherMonth(boolean isOtherMonth){
		mIsThisMonthDay = !isOtherMonth;
		if(!mIsThisMonthDay){
			textDay.setTextColor(getResources().getColor(mFontPrevNextColor));
		}else{
			textDay.setTextColor(getResources().getColor(mFontCurrentColor));
		}
	}
	public void setIsCurrentDay(boolean isCurrent){
		isCurrentDay = isCurrent;
		setAlternativeBackground(getResources().getColor(R.color.calendarBackgroundCurrentDay));
		setAlternativeFontColor(getResources().getColor(R.color.calendarTextColorCurrentDay),true);		
	}
	public boolean getIsCurrentDay(){
		return isCurrentDay;
	}
	public void setAlternativeBackground(int altColor){	
		mBackgroundAlternativeColor = altColor;
		setAlternativeBackground();
	}

	public void setAlternativeFontColor( int fontColor,boolean isBold){
		if(isBold) textDay.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		textDay.setTextColor(fontColor);
	}
	public void setAlternativeBackground(){
		mIsBackgroundAlternative = true;

		mBackgroundAlternativePaint = new Paint();
		mBackgroundAlternativePaint.setStyle(Style.FILL_AND_STROKE);
		mBackgroundAlternativePaint.setColor(mBackgroundAlternativeColor);
		backgroundDay.invalidate();
	}
	public void setAlternativeEnabled(boolean isEnabled){
		mIsBackgroundAlternative = isEnabled;
		backgroundDay.invalidate();
	}
	public void setSelected(boolean isSelected){
		this.isSelected = isSelected;
		backgroundDay.invalidate();
		backgroundSelection.invalidate();
	}
	public boolean getIsSelected(){return isSelected;}	
	public int getNumberDay(){return Integer.valueOf(mNumberOfDay);}
	protected class DayBackground extends View{
		public DayBackground(Context context) { super(context); }

		@Override
		public void onDraw(Canvas canvas){
			super.onDraw(canvas);
			if(mBackgroundContainer!=null){
				if(!isSelected){
					if(!mIsBackgroundAlternative){ 
						if(mIsThisMonthDay)
							canvas.drawRect(mBackgroundContainer, mBackgroundPaint);
						else
							canvas.drawRect(mBackgroundContainer, mBackgroundPaintOtherMonth);
					}else{ 
						canvas.drawRect(mBackgroundContainer, mBackgroundAlternativePaint);
					}
				}
			}
		}
	}
	protected class SelectedBackground extends View{
		public SelectedBackground(Context context) { super(context); }

		@Override
		public void onDraw(Canvas canvas){
			super.onDraw(canvas);
			if(mBackgroundContainer!=null){
				if(isSelected){
					canvas.drawRect(mBackgroundContainerFull, mBackgroundPaintFull);
				}
			}
		}
	}
	protected class HorizontalBarData extends View{
		private int mBarTotalWidth = 0;
		private List<BarElementData> mBarData = new ArrayList<BarElementData>();

		public HorizontalBarData(Context context){ super(context); }

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			for(BarElementData elem : mBarData){
				try{
					canvas.drawRect(elem.getRect().left,elem.getRect().top,elem.getRect().right,elem.getRect().bottom-mHeighBarShadow, elem.getPaint());
					canvas.drawRect(elem.getRect().left,elem.getRect().top+mHeighBarShadow,elem.getRect().right,elem.getRect().bottom, elem.getPaintAlt());
					//canvas.drawRect(elem.getRect(), elem.getPaint());
				}catch(NullPointerException e){/*TODO empty bar maybe*/}
			}
		}

		public void addElementToBarInfo(BarElementData element){

			mBarTotalWidth += element.getWidth();
			mBarData.add(element);

			reCalculateSize();

		}

		// TODO addElementToBarInfo List (optimized, only 1 call to reCalculateSize per list)

		public void reCalculateSize(){

			//Log.d(this.getClass().getName(),"totW : "+getWidth());
			if(mBarData.size()>0){
				float tProportion = (float)(getWidth())/mBarTotalWidth;

				int acumWidth = 0,twidth;


				for(BarElementData barE:mBarData){

					twidth = (int)(tProportion*(float)barE.getWidth());
					barE.setRect(new RectF(acumWidth, 0, twidth+acumWidth, mHeighBar));
					acumWidth += twidth;

				}
				if(acumWidth < getWidth()){
					RectF tRec =  mBarData.get(mBarData.size()-1).getRect();
					tRec.set(tRec.left, tRec.top, tRec.right+(acumWidth-getWidth()), tRec.bottom);
					mBarData.get(mBarData.size()-1).setRect(tRec);
				}
			}
		}

	}

}
