package es.daconstenla.infinip.database.test;

import java.util.List;
import java.util.Random;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import es.daconstenla.infinip.database.InfinitepHelper;
import es.daconstenla.infinip.database.MovementRaw;
import es.daconstenla.infinip.database.MovementSummary;
import es.daconstenla.infinip.database.TableMovementRaw;
import es.daconstenla.infinip.database.TableMovementSummary;
import es.daconstenla.infinip.database.utils.Utils;


public class DatabaseTester {
	private static SQLiteDatabase wDatabase,rDatabase;
	private static InfinitepHelper helper;
	
	private static long tempIDU,tempIDG;
	
	public static void runTests(InfinitepHelper help){
		
		helper = help;
		wDatabase = helper.getWritableDatabase();
		rDatabase = helper.getReadableDatabase();
		
		//Movement summary
		Log.d("DatabaseTester summary","inserts");
		insertMovementsSummary();
		Log.d("DatabaseTester summary","update 1 movemnt");
		updateMovementsSummary();
		Log.d("DatabaseTester summary","get multiple movement between dates");
		getMovementsBetweenDates();
		
		//Movement raw
		Log.d("DatabaseTester Raw","insert movements");
		insertMovementsRaw();
		Log.d("DatabaseTester Raw","get all raw movements");
		getAllRawMovements();
		Log.d("DatabaseTester Raw","convert from movements to history");
		moveMovementsToHistory();
		
		//Utils
		//Log.d("DatabaseTester utils","test conversion from date to string and long");
		//utilTest();
		
	}
	
