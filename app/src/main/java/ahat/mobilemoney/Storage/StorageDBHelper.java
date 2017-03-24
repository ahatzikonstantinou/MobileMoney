package ahat.mobilemoney.Storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by antonis on 10/3/2017.
 */

public class StorageDBHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MobileMoney.db";

    private static final String SQL_CREATE_TABLE_BANK = "CREATE TABLE " + StorageContract.DBBank.TABLE_NAME + " (" +
                                                        StorageContract.DBBank._ID + " INTEGER PRIMARY KEY," +
                                                        StorageContract.DBBank.COLUMN_NAME_NAME + " TEXT," +
                                                        StorageContract.DBBank.COLUMN_NAME_CODE + " TEXT," +
                                                        StorageContract.DBBank.COLUMN_NAME_VERSION + " INTEGER," +
                                                        StorageContract.DBBank.COLUMN_NAME_ACTIVE + " INTEGER," +
                                                        StorageContract.DBBank.COLUMN_NAME_TASKS + " TEXT," +
                                                        StorageContract.DBBank.COLUMN_NAME_USERNAME + " TEXT," +
                                                        StorageContract.DBBank.COLUMN_NAME_PASSWORD + " TEXT)";

    private static final String SQL_DELETE_TABLE_BANK = "DROP TABLE IF EXISTS " + StorageContract.DBBank.TABLE_NAME;


    public static final String SQL_SELECT_ALL = "SELECT * FROM " + StorageContract.DBBank.TABLE_NAME;

    public static final String SQL_SELECT_ALL_BY_ACTIVE = "SELECT * FROM " + StorageContract.DBBank.TABLE_NAME + " WHERE " + StorageContract.DBBank.COLUMN_NAME_ACTIVE + " = ?";

    public static final String SQL_SELECT_BY_CODE = "SELECT * FROM " + StorageContract.DBBank.TABLE_NAME + " WHERE " + StorageContract.DBBank.COLUMN_NAME_CODE + " = ?";


    public StorageDBHelper( Context context)
    {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate( SQLiteDatabase db )
    {
        db.execSQL( SQL_CREATE_TABLE_BANK );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
    {
        db.execSQL( SQL_DELETE_TABLE_BANK );
        onCreate( db );
    }

}
