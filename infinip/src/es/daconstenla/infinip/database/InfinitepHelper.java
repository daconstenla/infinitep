package es.daconstenla.infinip.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InfinitepHelper extends SQLiteOpenHelper{

	
	private static final String DB_NAME = "infinitep.db";
	private static final int DB_VERSION = 1;

	public InfinitepHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		TableMovementRaw.onCreate(db,TableMovementRaw.IS_HISTORY_TABLE);
		TableMovementRaw.onCreate(db,TableMovementRaw.IS_NORMAL_TABLE);
		
		TableMovementSummary.onCreate(db);
		
	}

	@Override
	/** 
	 * @param db database where the upgrade will be done
	 * @param oldVersion old numeric version of the database
	 * @param newVersion 
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		TableMovementRaw.onUpgrade(db,oldVersion,newVersion,TableMovementRaw.IS_NORMAL_TABLE);
		TableMovementRaw.onUpgrade(db,oldVersion,newVersion,TableMovementRaw.IS_HISTORY_TABLE);
		TableMovementSummary.onUpgrade(db,oldVersion,newVersion);
		
	}
	
	public InfinitepHelper getDatabaseReadable(){
		return getDatabaseReadable();
	}
	
	public InfinitepHelper getDatabaseWritable(){
		return getDatabaseWritable();
	}

}
