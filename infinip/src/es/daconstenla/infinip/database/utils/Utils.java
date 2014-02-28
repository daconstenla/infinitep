package es.daconstenla.infinip.database.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.util.Log;
import android.widget.TextView;

public class Utils {
	public static boolean DEBUG = false;
	/**
	 * 
	 * @param date String with date in this format yyyy-MM-dd
	 * @param time String whit time in this format HH:mm:ss
	 * @return long with time in milliseconds since Jan. 1, 1970, midnight GMT.
	 * If conversion fails, returns -1
	 */
	public static long createDateInMilisFromStrings(String date, String time){

		//String dtStart = "2010-10-15T09:27:37";  
		SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss",Locale.getDefault());

		String tDateTime = date+"T"+time;
		if(DEBUG)Log.d("Utils S > L", tDateTime);
		Date date2 = null;
		
		if(tDateTime != null){
			try {  
				date2 = format.parse(tDateTime);
				if(DEBUG)Log.d("Utils S > L", String.valueOf(date2));
				if(DEBUG)Log.d("Utils S > L", String.valueOf(date2.getTime()));
			}
			catch (java.text.ParseException e) {
				if(DEBUG)Log.d("Utils S > L","Cannot convert "+tDateTime+" trying alternative format, dd-MM-yyyy'T'HH:mm:ss");
				
				SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm",Locale.getDefault());
				
				try{
					date2 = format2.parse(tDateTime);
					if(DEBUG)Log.d("Utils S > L", String.valueOf(date2));
					if(DEBUG)Log.d("Utils S > L", String.valueOf(date2.getTime()));
				}catch (java.text.ParseException i) {
					if(DEBUG)Log.d("Utils S > L","Failed conversion of "+tDateTime);
					else i.printStackTrace();
					date2 = null;
				}
				
			}
		}

		if(date != null)
			return date2.getTime();
		else
			return -1;
	}
	/***
	 * Converts a long timestamp in a two parts string element in 0 position of array is date and 1 is time
	 * @param date_time long with the timestamp 
	 * @return 2 elements array with date and time
	 */
	public static String[] createDateInStringFromLong(long date_time){
		String datetime[] = new String[2];
		Calendar cal = new GregorianCalendar();
		if(DEBUG)Log.d("Utils L > S", String.valueOf(date_time));
		cal.setTimeInMillis(date_time);
		
		int month = 0,tmonth = cal.get(Calendar.MONTH);
		
		switch(tmonth){
		case Calendar.JANUARY:month = 1;break;
		case Calendar.FEBRUARY:month = 2;break;
		case Calendar.MARCH:month = 3;break;
		case Calendar.APRIL:month = 4;break;
		case Calendar.MAY:month = 5;break;
		case Calendar.JUNE:month = 6;break;
		case Calendar.JULY:month = 7;break;
		case Calendar.AUGUST:month = 8;break;
		case Calendar.SEPTEMBER:month = 9;break;
		case Calendar.OCTOBER:month = 10;break;
		case Calendar.NOVEMBER:month = 11;break;
		case Calendar.DECEMBER:month = 12;break;
		}
		
		String tString = cal.get(Calendar.YEAR)+"-"+String.valueOf(month)+"-"+cal.get(Calendar.DAY_OF_MONTH);
		datetime[0] = tString;
		tString = cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
		datetime[1] = tString;
		if(DEBUG)Log.d("Util L > S",tString);
		
		return datetime;
	}
	/**
	 * Change the size of a textview text to fit it in the height available
	 * @param textToAdjust textview which will be resized
	 * @param maxFontSize default size to start the resizing
	 */
	public static void resizeTextToFitInLayout(TextView textToAdjust,int maxFontSize) {
		int nH = textToAdjust.getHeight();
		int nTextSize = maxFontSize;
		
		while(textToAdjust.getLineHeight()>nH){
			nTextSize--;
			textToAdjust.setTextSize(nTextSize);
		}
		
	}
	public static int numberOfDaysBeforeInWeek(int firstDayOfWeek, int dayOfWeek){
		int diff = dayOfWeek-firstDayOfWeek;
		if(diff<0) diff+=7;
		
		return diff;
	}
	public static int numberOfDaysAfterInWeek(int firstDayOfWeek, int dayOfWeek){
		int diff = dayOfWeek-firstDayOfWeek;
		if(diff>=0) diff=7-diff;
		else diff=Math.abs(diff);
			
		return --diff;
	}
}
