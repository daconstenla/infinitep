package es.daconstenla.infinip.customcomponents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;
import es.daconstenla.infinip.R;
import es.daconstenla.infinip.customcomponents.pieces.ElementBackground;
import es.daconstenla.infinip.customcomponents.pieces.ItemCalendarDay;
import es.daconstenla.infinip.customcomponents.pieces.data.BarElementData;
import es.daconstenla.infinip.customcomponents.pieces.data.ColorMyCustom;
import es.daconstenla.infinip.database.utils.Utils;

public class MonthView extends ViewGroup{
	
	private int mWidthPerDay;
	private int mWidth;
	private int mHeightPerDay;
	private int mHeight;
	
	private int mDistanceDays;
	
	private int mSidePadding = 15;
	private int mTopBotPadding = 15;
	
	// Previous and next days of the first and last week in a month
	private boolean mShowPrevDays = true; 
	private boolean mShowNextDays = true;
	// Other vars
	private int mMonthNumber = 0;
	private String mMonthNameString = "";
	private int mYear = 0;
	private int mCurrentDay = 0;
	private int mNumberDaysMonth = 31;
	private int mFirstWeekDay = Calendar.MONDAY;
	private int mFirstMonthDayWeek = Calendar.MONDAY;
	
	private boolean mIsDateSet;
	
	private List<ItemCalendarDay> mDaysInCalendar;
	private ElementBackground mBackground;
	private RectF mRectAvilableCalendar = new RectF();
	private TextView mMonthName = null;
	private int mMonthNameTextSize = 200;
	private List<TextView> mWeekDayList = null;
	private int mWeekDaysFontSize = 40;
	private String[] mWeekDaysName = {
	        getResources().getString(R.string.monday),
	        getResources().getString(R.string.tuesday),
	        getResources().getString(R.string.wednesday),
	        getResources().getString(R.string.thursday),
	        getResources().getString(R.string.friday),
	        getResources().getString(R.string.saturday),
	        getResources().getString(R.string.sunday)
	    };
	
	private Typeface robotoThin;
	private boolean[] haveChanged = new boolean[43];
	
	public MonthView(Context context) {
		super(context);
		init();
	}

	public MonthView(Context context, AttributeSet attrs) {
		super(context,attrs);
		
		 //setVerticalScrollBarEnabled(true);
		 //setHorizontalScrollBarEnabled(true);
         //setMinimumHeight(1000);
         //setMinimumWidth(2400);
        
		TypedArray a = context.getTheme().obtainStyledAttributes(
				attrs,
				R.styleable.HorizontalBarActivityGraph,
				0, 0
		);
		try {
			/*mMinWidthVisible = a.getFloat(R.styleable.HorizontalBarActivityGraph_minWithVisible, 0.0f);
			mNumberOfVisibleHours = a.getInt(R.styleable.HorizontalBarActivityGraph_numberOfVisibleHours, 10);
			mSpaceBetweenBars = a.getFloat(R.styleable.HorizontalBarActivityGraph_spaceBetweenBars, 2.0f);
			mSingleViewMode = a.getBoolean(R.styleable.HorizontalBarActivityGraph_singleViewMode, false);
			mHighlightBorderStrength = a.getFloat(R.styleable.HorizontalBarActivityGraph_highlightStrength, 0.9f);
			mHeightBorder = a.getFloat(R.styleable.HorizontalBarActivityGraph_HeightBarBorder, 20.0f);
			mDisplayHours = a.getBoolean(R.styleable.HorizontalBarActivityGraph_displayHours,false);*/
			
		} finally {
			// release the TypedArray so that it can be reused.
			a.recycle();
		}
		
		init();
	}
	
