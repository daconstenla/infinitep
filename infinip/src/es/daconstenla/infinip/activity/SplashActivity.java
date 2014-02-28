package es.daconstenla.infinip.activity;

import es.daconstenla.infinip.R;
import es.daconstenla.infinip.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SplashActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_splash);
        
        /*       
        ItemCalendarDay item = (ItemCalendarDay) this.findViewById(R.id.dayTesting3);
        Resources res = getResources();
        Random r = new Random();
        int ra = r.nextInt(50),col;
        for(int i = 0 ; i < ra ; i++){
        	col = r.nextInt(6);
        	switch(col){
        	case 0:	item.addElemToBarInfo(new BarElementData(r.nextInt(10),res.getColor(ColorMyCustom.COLOR_SLEEP_LIGHT)));
        		break;
        	case 1:item.addElemToBarInfo(new BarElementData(r.nextInt(10),res.getColor(ColorMyCustom.COLOR_SLEEP_DEEP)));
        		break;
        	case 2:item.addElemToBarInfo(new BarElementData(r.nextInt(10),res.getColor(ColorMyCustom.COLOR_EXERCISE_LIGHT)));
        		break;
        	case 3: item.addElemToBarInfo(new BarElementData(r.nextInt(10),res.getColor(ColorMyCustom.COLOR_EXERCISE_HEAVY)));
        		break;
        	case 4:item.addElemToBarInfo(new BarElementData(r.nextInt(10),res.getColor(ColorMyCustom.COLOR_NOT_MOVING)));
        		break;
        	case 5:item.addElemToBarInfo(new BarElementData(r.nextInt(10),res.getColor(ColorMyCustom.COLOR_NOT_MOVING_COMPUTER)));
        		break;
        	}
        }
        
        item.setNumberOfDay(Utils.numberOfDaysAfterInWeek(Calendar.MONDAY,Calendar.WEDNESDAY));
        
        
        Calenda cal = (Calenda) this.findViewById(R.id.dayTesting3);
        cal.setDay(12);
        cal.randomFillCalendar();
        
        
        Log.d(this.getClass().getName(),"M "+Utils.numberOfDaysAfterInWeek(Calendar.MONDAY,Calendar.MONDAY));
        Log.d(this.getClass().getName(),"T "+Utils.numberOfDaysAfterInWeek(Calendar.MONDAY,Calendar.TUESDAY));
        Log.d(this.getClass().getName(),"W "+Utils.numberOfDaysAfterInWeek(Calendar.MONDAY,Calendar.WEDNESDAY));
        Log.d(this.getClass().getName(),"T "+Utils.numberOfDaysAfterInWeek(Calendar.MONDAY,Calendar.THURSDAY));
        Log.d(this.getClass().getName(),"F "+Utils.numberOfDaysAfterInWeek(Calendar.MONDAY,Calendar.FRIDAY));
        Log.d(this.getClass().getName(),"S "+Utils.numberOfDaysAfterInWeek(Calendar.MONDAY,Calendar.SATURDAY));
        Log.d(this.getClass().getName(),"S "+Utils.numberOfDaysAfterInWeek(Calendar.MONDAY,Calendar.SUNDAY));
        */
        
	}
	
	public void testButton(View view){
		//Toast.makeText(this, "woooo", Toast.LENGTH_LONG).show();
		
		Intent mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
		startActivity(mainIntent);
	    finish();
	}
}
