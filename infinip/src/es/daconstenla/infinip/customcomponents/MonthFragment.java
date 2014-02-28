package es.daconstenla.infinip.customcomponents;

import es.daconstenla.infinip.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MonthFragment extends Fragment{
	/**
	 * Keys for bundle storage
	 */
	private static final String CALENDAR_DAY = "DAY";
	private static final String CALENDAR_MONTH = "MONTH";
	private static final String CALENDAR_YEAR = "YEAR";
	
	/**
	 * Variables for class using
	 */
	private int month;
	private int year;
	private int day;
		
	public static MonthFragment newInstance(int day, int month, int year) {
		// Instantiate a new fragment
		MonthFragment fragment = new MonthFragment ();

		// Save the parameters
		Bundle bundle = new Bundle();
		bundle.putInt(CALENDAR_DAY, day);
		bundle.putInt(CALENDAR_MONTH, month);
		bundle.putInt(CALENDAR_YEAR, year);
		fragment.setArguments(bundle);
		fragment.setRetainInstance(true);

		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Load parameters when the initial creation of the fragment is done
		this.day = (getArguments() != null) ? getArguments().getInt(
				CALENDAR_DAY) : -1;
		this.month = (getArguments() != null) ? getArguments().getInt(
				CALENDAR_MONTH)
				: 0;
		this.year = (getArguments() != null) ? getArguments().getInt(
				CALENDAR_YEAR)
				: 0;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup rootView = (ViewGroup) inflater.inflate(es.daconstenla.infinip.R.layout.fragment_month, container, false);


//		TextView tvIndex = (TextView) rootView.findViewById(es.daconstenla.infinip.R.id.tvIndex);
//		tvIndex.setText(String.valueOf(this.index));
		MonthView monthV = (MonthView) rootView.findViewById(es.daconstenla.infinip.R.id.monthView);
		monthV.setDate(day, month, year);
		monthV.randomFillCalendar();
		
		// Change the background color
		rootView.setBackgroundColor(getResources().getColor(R.color.customGreyLight1));

		return rootView;

	}
	
}