	private void init(){
			
		if(!isInEditMode())
			robotoThin = Typeface.createFromAsset(getContext().getAssets(), "fonts/robotothin.ttf");
		else
			robotoThin = Typeface.DEFAULT;
		
		mDaysInCalendar = new ArrayList<ItemCalendarDay>();
		ItemCalendarDay it;
		
		mMonthName = new TextView(getContext());
		mMonthName.setGravity(Gravity.CENTER);
		mMonthName.setTypeface(robotoThin);	
		mMonthName.setTextSize(mMonthNameTextSize);
		
		mBackground = new ElementBackground(getContext());
		
		addView(mBackground);
		addView(mMonthName);
		
		mWeekDayList = new ArrayList<TextView>();
		for(int i = 0 ; i < mWeekDaysName.length;i++){
			TextView te = new TextView(getContext());
			te.setText(mWeekDaysName[i]);
			te.setTextSize(mWeekDaysFontSize);
			te.setTypeface(robotoThin);
			te.setGravity(Gravity.RIGHT);
			addView(te);
			mWeekDayList.add(te);
		}
		
		Arrays.fill(haveChanged, false);
		
		for( int i = 0 ; i < 42 ; i ++){
			it = new ItemCalendarDay(getContext());
			mDaysInCalendar.add(it);
			this.addView(it);
		}
		
		if(isInEditMode()){
			
			setMonth(2);
			setYear(2014);
			setDay(24);
			randomFillCalendar();
		}
		
		if(isDateSet()) fillCalendarWithDays();
		if(mWidth>0&&mHeight>0) positionDaysInCalendar();
		
	}
	