	public static void insertMovementsSummary(){
		long idInserted ;
		
		
		MovementSummary mov = new MovementSummary("01-01-2014","0:00:00","0:50:00",TableMovementSummary.TYPE_SLEEP_LIGHT);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);		
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","0:50:00","1:50:00",TableMovementSummary.TYPE_SLEEP_DEEP);
		idInserted = TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","1:50:00","2:20:00",TableMovementSummary.TYPE_SLEEP_LIGHT);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","2:20:00","3:30:00",TableMovementSummary.TYPE_SLEEP_DEEP);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","3:30:00","4:50:00",TableMovementSummary.TYPE_SLEEP_LIGHT);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","4:50:00","6:30:00",TableMovementSummary.TYPE_SLEEP_DEEP);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","6:30:00","7:30:00",TableMovementSummary.TYPE_NOT_MOVING);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","7:30:00","7:45:00",TableMovementSummary.TYPE_MOVE_WALK);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","7:45:00","9:00:00",TableMovementSummary.TYPE_MOVE_FASTER);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","9:00:00","9:30:00",TableMovementSummary.TYPE_MOVE_WALK);
		tempIDU = TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+tempIDU+" toUpdate");
		mov = new MovementSummary("01-01-2014","9:30:00","12:00:00",TableMovementSummary.TYPE_COMPUTER);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","12:00:00","12:40:00",TableMovementSummary.TYPE_MOVE_WALK);
		tempIDG = TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+tempIDG+" toGet");
		mov = new MovementSummary("01-01-2014","12:40:00","15:00:00",TableMovementSummary.TYPE_COMPUTER);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","15:00:00","15:10:00",TableMovementSummary.TYPE_MOVE_WALK);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","15:10:00","15:30:00",TableMovementSummary.TYPE_NOT_MOVING);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","15:30:00","18:00:00:",TableMovementSummary.TYPE_COMPUTER);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-01-2014","18:00:00","18:15:00",TableMovementSummary.TYPE_MOVE_WALK);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		
		Random r = new Random();
			
		int i1=r.nextInt(7);
		mov = new MovementSummary("01-01-2014","18:15:00","18:45:00",i1);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		i1=r.nextInt(7);
		mov = new MovementSummary("01-01-2014","18:45:00","19:10:00",i1);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		i1=r.nextInt(7);
		mov = new MovementSummary("01-01-2014","19:10:00","19:50:00",i1);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		i1=r.nextInt(7);
		mov = new MovementSummary("01-01-2014","19:50:00","20:30:00",i1);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		i1=r.nextInt(7);
		mov = new MovementSummary("01-01-2014","20:30:00","22:20:00",i1);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		i1=r.nextInt(7);
		mov = new MovementSummary("01-01-2014","22:20:00","23:59:00",TableMovementSummary.TYPE_SLEEP_LIGHT);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		
		mov = new MovementSummary("01-02-2014","11:22:00","22:33:00",i1);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-03-2014","11:22:00","22:33:00",i1);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementSummary("01-04-2014","11:22:00","22:33:00",i1);
		idInserted =TableMovementSummary.insertMovement(wDatabase, mov);
		Log.d("DatabaseTester","insert "+idInserted);
		
	}
	
	public static void updateMovementsSummary(){
		
		MovementSummary mov = new MovementSummary(tempIDU);
		mov = TableMovementSummary.getMovement(rDatabase, mov);
		
		Log.d("DatabaseTester","Updating prev :"+mov);
		mov.type = TableMovementSummary.TYPE_MOVE_FASTER;
		TableMovementSummary.updateMovement(wDatabase, mov);
		
		mov = TableMovementSummary.getMovement(rDatabase, mov);
		Log.d("DatabaseTester","Updating after :"+mov);
		
	}
	
	public static void getMovementsBetweenDates(){
		
		TableMovementSummary.getMovementsBetweenDates(rDatabase, "01-01-2014", "01-01-2014");
		TableMovementSummary.getMovementsBetweenDates(rDatabase, "01-01-2014", "01-02-2014");
		TableMovementSummary.getMovementsBetweenDates(rDatabase, "01-01-2014", "12-12-2014");
		
	}

	public static void insertMovementsRaw(){
		
		long idInserted ;
		MovementRaw mov = new MovementRaw(Utils.createDateInMilisFromStrings("2014-01-01", "00:00:00"), 0, 0, 0);
		idInserted = TableMovementRaw.insertMovement(wDatabase, mov, TableMovementRaw.IS_NORMAL_TABLE);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementRaw(Utils.createDateInMilisFromStrings("2014-02-02", "01:01:00"), 1, 1, 1);
		idInserted = TableMovementRaw.insertMovement(wDatabase, mov, TableMovementRaw.IS_NORMAL_TABLE);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementRaw(Utils.createDateInMilisFromStrings("2014-03-03", "02:02:00"), 2, 2, 2);
		idInserted = TableMovementRaw.insertMovement(wDatabase, mov, TableMovementRaw.IS_NORMAL_TABLE);
		Log.d("DatabaseTester","insert "+idInserted);
		mov = new MovementRaw(Utils.createDateInMilisFromStrings("2014-04-04", "03:03:00"), 3, 3, 3);
		idInserted = TableMovementRaw.insertMovement(wDatabase, mov, TableMovementRaw.IS_NORMAL_TABLE);
		Log.d("DatabaseTester","insert "+idInserted);
		
	}
		
	public static void getAllRawMovements(){
		
		List<MovementRaw> movs = TableMovementRaw.getAllRawMovements(rDatabase);
		for (MovementRaw movementRaw : movs) {
			Log.d("DatabaseTester ",""+movementRaw);
		}
		
	}
	
	public static void moveMovementsToHistory(){
	
		List<MovementRaw> movs = TableMovementRaw.getAllRawMovements(rDatabase);
		TableMovementRaw.moveMovementsToHistory(wDatabase, movs);
		
	}
	
	public static void utilTest(){
		Utils.DEBUG = true;
		//"yyyy-MM-dd'T'HH:mm:ss"
		String date = "2014-02-01",time="22:33:44"; 
		String []datetime;
		Long dateL = Utils.createDateInMilisFromStrings(date, time);
		Log.w("DatabaseTester"," test Util : "+String.valueOf(dateL));
		datetime = Utils.createDateInStringFromLong(dateL);
		Log.w("DatabaseTester"," test Util : "+datetime[0]+""+datetime[1]);
	}
	
}
