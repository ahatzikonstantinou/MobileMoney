package ahat.mobilemoney.Storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import ahat.mobilemoney.Banking.BankDTO;
import ahat.mobilemoney.Banking.BankDefinition;
import ahat.mobilemoney.Banking.BankShort;
import ahat.mobilemoney.Banking.TaskDefinition;
import ahat.mobilemoney.Banking.UserCredentials;

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
        try
        {
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
        finally
        {
            cursor.close();
        }
    }

    /*
     * Without Tasks
     */
    public List<BankDTO> GetAllBanksNoTasks()
    {
        return GetAllBanksNoTasks( StorageDBHelper.SQL_SELECT_ALL, null );
    }

    private List<TaskDefinition> BytesToTasks( byte[] buffer ) throws IOException, ClassNotFoundException
    {
        ObjectInputStream ois = new ObjectInputStream( new ByteArrayInputStream( buffer) );
        try
        {
            return new ArrayList<>( (List<TaskDefinition>) ois.readObject() );
        }
        finally
        {
            ois.close();
        }
    }
    private byte[] TasksToBytes( List<TaskDefinition> tasks ) throws IOException
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream( bos);
        oos.writeObject( tasks );
        oos.close();
        return bos.toByteArray();
    }

    public void SaveNew( BankDefinition newBank ) throws IOException
    {
        byte[] tasks = TasksToBytes( newBank.getTasks() );

        //from https://developer.android.com/training/basics/data-storage/databases.html
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( StorageContract.DBBank.COLUMN_NAME_CODE, newBank.getCode() );
        values.put( StorageContract.DBBank.COLUMN_NAME_NAME, newBank.getName() );
        values.put( StorageContract.DBBank.COLUMN_NAME_VERSION, newBank.getVersion() );
        values.put( StorageContract.DBBank.COLUMN_NAME_ACTIVE, newBank.isActive() ? 1 : 0 );
        values.put( StorageContract.DBBank.COLUMN_NAME_TASKS, tasks );
//        values.put( StorageContract.DBBank.COLUMN_NAME_TASKS, Base64.encodeToString( tasks, Base64.NO_WRAP ) );
        long newRowId = db.insert( StorageContract.DBBank.TABLE_NAME, null, values );
    }

    public void Update( BankDefinition newBank ) throws IOException
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = StorageContract.DBBank.COLUMN_NAME_CODE + " = ?";
        String[] selectionArgs = { newBank.getCode() };

        byte[] tasks = TasksToBytes( newBank.getTasks() );

        ContentValues values = new ContentValues();
        values.put( StorageContract.DBBank.COLUMN_NAME_NAME, newBank.getName() );
        values.put( StorageContract.DBBank.COLUMN_NAME_VERSION, newBank.getVersion() );
        values.put( StorageContract.DBBank.COLUMN_NAME_ACTIVE, newBank.isActive() ? 1 : 0 );
        values.put( StorageContract.DBBank.COLUMN_NAME_TASKS, tasks );
//        values.put( StorageContract.DBBank.COLUMN_NAME_TASKS, Base64.encodeToString( tasks, Base64.NO_WRAP ) );

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
        try
        {
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
        finally
        {
            cursor.close();
        }
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

    public BankDefinition GetBank( String code ) throws IOException, ClassNotFoundException
    {
        BankDefinition bank = new BankDefinition();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try
        {
            cursor = db.rawQuery( StorageDBHelper.SQL_SELECT_BY_CODE, new String[]{ code } );
            if( cursor.moveToNext() )
            {
                bank.setVersion( cursor.getLong( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_VERSION ) ) );
                bank.setName( cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_NAME ) ) );
                bank.setCode( cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_CODE ) ) );
                bank.setTasks( BytesToTasks( cursor.getBlob( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_TASKS ) ) ) );

                return bank;
            }
        }
        finally
        {
            cursor.close();
        }
        return null;
    }

    public void DeleteBank( String code )
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = StorageContract.DBBank.COLUMN_NAME_CODE + " = ?";
        String[] selectionArgs = { code };
        db.delete( StorageContract.DBBank.TABLE_NAME, selection, selectionArgs );
    }

    public BankDefinition GetBankDTO( String code )
    {
        BankDefinition bankDefinition = new BankDefinition();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery( StorageDBHelper.SQL_SELECT_BY_CODE, new String[]{ code } );
            if( cursor.moveToNext() )
            {
                bankDefinition.setVersion( cursor.getLong( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_VERSION ) ) );
                bankDefinition.setName( cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_NAME ) ) );
                bankDefinition.setCode( cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_CODE ) ) );
                bankDefinition.setActive( cursor.getInt( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_ACTIVE ) ) == 1 );
                bankDefinition.setTasks( BytesToTasks( cursor.getBlob( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_TASKS ) ) ) );
            }
        }
        catch( ClassNotFoundException e )
        {
            e.printStackTrace();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        finally
        {
            if( null != cursor )
            {
                cursor.close();
            }
        }
        return bankDefinition;
    }

    public UserCredentials GetUserCredentials( String bankCode )
    {
        UserCredentials uc = new UserCredentials();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try
        {
            cursor = db.rawQuery( StorageDBHelper.SQL_SELECT_BY_CODE, new String[]{ bankCode } );
            if( cursor.moveToNext() )
            {
                uc.setPassword( cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_PASSWORD ) ) );
                uc.setUsername( cursor.getString( cursor.getColumnIndex( StorageContract.DBBank.COLUMN_NAME_USERNAME ) ) );

                return uc;
            }
        }
        finally
        {
            cursor.close();
        }
        return null;
    }
}
