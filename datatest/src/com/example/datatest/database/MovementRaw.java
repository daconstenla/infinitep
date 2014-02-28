package com.example.datatest.database;

import com.example.datatest.util.Utils;

public class MovementRaw {
	public long id;
	public long dateTime;
	public String date;
	public String time;
	public int acel_x;
	public int acel_y;
	public int acel_z;

	public MovementRaw(){}
	/**
	 * 
	 * @param identifier identifier if known from sql row or imported  
	 * @param dat of measurement
	 * @param tim of measurement
	 * @param aceleration_x measure acceleration in X axis
	 * @param aceleration_y measure acceleration in Y axis
	 * @param aceleration_z measure acceleration in Z axis
	 */
	
	
	// With Strings & long
	public MovementRaw(long identifier,long dateTim, int aceleration_x,int aceleration_y,int aceleration_z,String dat, String tim){
		
		_init_(identifier, dateTim, aceleration_x, aceleration_y, aceleration_z, dat, tim);
		
	}
	
	// Only with Long 
	public MovementRaw(long dateTim, int aceleration_x,int aceleration_y,int aceleration_z){
		
		_init_(Long.MIN_VALUE,dateTim,aceleration_x, aceleration_y,aceleration_z,"","");
		
	}
	
	public MovementRaw(long identifier, long dateTime, int aceleration_x,int aceleration_y,int aceleration_z){
		
		_init_(identifier,dateTime,aceleration_x, aceleration_y,aceleration_z,"","");
		
	}
	
	private void _init_(long identifier,long dateTim, int aceleration_x, int aceleration_y,int aceleration_z,String dat,String tim){
		if(id == Long.MIN_VALUE) id = -1;
		else id = identifier;
		
		if(dat.length()==0){
			String tempS[] = Utils.createDateInStringFromLong(dateTim);
			this.date = tempS[0];
			this.time = tempS[1];
		}else{
			this.date = dat;
			this.time = tim;
		}
		
		this.dateTime = dateTim;
		acel_x = aceleration_x;
		acel_y = aceleration_y;
		acel_z = aceleration_z;
	}
	
	public String toString(){
		String toret = 	"id "		+ 	id +
						"dateTime "	+	dateTime +
						"date "		+ 	date +
						"time "		+	time +
						"acelX "	+ 	acel_x +
						"acelY "	+	acel_y +
						"acelZ "	+ 	acel_z	
						;
		return toret;
	}
	
	
}
