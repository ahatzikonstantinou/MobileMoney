package ahat.mobilemoney;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ahat.mobilemoney.Banking.Bank;
import ahat.mobilemoney.Banking.BankDTO;
import ahat.mobilemoney.Banking.BankShort;
import ahat.mobilemoney.Banking.CredentialsProvider;
import ahat.mobilemoney.Banking.CredentialsUrlProvider;
import ahat.mobilemoney.Banking.ResultContinue;
import ahat.mobilemoney.Banking.ResultGotoLast;
import ahat.mobilemoney.Banking.ResultTerminateTask;
import ahat.mobilemoney.Banking.StaticUrlProvider;
import ahat.mobilemoney.Banking.Step;
import ahat.mobilemoney.Banking.Task;
import ahat.mobilemoney.Banking.TaskDefinitions;
import ahat.mobilemoney.Banking.UrlStep;

/**
 * Created by antonis on 19/3/2017.
 * This class handles all communication with the platform such as check if a given banks actions are still valid,
 * retrieve a user's accounts based on the user's mobile phone, etc.
 */

public class Platform
{
    public static List<BankDTO> GetAllBanks( Context context, CredentialsProvider credentialsProvider )
    {
        // TODO: remove after testing
        TaskDefinitions td = new TaskDefinitions();

        return Arrays.asList(
            //Test using github
            new BankDTO( 3, "Alpha Bank", "00A", false,
                 Arrays.asList(
                     new Task(
                         Task.Code.TestLogin,
                         Arrays.asList(
                             new UrlStep( Step.Code.LoadLoginScreen, "Load Login Screen", td.Github.login.regex, new StaticUrlProvider( td.Github.login.url), new ResultContinue(), new ResultTerminateTask(), context ),
                             new UrlStep( Step.Code.FillCredentialsAndLogin, "Fill Credentials And Login", td.Github.credentials.regex, new CredentialsUrlProvider( td.Github.credentials.url, credentialsProvider.getUsername(), credentialsProvider.getPassword() ), new ResultContinue(), new ResultTerminateTask(), context ),
                             new UrlStep( Step.Code.Logout, "Logout", td.Github.logout.regex, new StaticUrlProvider( td.Github.logout.url ), new ResultTerminateTask(), new ResultTerminateTask(), context )
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
                 )
            ),
            new BankDTO( 1, "Piraeus", "00P", false, null ),
            new BankDTO( 1, "Eurobank", "0EU", false, null ),
            new BankDTO( 1, "Ethniki", "00E", false, null )
        );
    }

    public static BankDTO GetBankByCode( String code )
    {
        List<BankDTO> banks = GetAllBanks();
        for( BankDTO b : banks )
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
    public static List<BankDTO> GetBankListExcludeExisting( List<BankShort> localBanks )
    {
        // TODO: replace with information that comes from the platform with a web request
        List<BankDTO> remoteBanks = GetAllBanks();
        List<BankDTO> banks = new ArrayList<>();
        for( BankDTO r : remoteBanks )
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
