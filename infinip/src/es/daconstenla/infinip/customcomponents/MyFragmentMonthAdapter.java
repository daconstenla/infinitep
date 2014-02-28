package es.daconstenla.infinip.customcomponents;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentMonthAdapter extends FragmentPagerAdapter {
	// List of fragments which are going to set in the view pager widget
		List<Fragment> fragments;

		/**
		 * Constructor
		 * 
		 * @param fm
		 *            interface for interacting with Fragment objects inside of an
		 *            Activity
		 */
		public MyFragmentMonthAdapter(FragmentManager fm) {
			super(fm);
			this.fragments = new ArrayList<Fragment>();
		}

		public void addFragment(Fragment fragment) {
			this.fragments.add(fragment);
		}

		@Override
		public Fragment getItem(int arg0) {
			if((arg0+1)>this.fragments.size()){
				if(arg0 == this.fragments.size()){
					Fragment f = new MonthFragment();
					this.fragments.add(new)
				}else
					return null;
					
			}else
				return this.fragments.get(arg0);
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}
}
