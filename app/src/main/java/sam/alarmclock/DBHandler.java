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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
