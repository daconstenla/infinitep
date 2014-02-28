package com.example.datatest.database;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class TableMovementSummary {
	//Types of movements
	public static final int TYPE_NOT_MOVING = 0;
	public static final int TYPE_COMPUTER = 1;
	public static final int TYPE_MOUSE = 2;
	public static final int TYPE_KEYBOARD = 3;
	public static final int TYPE_MOVE_WALK = 4;
	public static final int TYPE_MOVE_FASTER = 5;
	public static final int TYPE_SLEEP_DEEP = 6;
	public static final int TYPE_SLEEP_LIGHT = 7;
	
	// Columns IDs
	public static final int KEY = 0;
	public static final int DATE = 1;
	public static final int TIME_START = 2;
	public static final int TIME_END = 3;
	public static final int DURATION = 4;
	public static final int TYPE = 5;
	
	// Database table
	private static final String TABLE_NAME = "movement_summary";
	private static final String[] COLUMNS = {"_id","date","time_start","time_end","duration","type",};
	private static final String[] TYPES = {"INTEGER","TEXT","TEXT","TEXT","INTEGER","INTEGER"};
	
	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
			+ TABLE_NAME
			+ "(" 
			+ COLUMNS[KEY] +" "+ TYPES[KEY] + " primary key autoincrement, " 
			+ COLUMNS[DATE] +" "+ TYPES[DATE] + " not null, " 
			+ COLUMNS[TIME_START] +" "+ TYPES[TIME_START] + " not null, " 
			+ COLUMNS[TIME_END] +" "+ TYPES[TIME_END] + " not null, "
			+ COLUMNS[DURATION] +" "+ TYPES[DURATION] + " not null, "
			+ COLUMNS[TYPE] +" "+ TYPES[TYPE] + " not null "
			+ ");";

	public static void onCreate( SQLiteDatabase database ) {
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade( SQLiteDatabase database, int oldVersion, int newVersion ) 
	{
		Log.w(TableMovementSummary.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(database);
	}
	
	/**
	 * Inserts a new movement in dbWritable database
	 * @param dbWritable database where the movement will be inserted
	 * @param movement movement to insert into database
	 */
	public static long insertMovement( SQLiteDatabase dbWritable, MovementSummary movement ) {
		
		ContentValues values = new ContentValues();
		
        values.put(COLUMNS[DATE], movement.date);
        values.put(COLUMNS[TIME_START], movement.timeStart);
        values.put(COLUMNS[TIME_END], movement.timeEnd);
        values.put(COLUMNS[DURATION], movement.duration);
        values.put(COLUMNS[TYPE], movement.type);
 
        return dbWritable.insert(TABLE_NAME, null, values);
	}
	
	/**
	 * Update the movement with id idMovement in dbWritable database
	 * @param dbWritable database where the movement will be inserted
	 * @param movement movement to insert into database
	 */
	public static long updateMovement( SQLiteDatabase dbWritable, MovementSummary movement ) {
		
		ContentValues values = new ContentValues();
		
		values.put(COLUMNS[KEY], movement.id);
		values.put(COLUMNS[DATE], movement.date);
        values.put(COLUMNS[TIME_START], movement.timeStart);
        values.put(COLUMNS[TIME_END], movement.timeEnd);
        values.put(COLUMNS[DURATION], movement.duration);
        values.put(COLUMNS[TYPE], movement.type);
 
        return dbWritable.update(TABLE_NAME, values, COLUMNS[KEY] + " = ?",
                new String[] { String.valueOf( movement.id ) });
	}
	
	public static MovementSummary getMovement( SQLiteDatabase dbReadable, MovementSummary movement ) {
		
		Cursor cursor = dbReadable.query(TABLE_NAME, COLUMNS, COLUMNS[0] + "=?",
                new String[] { String.valueOf(movement.id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        
        MovementSummary mov = new MovementSummary(cursor.getLong(KEY), cursor.getString(DATE), 
        		cursor.getString(TIME_START), cursor.getString(TIME_END), 
        		cursor.getInt(TYPE));
 
        cursor.close();
        
        return mov; 
        
	}
	
	/**
	 * Search movements between inital_date and final_date
	 * @param dbReadable database where the movements will be searched 
	 * @param initial_date date which will be the starting date to search movements
	 * @param final_date date which will be the final date to search movements
	 * @return a list with founded movements in dbReadable
	 */
	public static List<MovementSummary> getMovementsBetweenDates( SQLiteDatabase dbReadable, String initial_date, String final_date ){
		Cursor cursor = dbReadable.query(TABLE_NAME, null, COLUMNS[DATE] + " BETWEEN ? AND ?", new String[] {
                initial_date , final_date  }, null, null, null, null); 
	
		List<MovementSummary> movements = new LinkedList<MovementSummary>();
		
		if(cursor != null){
			
			
			if (cursor.moveToFirst()) {
				do {
	                MovementSummary move = new MovementSummary(cursor.getLong(KEY), cursor.getString(DATE), 
	                		cursor.getString(TIME_START), cursor.getString(TIME_END), 
	                		cursor.getInt(TYPE));
	                movements.add(move);
	            } while (cursor.moveToNext());
			
				cursor.close();
				return movements;
			}
			
		}
		
		return movements;
	}

	/**
	 * Deletes movement from database received as parameter, not implemented
	 * @param db writable database 
	 * @param movement movement id to delete
	 */
	public static void deleteMovement( SQLiteDatabase db, long movement_id ) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
}
