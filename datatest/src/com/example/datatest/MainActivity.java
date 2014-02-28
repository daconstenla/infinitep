package com.example.datatest;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.example.datatest.database.InfinitepHelper;
import com.example.datatest.databaseTest.DatabaseTester;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		InfinitepHelper data = new InfinitepHelper(this.getApplicationContext());
		
		DatabaseTester.runTests(data);
		
		getApplicationContext().deleteDatabase("infinitep.db");
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
