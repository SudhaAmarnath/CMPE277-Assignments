package sjsu.cmpe277.sudha.smartscan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SmartScan.db";
    public static final String TABLE_NAME = "entry";
    public static final String ID = "_ID";
    public static final String COLUMN_NAME = "ImagePath";
    public static final String COLUMN2_NAME = "FilePath";
    public static final String COLUMN3_NAME = "FileName";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Query = "CREATE TABLE " + TABLE_NAME + "(" +
                ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN2_NAME + " TEXT," +
                COLUMN3_NAME + " TEXT );";
        db.execSQL(Query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addHandler(Controller e) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues x = new ContentValues();
        x.put(ID,1);
        x.put(COLUMN_NAME, "sudha");
        x.put(COLUMN2_NAME, e.getFilePath());
        x.put(COLUMN3_NAME, e.getFileName());
        db.insert(TABLE_NAME, null, x);
    }

    public String loadHandler() {
        String result = "";
        String query = "Select * FROM" + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            String result_2 = cursor.getString(2);
            String result_3 = cursor.getString(3);
            result += String.valueOf(result_0) + " " + result_1 + " " + result_2 + " " + result_3 + System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public Controller findHandler(int id) {
        String query = "Select * FROM " + TABLE_NAME + "WHERE _ID = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Controller e = new Controller();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            e.setId(Integer.parseInt(cursor.getString(0)));
            e.setImagePath(cursor.getString(1));
            e.setFilePath(cursor.getString(2));
            e.setFileName(cursor.getString(3));
            cursor.close();
        } else {
            e = null;
        }
        db.close();
        return e;
    }

    public boolean deleteHandler(int ID) {
        boolean result = false;
        String query = "Select*FROM" + TABLE_NAME + "WHERE _ID = '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Controller e = new Controller();
        if (cursor.moveToFirst()) {
            e.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, "_ID=?",
                    new String[] {
                String.valueOf(Controller.getId())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateHandler(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(ID, id);
        args.put(COLUMN_NAME, name);
        return db.update(TABLE_NAME, args, ID + "=" + id, null) > 0;
    }

}