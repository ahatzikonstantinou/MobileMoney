package ahat.mobilemoney.Banking;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;

import java.util.List;

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
    public static BankDTO UpdateFromPlatform( Context context, BankShort localBank, BankDTO remoteBank )
    {
        return new BankDTO( remoteBank.getVersion(), remoteBank.getName(), remoteBank.getCode(), GetLocalBankDTO( context, localBank ).isActive(), null );
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

    public static Bank GetBank( Context context, BankDTO bankDTO )
    {
        StorageProxy storageProxy = new StorageProxy( context );
        return storageProxy.GetBank( bankDTO.getCode() );
    }
}
