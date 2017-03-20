package ahat.mobilemoney.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ahat.mobilemoney.Banking.BankDTO;
import ahat.mobilemoney.Banking.BankShort;

/**
 * Created by antonis on 19/3/2017.
 */

public class StorageProxy
{
    private StorageDBHelper dbHelper;
    private Cursor          cursor;

    public StorageProxy( Context context )
    {
        dbHelper = new StorageDBHelper( context );
    }

    public List<BankShort> GetAllBanksShort()
    {
        List<BankShort> banks = new ArrayList<BankShort>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery( StorageDBHelper.SQL_SELECT_ALL, null );
        while( cursor.moveToNext() )
        {
            banks.add(
                    new BankShort(
                            cursor.getLong( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_VERSION ) ),
                            cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_CODE ) )
                            )
            );
        }
        return banks;
    }

    /*
     * Without Tasks
     */
    public List<BankDTO> GetAllBanksNoTasks()
    {
        List<BankDTO> banks = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery( StorageDBHelper.SQL_SELECT_ALL, null );
        while( cursor.moveToNext() )
        {
            banks.add(
                    new BankDTO(
                            cursor.getLong( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_VERSION ) ),
                            cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_NAME ) ),
                            cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_CODE ) ),
                            null
                            )
            );
        }
        return banks;
    }

    public void SaveNew( BankDTO newBank )
    {
        //from https://developer.android.com/training/basics/data-storage/databases.html
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( StorageContract.DBBank.COLUMN_NAME_CODE, newBank.getCode() );
        values.put( StorageContract.DBBank.COLUMN_NAME_NAME, newBank.getName() );
        values.put( StorageContract.DBBank.COLUMN_NAME_VERSION, newBank.getVersion() );
        long newRowId = db.insert( StorageContract.DBBank.TABLE_NAME, null, values );
    }

    public void Update( BankDTO newBank )
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = StorageContract.DBBank.COLUMN_NAME_CODE + " = ?";
        String[] selectionArgs = { newBank.getCode() };

        ContentValues values = new ContentValues();
        values.put( StorageContract.DBBank.COLUMN_NAME_NAME, newBank.getName() );
        values.put( StorageContract.DBBank.COLUMN_NAME_VERSION, newBank.getVersion() );

        db.update( StorageContract.DBBank.TABLE_NAME, values, selection, selectionArgs );
    }
}
