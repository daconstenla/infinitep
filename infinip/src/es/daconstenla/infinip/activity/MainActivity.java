package es.daconstenla.infinip.activity;

import java.util.Random;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import es.daconstenla.infinip.R;
import es.daconstenla.infinip.R.id;
import es.daconstenla.infinip.R.layout;
import es.daconstenla.infinip.R.menu;
import es.daconstenla.infinip.customcomponents.HorizontalGraph;
import es.daconstenla.infinip.customcomponents.pieces.data.ColorMyCustom;

public class MainActivity extends Activity {

	private static String POS = "position";
	//private SelectorColor ctlColor;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR); // Add this line
        setContentView(R.layout.activity_main);
        if( getActionBar() != null){
        	ActionBar actionBar = getActionBar();
        	actionBar.show();
        }else
        	Log.d("oh","ss null :o");
        //Resources res = getResources();
        
        float tempF = 0;
        if(savedInstanceState != null && savedInstanceState.containsKey(POS))
        	tempF = (Float) savedInstanceState.get(POS);
        else
        	tempF = 0;

        final HorizontalGraph graph = (HorizontalGraph) this.findViewById(R.id.HorizontalBar);

        int tempMax = 5000;
        float tempVal = 0;
        int tempVal2 = 0;
        int tempVal2_a = 0;
        Random r = new Random();
        //Log.v("init"," add data");
		while( tempMax > 0 ){
			
        	tempVal = r.nextInt(500);
        	tempMax -= tempVal;
        	tempVal2_a = tempVal2;
        	while( tempVal2 == tempVal2_a ) tempVal2 = r.nextInt(6);
        	
        	switch(tempVal2){
        		case 1: graph.addItem(String.valueOf(tempVal+tempMax),tempVal,ColorMyCustom.COLOR_SLEEP_DEEP); break;
        		case 2: graph.addItem(String.valueOf(tempVal+tempMax),tempVal,ColorMyCustom.COLOR_SLEEP_LIGHT); break;
        		case 3: graph.addItem(String.valueOf(tempVal+tempMax),tempVal,ColorMyCustom.COLOR_EXERCISE_HEAVY); break;
        		case 4: graph.addItem(String.valueOf(tempVal+tempMax),tempVal,ColorMyCustom.COLOR_EXERCISE_LIGHT); break;
        		case 5: graph.addItem(String.valueOf(tempVal+tempMax),tempVal,ColorMyCustom.COLOR_NOT_MOVING_COMPUTER); break;
        		case 6: graph.addItem(String.valueOf(tempVal+tempMax),tempVal,ColorMyCustom.COLOR_NOT_MOVING); break;
        	}
        	//Log.v("init"," add data "+tempMax);
        }
        
		//Log.v("init"," add hours");
		for(int i = 0 ; i < 25 ; i++){
        	if(i<10) graph.addHour("0"+String.valueOf(i));
        	else graph.addHour(String.valueOf(i));
        }
		
		graph.setGraphPosition(tempF);
	        
    }

    private void launchCalendarActivity(){
    	Intent calendarIntent = new Intent().setClass(MainActivity.this, CalendarActivity.class);
		startActivity(calendarIntent);
    }
    
    private void launchRangeDateActivity(){
    	Intent rangeDateIntent = new Intent().setClass(MainActivity.this, RangeActivity.class);
    	startActivity(rangeDateIntent);
    }
    
    private void launchSyncActivity(){
    	Intent syncDateIntent = new Intent().setClass(MainActivity.this, SyncActivity.class);
    	startActivity(syncDateIntent);
    }
        
    private void launchSettingsActivity(){
    	Intent settingsIntent = new Intent().setClass(MainActivity.this, SettingsActivity.class);
    	startActivity(settingsIntent);
    }
    
    

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	outState.putFloat(POS, ((HorizontalGraph) this.findViewById(R.id.HorizontalBar)).getGraphPosition());
    	super.onSaveInstanceState(outState);
    	//Log.v("onSaveInstanceState" , " saved state? :O");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        	case R.id.settings_settings:
        		launchSettingsActivity();
            return true;
        	case R.id.settings_calendar:
        		launchCalendarActivity();
            return true;
        	case R.id.settings_range:
        		launchRangeDateActivity();
            return true;
        	case R.id.settings_sync:
        		launchSyncActivity();
            return true;
        
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
}