	@Override 
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){

		 // ceil not round - avoid thin vertical gaps along the left/right edges
		 int width = MeasureSpec.getSize(widthMeasureSpec);
		 //int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
		 int height = MeasureSpec.getSize(heightMeasureSpec);
		 setMeasuredDimension(width, height);

    }
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int w = r-l;
		int h = b-t;
		
		if(changed){		
			r = w;
			b = h;
			
			// Full background size
			mBackground.layout(0, 0, w, h);
			mBackground.setPadding(mSidePadding, mTopBotPadding, mSidePadding, mTopBotPadding);
			// Size available after paddings
			mRectAvilableCalendar = mBackground.getRectInternalAvailable();
			// Calculate size elements 
			mWidth = (int) mRectAvilableCalendar.width();
		    mWidthPerDay = mWidth/7;
		    mHeight = (int) mRectAvilableCalendar.height();
		    mHeightPerDay = mHeight/9;
		    
		    // Set size of mMonthName
		    mMonthName.layout((int)mRectAvilableCalendar.left, (int)mRectAvilableCalendar.top, (int)mRectAvilableCalendar.right, (int)(mHeightPerDay*1.5));
		    mMonthName.setTextSize(mMonthNameTextSize);
		    Utils.resizeTextToFitInLayout(mMonthName, mMonthNameTextSize);
		    
		    //Log.d(getClass().getName(), "["+changed+"][tot] t : "+t+" b : "+b+" l : "+l+" r : "+r);
		    //Log.d(getClass().getName(), "["+changed+"][rec] t : "+mRectAvilableCalendar.top+" b : "+mRectAvilableCalendar.bottom+" l : "+mRectAvilableCalendar.left+" r : "+mRectAvilableCalendar.right);
		    //Log.d("-nDays-","days: "+mDaysInCalendar.size()+"prop w: "+mWidthPerDay+" h: "+mHeightPerDay);
		    
		    int left = (int)mRectAvilableCalendar.left;
		    int right = 0;
		    int top = (int)mRectAvilableCalendar.top+(int)(mHeightPerDay*1.6);
		    int bottom = top+(int)(mHeightPerDay*0.6);
		    
		    mDistanceDays = top+(int)(mHeightPerDay*0.4);
		    mHeightPerDay = (mHeight-mDistanceDays)/6;
		    		    
		    TextView tTxt = mWeekDayList.get(0);
		    tTxt.layout(left, top, right, bottom);
		    Utils.resizeTextToFitInLayout(tTxt, mWeekDaysFontSize);	    
		    float tempSize = tTxt.getTextSize();
		    
		    for(TextView te : mWeekDayList ){
		    	right = left+mWidthPerDay;
		    	te.layout(left, top, right, bottom);
		    	te.setTextSize(TypedValue.COMPLEX_UNIT_PX, tempSize);
		    	te.setText(te.getText());
		    	left+=mWidthPerDay;
		    }
		    
		    positionDaysInCalendar();
		}
		
	}
	
	private void positionDaysInCalendar(){
    	int topBase = ((int)mRectAvilableCalendar.top)+mDistanceDays;
    	int leftBase = (int)mRectAvilableCalendar.left;
    	
		int column = 0;
    	if(mDaysInCalendar.size()>0){
	    	for(ItemCalendarDay it : mDaysInCalendar){
	    		
	    		it.layout(leftBase,topBase,leftBase+mWidthPerDay,topBase+mHeightPerDay);

	    		it.setPadding(5, 5, 5, 5);
	    		
	    		leftBase+=mWidthPerDay;
	    		column++;
	    		
	    		if(column==7){
	    			topBase += mHeightPerDay;
	    			leftBase = (int)mRectAvilableCalendar.left;
	    			column = 0;
	    		}
	    	}
    	}
    }
	private void fillCalendarWithDays(){
		
		// Once date is set
		// Get values constants of the month
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, mMonthNumber);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.YEAR, mYear);
		
		mNumberDaysMonth = cal.getMaximum(Calendar.DAY_OF_MONTH);
		//mNumberOfWeeks = cal.getMaximum(Calendar.WEEK_OF_MONTH);

		mFirstMonthDayWeek = cal.get(Calendar.DAY_OF_WEEK);
		cal.set(Calendar.DAY_OF_MONTH, mNumberDaysMonth);
		//mLastMonthDayWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		int nDaysBefore = Utils.numberOfDaysBeforeInWeek(mFirstWeekDay, mFirstMonthDayWeek);
		//int nDaysAfter = Utils.numberOfDaysAfterInWeek(mFirstWeekDay, mLastMonthDayWeek);
    	
		// Previous month days to display
		if(mMonthNumber>1) cal.set(Calendar.MONTH, mMonthNumber-1);
		else cal.set(Calendar.MONTH, 12);
		
		int nFirstPrevMonthDayToDisplay = cal.getMaximum(Calendar.DAY_OF_MONTH)-nDaysBefore;

		int i = nFirstPrevMonthDayToDisplay;
		int d = 0;
		ItemCalendarDay it;
		
		for(;i<cal.getMaximum(Calendar.DAY_OF_MONTH);i++,d++){
			it = mDaysInCalendar.get(d);
			if(!mShowPrevDays) it.setAlpha(0);
			else{
				it.setNumberOfDay(i);
				it.setIsOtherMonth(true);
			}
		}
		
		for(i=1;i<mNumberDaysMonth;i++,d++){
			it = mDaysInCalendar.get(d);
			it.setNumberOfDay(i);
			if(i == mCurrentDay){
				it.setIsCurrentDay(true);
			}
			it.setIsOtherMonth(false);
		}
		
		for(i=1;d<mDaysInCalendar.size();d++,i++){
			it = mDaysInCalendar.get(d);
			if(!mShowNextDays) it.setAlpha(0);
			else{
				it.setNumberOfDay(i);
				it.setIsOtherMonth(true);
			}
		}
	
	}
	
	public void setMonth(int month){
		
		if(month > 0 && month < 13){
		month--;
			switch(month){
				case Calendar.JANUARY: mMonthNameString = getResources().getString(R.string.january); break;
				case Calendar.FEBRUARY: mMonthNameString = getResources().getString(R.string.february); break;
				case Calendar.MARCH: mMonthNameString = getResources().getString(R.string.march); break;
				case Calendar.APRIL: mMonthNameString = getResources().getString(R.string.april); break;
				case Calendar.MAY: mMonthNameString = getResources().getString(R.string.may); break;
				case Calendar.JUNE: mMonthNameString = getResources().getString(R.string.june); break;
				case Calendar.JULY: mMonthNameString = getResources().getString(R.string.july); break;
				case Calendar.AUGUST: mMonthNameString = getResources().getString(R.string.august); break;
				case Calendar.SEPTEMBER: mMonthNameString = getResources().getString(R.string.september); break;
				case Calendar.OCTOBER: mMonthNameString = getResources().getString(R.string.october); break;
				case Calendar.NOVEMBER: mMonthNameString = getResources().getString(R.string.november); break;
				case Calendar.DECEMBER: mMonthNameString = getResources().getString(R.string.december); break;
			}
			
			if(mYear!=0)
				mMonthName.setText(mMonthNameString+" "+mYear);
			else
				mMonthName.setText(mMonthNameString);
			
			mMonthNumber = month;
			if(mIsDateSet || isDateSet()) {
				fillCalendarWithDays();
				mIsDateSet = true;
			}
		}
	}
	public void setDay(int day){
		if(day > 0 && day < 32){
			mCurrentDay = day;
		}
	}
	public void setYear(int year){
		if(year > 2000 ){
			mYear = year;
			if(mIsDateSet || isDateSet()) {
				mMonthName.setText(mMonthNameString+" "+mYear);
				fillCalendarWithDays();
				mIsDateSet = true;
			}
		}
	}
	
	public void setDate(int day,int month,int year){
		setDay(day);
		setMonth(month);
		setYear(year);
	}
	
	private boolean isDateSet(){
		return (mYear!=0&&mMonthNumber>=0);
	}
	
	public void randomFillCalendar(){
		
		for(ItemCalendarDay day:mDaysInCalendar){
			int sl = 10;
			int sd = 10;
			int el = 10;
			int eh = 10;
			int nm = 10;
			int np = 10;
			
			Resources res = getResources();
	        Random r = new Random();
	        int ra = r.nextInt(50),col;
	        for(int i = 0 ; i < ra ; i++){
	        	col = r.nextInt(6);
	        	switch(col){
	        	case 0:	sl+=r.nextInt(10);
	        		break;
	        	case 1: sd+=r.nextInt(10);
	        		break;
	        	case 2: el+=r.nextInt(10);
	        		break;
	        	case 3: eh+=r.nextInt(10);
	        		break;
	        	case 4: nm+=r.nextInt(10);
	        		break;
	        	case 5: np+=r.nextInt(10);
	        		break;
	        	}
	        }
	        day.addElemToBarInfo(new BarElementData(sl,res.getColor(ColorMyCustom.COLOR_SLEEP_LIGHT),res.getColor(ColorMyCustom.COLOR_SLEEP_LIGHT_shadow)));
	        day.addElemToBarInfo(new BarElementData(sd,res.getColor(ColorMyCustom.COLOR_SLEEP_DEEP),res.getColor(ColorMyCustom.COLOR_SLEEP_DEEP_shadow)));
	        day.addElemToBarInfo(new BarElementData(el,res.getColor(ColorMyCustom.COLOR_EXERCISE_LIGHT),res.getColor(ColorMyCustom.COLOR_EXERCISE_LIGHT_shadow)));
	        day.addElemToBarInfo(new BarElementData(eh,res.getColor(ColorMyCustom.COLOR_EXERCISE_HEAVY),res.getColor(ColorMyCustom.COLOR_EXERCISE_HEAVY_shadow)));
	        day.addElemToBarInfo(new BarElementData(nm,res.getColor(ColorMyCustom.COLOR_NOT_MOVING),res.getColor(ColorMyCustom.COLOR_NOT_MOVING_shadow)));
	        day.addElemToBarInfo(new BarElementData(np,res.getColor(ColorMyCustom.COLOR_NOT_MOVING_COMPUTER),res.getColor(ColorMyCustom.COLOR_NOT_MOVING_COMPUTER_shadow)));
		}
		
	}
	
	public int getItemCalendarTouched(float x, float y){
		int item = 0;
		int roundRow;
		int roundCol;
		
		if(y<mDistanceDays){
			return -1;
		}else{
			float col = x/mWidthPerDay;
			float row = (y-mDistanceDays)/mHeightPerDay;
			roundRow = Math.round(row);
			roundCol = Math.round(col);
			if(roundRow == 0) roundRow = 1;
			if(roundCol == 0) roundCol = 1;
			if(roundRow > 6) roundRow = 6;
			
			item = ((roundRow-1)*7)+roundCol;
			//Log.d("t","r : "+roundRow+" c : "+roundCol+" i : "+item);
		}
		
		return --item;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		
		ItemCalendarDay it = null;
		int index = getItemCalendarTouched(e.getAxisValue(MotionEvent.AXIS_X), e.getAxisValue(MotionEvent.AXIS_Y));
		if(index >= 0){ 
			it = mDaysInCalendar.get(index);
		
			switch (e.getAction()) {
		    case MotionEvent.ACTION_MOVE:
		    	Log.d("MOVE","cal touched on item "+index);
		    	if(it!=null && !haveChanged[index]){
		    		haveChanged[index] = true;
	    			it.setSelected(!it.getIsSelected());
		    	}else{
		    		Log.d("MOVE","["+index+"]"+"("+it.getIsSelected()+"){"+haveChanged[index]+"}");
		    	}
		    	break;
		    case MotionEvent.ACTION_DOWN:
		    	Log.d("DOWN","cal touched on item "+index);
		    		if(it!=null){ 
		    			haveChanged[index] = true;
		    			it.setSelected(!it.getIsSelected());
		    		}
				break;
		    case MotionEvent.ACTION_UP:
		    	Log.d("UP","cal touched on item "+index);
		    	Arrays.fill(haveChanged, false);
		    	break;
			}
		}
		return true;
	}
}
