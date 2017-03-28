package ahat.mobilemoney;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ahat.mobilemoney.Banking.BankDefinition;
import ahat.mobilemoney.Banking.BankShort;
import ahat.mobilemoney.Banking.TaskDefinition;
import ahat.mobilemoney.Banking.TaskDefinitions;

/**
 * Created by antonis on 19/3/2017.
 * This class handles all communication with the platform such as check if a given banks actions are still valid,
 * retrieve a user's accounts based on the user's mobile phone, etc.
 */

public class Platform
{
    public static List<BankDefinition> GetAllBanks()
    {
        // TODO: remove after testing
        TaskDefinitions td = new TaskDefinitions();

        return Arrays.asList(
            //Test using github
            new BankDefinition( 3, "Alpha Bank", "00A", false,
                Arrays.asList(
                     new TaskDefinition(
                         "TestLogin",
                         Arrays.asList( td.Github.login, td.Github.credentials, td.Github.logout )
                     )
                )
//                     new Task(
//                         Task.Code.ImportAccounts,
//                         Arrays.asList(
//                             new UrlStep( Step.Code.LoadLoginScreen, "Load Login Screen", new ResultContinue(), new ResultTerminateTask(), context ),
//                             new UrlStep( Step.Code.FillCredentialsAndLogin, "Fill Credentials And Login", new ResultContinue(), new ResultTerminateTask(), context ),
//                             new UrlStep( Step.Code.LoadAccountsScreen, "Load Accounts Screen", new ResultContinue(), new ResultGotoLast(), context ),
//                             new UrlStep( Step.Code.Logout, "Logout", new ResultTerminateTask(), new ResultTerminateTask(), context )
//                         )
//                     )
//                 )
            ),
            new BankDefinition( 1, "Piraeus", "00P", false, null ),
            new BankDefinition( 1, "Eurobank", "0EU", false, null ),
            new BankDefinition( 1, "Ethniki", "00E", false, null )
        );
    }

    public static BankDefinition GetBankByCode( String code )
    {
        List<BankDefinition> banks = GetAllBanks();
        for( BankDefinition b : banks )
        {
            if( b.getCode().equals( code ) )
            {
                return b;
            }
        }
        return null;
    }
    /*
     * Retrieves all banks that are not present in the param list or have a version higher than in the param list.
     */
    public static List<BankDefinition> GetBankListExcludeExisting( List<BankShort> localBanks )
    {
        // TODO: replace with information that comes from the platform with a web request
        List<BankDefinition> remoteBanks = GetAllBanks();
        List<BankDefinition> banks = new ArrayList<>();
        for( BankDefinition r : remoteBanks )
        {
            boolean add = true;
            for( BankShort l : localBanks )
            {
                if( l.getCode().equals( r.getCode() ) && l.getVersion() >= r.getVersion() )
                {
                    add = false;
                    break;
                }
            }
            if( add )
            {
                banks.add( r );
            }
        }
        return banks;
    }

    /**
     * This function returns false for banks that have changed some of their pages so there is risk of a crash
     * @param code
     * @return
     */
    public static boolean BankIsEnabled( String code )
    {
        // TODO: replace with information that comes from the platform with a web request
        switch( code )
        {
            case "00A": return false;
            case "00P": return true;
            case "0EU": return true;
            case "00E": return true;
        }
        return false;
    }


}
