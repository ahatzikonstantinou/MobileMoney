package ahat.mobilemoney.Banking;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;

import java.io.IOException;
import java.util.List;

import ahat.mobilemoney.Platform;
import ahat.mobilemoney.R;
import ahat.mobilemoney.Storage.StorageProxy;

/**
 * Created by antonis on 21/3/2017.
 */

public class BankService
{
    /**
     * Called when a user deletes a bank. IT actually only deletes the user's credentials from the db and marks the bank inactive
     * @param bank: the bank to "delete"
     */
    public static void UserDeleteBank( Context context, BankDTO bank )
    {
        StorageProxy storageProxy = new StorageProxy( context );
        storageProxy.UserDeleteBank( bank );
    }

    /**
     * Called in order to display the list of Banks that the user has registered as active
     * @return
     */
    public static List<BankDTO> GetAllActiveBanksForListEdit( Context context )
    {
        StorageProxy storageProxy = new StorageProxy( context );
        return storageProxy.GetAllActiveBanksNoTasks();
    }

    public static List<BankDTO> GetAllInactiveBanksForListAdd( Context context )
    {
        StorageProxy storageProxy = new StorageProxy( context );
        return storageProxy.GetAllInactiveBanksNoTasks();
    }

    public static String GetTaskName( Context context, Task.Code taskCode )
    {
        String[] taskNames = context.getResources().getStringArray( R.array.task_titles );
        for( String name : taskNames )
        {
            String[] codeName = name.split( "\\|" );
            if( codeName[0].equals( taskCode.name() ) )
            {
                return codeName[1];
            }
        }

        return "Not found";
    }

    public static TaskDefinition GetTaskOfBank( BankDefinition bank, Task.Code taskCode )
    {
        List<TaskDefinition> tasks = bank.getTasks();
        if( null == tasks )
        {
            return null;
        }

        for( TaskDefinition task : bank.getTasks() )
        {
            if( task.getCode().equals( taskCode ) )
            {
                return task;
            }
        }

        return null;
    }

    public static int GetBankLogo( Context context, BankDTO bankDTO )
    {
        // TODO: change this. Currently it corresponds to an array in strings.xml
        TypedArray logos = context.getResources().obtainTypedArray( R.array.bank_logos );

        switch( bankDTO.getCode() )
        {
            case "00P": return logos.getResourceId( 0, -1);
            case "00A": return logos.getResourceId( 1, -1);
            case "0EU": return logos.getResourceId( 2, -1);
            case "00E": return logos.getResourceId( 3, -1);
        }
        return 0;
    }

    public static void UserAddBank( Context context, BankDTO bankDTO )
    {
        StorageProxy storageProxy = new StorageProxy( context );
        storageProxy.UserAddBank( bankDTO );
    }

    public static BankDTO GetLocalBankDTO( Context context, BankShort bankShort )
    {
        StorageProxy storageProxy = new StorageProxy( context );
        return storageProxy.GetBankDTONoTasks( bankShort.getCode() );
    }

    /**
     * Return a new BankDTO with only the core properties updated i.e. property active will not be affected
     * @param localBank
     * @param remoteBank
     * @return
     */
    public static BankDefinition UpdateFromPlatform( Context context, BankShort localBank, BankDefinition remoteBank )
    {
        return new BankDefinition( remoteBank.getVersion(), remoteBank.getName(), remoteBank.getCode(), GetLocalBankDTO( context, localBank ).isActive(), remoteBank.getTasks() );
    }

    public static void UserClearCredentials( Context context, BankDTO bankDTO )
    {
        StorageProxy storageProxy = new StorageProxy( context );
        storageProxy.ClearCredentials( bankDTO );
    }

    public static void UserStoreCredentials( Context context, BankDTO bankDTO, String username, String password )
    {
        // TODO: encrypt username, password
        StorageProxy storageProxy = new StorageProxy( context );
        storageProxy.StoreCredentials( bankDTO, username, password );
    }

    public static BankDefinition GetBank( Context context, BankDTO bankDTO ) throws IOException, ClassNotFoundException
    {
        StorageProxy storageProxy = new StorageProxy( context );
        return storageProxy.GetBank( bankDTO.getCode() );
    }

    public static BankDefinition RefreshBank( Activity parentActivity, BankDTO bankDTO ) throws IOException
    {
        StorageProxy storageProxy = new StorageProxy( parentActivity );
        storageProxy.DeleteBank( bankDTO.getCode() );
        storageProxy.SaveNew( Platform.GetBankByCode( bankDTO.getCode() ) );

        return storageProxy.GetBankDTO( bankDTO.getCode()  );
    }

    public static UserCredentials GetUserCredentials( Context context, String bankCode )
    {
        StorageProxy storageProxy = new StorageProxy( context );
        return storageProxy.GetUserCredentials( bankCode );
    }
}
