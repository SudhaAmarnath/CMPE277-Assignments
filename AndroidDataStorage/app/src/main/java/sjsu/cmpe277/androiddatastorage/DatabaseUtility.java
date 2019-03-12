package sjsu.cmpe277.androiddatastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseUtility {


    public static final String MESSAGE="Message";
    public static final String TABLE_NAME="Msg_Table";
    public static final String DATABASE_NAME="SQLiteAssignment.db";
    public static final int DATABASE_VERSION=4;
    public static final String TABLE_CREATE="create table Msg_Table (Message text not null);";

    DataBaseHelper dataBaseHelper;
    Context context;
    SQLiteDatabase sqLiteDatabase;

    public DatabaseUtility(Context context) {
        this.context =context;
        dataBaseHelper=new DataBaseHelper(context);
    }

    public DatabaseUtility open() {
        sqLiteDatabase =dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    public long insert(String insertMessage)
    {
        ContentValues content=new ContentValues();
        content.put(MESSAGE, insertMessage);
        return sqLiteDatabase.insertOrThrow(TABLE_NAME, null, content);
    }

    public Cursor retrieve()
    {
        return sqLiteDatabase.query(TABLE_NAME, new String[]{MESSAGE}, null, null, null, null, null);
    }


    private static class DataBaseHelper extends SQLiteOpenHelper {

        public DataBaseHelper(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(TABLE_CREATE);
            } catch (SQLException s) {
                s.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Msg_Table");
            onCreate(sqLiteDatabase);
        }

    }

}
