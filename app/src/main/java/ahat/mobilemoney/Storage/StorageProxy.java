package ahat.mobilemoney.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ahat.mobilemoney.Banking.Bank;
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
        return GetAllBanksNoTasks( StorageDBHelper.SQL_SELECT_ALL, null );
    }

    public void SaveNew( BankDTO newBank )
    {
        //from https://developer.android.com/training/basics/data-storage/databases.html
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( StorageContract.DBBank.COLUMN_NAME_CODE, newBank.getCode() );
        values.put( StorageContract.DBBank.COLUMN_NAME_NAME, newBank.getName() );
        values.put( StorageContract.DBBank.COLUMN_NAME_VERSION, newBank.getVersion() );
        values.put( StorageContract.DBBank.COLUMN_NAME_ACTIVE, newBank.isActive() ? 1 : 0 );
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
        values.put( StorageContract.DBBank.COLUMN_NAME_ACTIVE, newBank.isActive() ? 1 : 0 );

        db.update( StorageContract.DBBank.TABLE_NAME, values, selection, selectionArgs );
    }

    public void UserDeleteBank( BankDTO bank )
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = StorageContract.DBBank.COLUMN_NAME_CODE + " = ?";
        String[] selectionArgs = { bank.getCode() };

        ContentValues values = new ContentValues();
        values.putNull( StorageContract.DBBank.COLUMN_NAME_USERNAME );
        values.putNull( StorageContract.DBBank.COLUMN_NAME_PASSWORD );
        values.put( StorageContract.DBBank.COLUMN_NAME_ACTIVE, 0 );

        db.update( StorageContract.DBBank.TABLE_NAME, values, selection, selectionArgs );
    }

    public List<BankDTO> GetAllBanksNoTasks( String query, String[] args )
    {
        List<BankDTO> banks = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery( query, args );
        while( cursor.moveToNext() )
        {
            banks.add(
                    new BankDTO(
                            cursor.getLong( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_VERSION ) ),
                            cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_NAME ) ),
                            cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_CODE ) ),
                            cursor.getInt( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_ACTIVE ) ) == 1,
                            null
                    )
            );
        }
        return banks;
    }

    public List<BankDTO> GetAllActiveBanksNoTasks()
    {
        return GetAllBanksNoTasks( StorageDBHelper.SQL_SELECT_ALL_BY_ACTIVE, new String[] { "1" }  );
    }

    public List<BankDTO> GetAllInactiveBanksNoTasks()
    {
        return GetAllBanksNoTasks( StorageDBHelper.SQL_SELECT_ALL_BY_ACTIVE, new String[] { "0" } );
    }

    public void UserAddBank( BankDTO bankDTO )
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = StorageContract.DBBank.COLUMN_NAME_CODE + " = ?";
        String[] selectionArgs = { bankDTO.getCode() };

        ContentValues values = new ContentValues();
        values.put( StorageContract.DBBank.COLUMN_NAME_ACTIVE, 1 );

        db.update( StorageContract.DBBank.TABLE_NAME, values, selection, selectionArgs );

    }

    public BankDTO GetBankDTONoTasks( String code )
    {
        List<BankDTO> banks = GetAllBanksNoTasks( StorageDBHelper.SQL_SELECT_BY_CODE, new String[]{ code } );
        if( 0 == banks.size() )
        {
            return null;
        }
        return banks.get( 0 );
    }

    public void ClearCredentials( BankDTO bankDTO )
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = StorageContract.DBBank.COLUMN_NAME_CODE + " = ?";
        String[] selectionArgs = { bankDTO.getCode() };

        ContentValues values = new ContentValues();
        values.putNull( StorageContract.DBBank.COLUMN_NAME_USERNAME );
        values.putNull( StorageContract.DBBank.COLUMN_NAME_PASSWORD );

        db.update( StorageContract.DBBank.TABLE_NAME, values, selection, selectionArgs );
    }

    public void StoreCredentials( BankDTO bankDTO, String username, String password )
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = StorageContract.DBBank.COLUMN_NAME_CODE + " = ?";
        String[] selectionArgs = { bankDTO.getCode() };

        ContentValues values = new ContentValues();
        values.put( StorageContract.DBBank.COLUMN_NAME_USERNAME, username );
        values.put( StorageContract.DBBank.COLUMN_NAME_PASSWORD, password );

        db.update( StorageContract.DBBank.TABLE_NAME, values, selection, selectionArgs );
    }

    public Bank GetBank( String code )
    {
        Bank bank = new Bank();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery( StorageDBHelper.SQL_SELECT_BY_CODE, new String[]{ code } );
        if( cursor.moveToNext() )
        {
            bank.setVersion( cursor.getLong( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_VERSION ) ) );
            bank.setName( cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_NAME ) ) );
            bank.setCode( cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_CODE ) ) );
            bank.setPassword( cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_PASSWORD ) ) );
            bank.setUsername( cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_USERNAME ) ) );

            return bank;
        }
        return null;
    }
}
