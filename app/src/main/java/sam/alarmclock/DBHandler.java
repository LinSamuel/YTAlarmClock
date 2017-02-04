package sam.alarmclock;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "videooptions.db";
    public static final String TABLE_VIDEOOPTIONS = "videooptions";
    public static final String COLUMN_VIDEOURL = "videourl";
    public static final String COLUMN_VIDEONAME = "videoname";


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_VIDEOOPTIONS + "(" +
                COLUMN_VIDEOURL + " TEXT " +
                COLUMN_VIDEONAME + " TEXT " +
                ");";
        db.execSQL(query);
    }

    //Drop current table if version is upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEOOPTIONS);
        onCreate(db);
    }

    //Add a videoOption to database
    public void addVideoOption(VideoOption videoOption){
        ContentValues values = new ContentValues();
        values.put(COLUMN_VIDEOURL, videoOption.getVideoURL());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_VIDEOOPTIONS, null, values);
        db.close();
    }

    //Delete a videoOption from database
    public void deleteVideoOption(String url){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM( " + TABLE_VIDEOOPTIONS + " WHERE " + COLUMN_VIDEOURL + "=\"" + url + "\";");
    }

    public String databaseToString() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_VIDEOOPTIONS + " WHERE 1";

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("videourl")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("videourl"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }

}
