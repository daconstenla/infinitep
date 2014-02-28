package com.example.datatest.database;

import android.util.Log;

import com.example.datatest.util.Utils;

public class MovementSummary {

	public static boolean DEBUG = false;
	public long id;
	public String date;
	public String timeStart;
	public String timeEnd;
	public long dateTimeStart;
	public long dateTimeEnd;
	public long duration;
	public int type;

	public MovementSummary(){}
	public MovementSummary(long identifier){
		id = identifier;
	}
	public MovementSummary(long identifier,String dateMovement, String timeStartMovement, String timeEndMovement,  int typeMovement, long dur){
		_init_(identifier,dateMovement, timeStartMovement, timeEndMovement,  typeMovement,dur);
	}
	public MovementSummary(long identifier,String dateMovement, String timeStartMovement, String timeEndMovement,  int typeMovement){
		_init_(identifier,dateMovement, timeStartMovement, timeEndMovement,  typeMovement, Long.MIN_VALUE);
	}
	public MovementSummary(String dateMovement, String timeStartMovement, String timeEndMovement, int typeMovement){
		_init_(Integer.MIN_VALUE,dateMovement, timeStartMovement, timeEndMovement,  typeMovement, Long.MIN_VALUE);
	}
	private void _init_(long identifier,String dateMovement, String timeStartMovement, String timeEndMovement,  int typeMovement, long dur){
		if(DEBUG)Log.d(this.getClass().getName(),
												"id "	+	String.valueOf(identifier) +
												"date"	+ 	dateMovement +
												"ini"	+ 	timeStartMovement +
												"end"	+ 	timeEndMovement +
												"typ"	+ 	String.valueOf(typeMovement) +
												"dur"	+ 	String.valueOf(dur)
												);
		
		if(identifier==Integer.MIN_VALUE) id = -1;
		else id = identifier;
		
		dateTimeStart = Utils.createDateInMilisFromStrings(dateMovement,timeStartMovement);
		dateTimeEnd = Utils.createDateInMilisFromStrings(dateMovement,timeEndMovement);
		date = dateMovement;
		timeStart = timeStartMovement;
		timeEnd = timeEndMovement;
		
		if(dur==Long.MIN_VALUE) duration = dateTimeEnd - dateTimeStart; 
		else duration = dur;
		
		type = typeMovement;
	}
	
	public String toString(){
		String toret = "";
		
		toret = "id "	+	String.valueOf(id) +
				"date"	+ 	date +
				"ini"	+ 	String.valueOf(dateTimeStart) +
				"end"	+ 	String.valueOf(dateTimeEnd) +
				"typ"	+ 	String.valueOf(type) +
				"dur"	+ 	String.valueOf(duration);
		
		return toret;
	}
}
