package ahat.mobilemoney;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import ahat.mobilemoney.Banking.BankDTO;
import ahat.mobilemoney.Banking.BankService;
import ahat.mobilemoney.Banking.TaskDefinition;

/**
 * Created by antonis on 11/3/2017.
 */

public class Utils
{
    public static boolean UserIsRegistered()
    {
        // TODO: add real check
        return false;
    }

    public static List<BanksActivityListItem> GetBanksActivityListItems( Context context )
    {
        List<BankDTO> banks = BankService.GetAllActiveBanksForListEdit( context );
        List<BanksActivityListItem> items = new ArrayList<>( banks.size() );
        for( BankDTO b : banks )
        {
            items.add( new BanksActivityListItem( b, BankService.GetBankLogo( context, b ) ) );
        }
        return items;
    }

    public static List<BanksActivityListItem> GetAddBanksActivityListItems( Context context )
    {
        List<BankDTO> banks = BankService.GetAllInactiveBanksForListAdd( context );
        List<BanksActivityListItem> items = new ArrayList<>( banks.size() );
        for( BankDTO b : banks )
        {
            items.add( new BanksActivityListItem( b, BankService.GetBankLogo( context, b ) ) );
        }
        return items;
    }

    public static void AskDeleteBank( Context context, BankDTO bankDTO, Class<?> activityClassToGo )
    {
        new AlertDialog.
            Builder( context ).
               setTitle( "Confirm Bank Delete" ).
               setMessage( "Your bank credentials and accounts will be deleted." ).
               setIcon( R.drawable.ic_warning_black_24dp ).
               setNegativeButton( android.R.string.cancel, null ).
               setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener()
               {
                   @Override
                   public void onClick( DialogInterface dialog, int which )
                   {
                       BankService.UserDeleteBank( context, bankDTO );
                       Intent intent = new Intent( context, activityClassToGo );
                       context.startActivity( intent );
                   }
               } ).
               create().
               show();
    }

    /**
     * Runs a task as an AsyncTask. The asynctask starts a dialog, the executes the task's steps, updates the dialog at each step
     * or the dialog may cancel the asynctask onClickCancel
     * @param parent
     * @param task
     */
    public static void RunTask( Activity parentActivity, TaskDefinition task, String title )
    {
        BankTaskAsync bta = new BankTaskAsync( parentActivity, task, title );
        bta.execute();
    }

}
