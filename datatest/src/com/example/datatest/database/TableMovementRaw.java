package com.example.datatest.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class TableMovementRaw {

	// Columns IDs
	public static final int KEY = 0;
	public static final int DATE_TIME = 1;
	public static final int ACEL_X = 2;
	public static final int ACEL_Y = 3;
	public static final int ACEL_Z = 4;

	public static final boolean IS_HISTORY_TABLE = true;
	public static final boolean IS_NORMAL_TABLE = false;
	// id?, fecha, hora, acel_x, acel_y, acel_z

	// Database table
	public static final String TABLE_NORMAL = "movement_raw";
	public static final String TABLE_HISTORY = "movement_history";
	public static final String[] COLUMNS = {"_id","date_time","acel_x","acel_y","acel_z"};
	public static final String[] TYPES = {"INTEGER","INTEGER","INTEGER","INTEGER","INTEGER"};

	// Database creation SQL statement
	private static final String DATABASE_CREATE_BASE = 
			"(" 
					+ COLUMNS[KEY] +" "+ TYPES[KEY] + " primary key autoincrement, " 
					+ COLUMNS[DATE_TIME] +" "+ TYPES[DATE_TIME] + " not null, " 
					+ COLUMNS[ACEL_X] +" "+ TYPES[ACEL_X] + " not null, " 
					+ COLUMNS[ACEL_Y] +" "+ TYPES[ACEL_Y] + " not null, "
					+ COLUMNS[ACEL_Z] +" "+ TYPES[ACEL_Z] + " not null "
					+ ");";

	private static final String DATABASE_CREATE_NORMAL = "create table " 
			+ TABLE_NORMAL + DATABASE_CREATE_BASE;
	private static final String DATABASE_CREATE_HISTORY = "create table " 
			+ TABLE_HISTORY + DATABASE_CREATE_BASE;


	public static void onCreate(SQLiteDatabase database,boolean history ) {
		if(history)
			database.execSQL(DATABASE_CREATE_HISTORY);
		else
			database.execSQL(DATABASE_CREATE_NORMAL);
	}
	/**
	 * @param db database where the upgrade will be done
	 * @param oldVersion old numeric version of the database
	 * @param newVersion 
	 * @param history
	 */
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion, boolean history) 
	{

		Log.w(TableMovementRaw.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		if(history)
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
		else
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_NORMAL);

		onCreate(database,history);
	}

	/**
	 * Inserts a new movement in dbWritable database
	 * @param dbWritable database where the movement will be inserted
	 * @param movement movement to insert into database
	 */
	public static long insertMovement( SQLiteDatabase dbWritable, MovementRaw movement , boolean isHistory ) {

		ContentValues values = new ContentValues();

		values.put(COLUMNS[DATE_TIME], movement.dateTime);
		values.put(COLUMNS[ACEL_X], movement.acel_x);
		values.put(COLUMNS[ACEL_Y], movement.acel_y);
		values.put(COLUMNS[ACEL_Z], movement.acel_z);

		if(isHistory == IS_HISTORY_TABLE)
			return dbWritable.insert(TABLE_HISTORY, null, values);
		else
			return dbWritable.insert(TABLE_NORMAL, null, values);
	}

	public static MovementRaw getMovement( SQLiteDatabase dbReadable, MovementRaw movement ) {

		Cursor cursor = dbReadable.query(TABLE_NORMAL, COLUMNS, COLUMNS[0] + "=?",
				new String[] { String.valueOf(movement.id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		return new MovementRaw(movement.id,cursor.getLong(DATE_TIME),cursor.getInt(ACEL_X),cursor.getInt(ACEL_Y),cursor.getInt(ACEL_Z));
	}

	public static List<MovementRaw> getAllRawMovements( SQLiteDatabase dbReadable ) {

		List<MovementRaw> movements = new ArrayList<MovementRaw>();

		Cursor cursor = dbReadable.query(TABLE_NORMAL,
				COLUMNS, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			MovementRaw movement = new MovementRaw(cursor.getInt(KEY), cursor.getLong(DATE_TIME), cursor.getInt(ACEL_X), cursor.getInt(ACEL_Y), cursor.getInt(ACEL_Z));
			movements.add(movement);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return movements;
	}
	
	public static void moveMovementsToHistory(SQLiteDatabase dbWritable,List<MovementRaw> movements){
		for(MovementRaw mov:movements){
			if(!deleteMovementRaw(dbWritable, mov))
				Log.d("moveMovementsToHistory", "not deleted, CARE!");
			else
				insertMovement(dbWritable, mov, IS_HISTORY_TABLE);
		}
	}
	
	private static boolean deleteMovementRaw(SQLiteDatabase dbWritable, MovementRaw mov){
		boolean deleted = false;
				
		if(dbWritable.delete(TABLE_NORMAL,COLUMNS[KEY] + " = " + mov.id, null)>0)
			deleted = true;
		
		return deleted;
	}
	

}
