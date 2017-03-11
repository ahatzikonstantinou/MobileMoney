package ahat.mobilemoney;

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

    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + StorageContract.Bank.TABLE_NAME + " (" +
                                                   StorageContract.Bank._ID + " INTEGER PRIMARY KEY," +
                                                   StorageContract.Bank.COLUMN_NAME_NAME + " TEXT," +
                                                   StorageContract.Bank.COLUMN_NAME_USERNAME + " TEXT," +
                                                   StorageContract.Bank.COLUMN_NAME_PASSWORD + " TEXT)";

    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + StorageContract.Bank.TABLE_NAME;

    public StorageDBHelper( Context context)
    {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate( SQLiteDatabase db )
    {
        db.execSQL( SQL_CREATE_TABLE );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
    {
        // TODO: have to think what to do on database upgrade
        onCreate( db );
    }
}
