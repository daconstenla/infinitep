package es.daconstenla.infinip.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import es.daconstenla.infinip.R;
import es.daconstenla.infinip.customcomponents.MonthFragmentTest;
import es.daconstenla.infinip.customcomponents.MyFragmentMonthAdapter;

//TODO añadiendo el fragment adapter pero nah, es old way, habrá algo nuevo
// Tabfragment?
public class CalendarActivity  extends FragmentActivity {
	/**
	 * The pager widget, which handles animation and allows swiping horizontally
	 * to access previous and next pages.
	 */
	ViewPager pager = null;

	/**
	 * The pager adapter, which provides the pages to the view pager widget.
	 */
	MyFragmentMonthAdapter pagerAdapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.setContentView(R.layout.fragment_viewpager);

		// Instantiate a ViewPager
		this.pager = (ViewPager) this.findViewById(R.id.pager);

		// Create an adapter with the fragments we show on the ViewPager
		MyFragmentMonthAdapter adapter = new MyFragmentMonthAdapter(
				getSupportFragmentManager());
		
		/*adapter.addFragment(MonthFragmentTest.newInstance(getResources()
				.getColor(R.color.activityLighterOrange), 0));
		adapter.addFragment(MonthFragmentTest.newInstance(getResources()
				.getColor(R.color.activityLightBlue), 1));
		adapter.addFragment(MonthFragmentTest.newInstance(getResources()
				.getColor(R.color.activityLightGreen), 2));
		adapter.addFragment(MonthFragmentTest.newInstance(getResources()
				.getColor(R.color.activityLighterRed), 3));*/
		
		
		
		this.pager.setAdapter(adapter);

	}

	@Override
	public void onBackPressed() {

		// Return to previous page when we press back button
		if (this.pager.getCurrentItem() == 0)
			super.onBackPressed();
		else
			this.pager.setCurrentItem(this.pager.getCurrentItem() - 1);

	}
	/*
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_calendar);
        
        
        MonthView cal = (MonthView) this.findViewById(R.id.calenda1);
        cal.setYear(2014);
        cal.setDay(12);
        cal.setMonth(1);
        
        cal.randomFillCalendar();
        
        cal.setOnTouchListener(new OnSwipeTouchListener(this) {
        	@Override
            public void onSwipeTop() {
                Toast.makeText(CalendarActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
        	@Override
            public void onSwipeRight() {
                Toast.makeText(CalendarActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
        	@Override
            public void onSwipeLeft() {
                Toast.makeText(CalendarActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
        	@Override
            public void onSwipeBottom() {
                Toast.makeText(CalendarActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }
        	@Override
	        public boolean onTouch(View v, MotionEvent event) {
	            return gestureDetector.onTouchEvent(event);
	        }
        });
	}*/

/*

	@Override
	public android.support.v4.app.Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}*/
	
}
