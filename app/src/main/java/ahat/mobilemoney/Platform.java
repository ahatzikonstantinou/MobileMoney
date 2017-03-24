package ahat.mobilemoney;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ahat.mobilemoney.Banking.Bank;
import ahat.mobilemoney.Banking.BankDTO;
import ahat.mobilemoney.Banking.BankShort;
import ahat.mobilemoney.Banking.Step;
import ahat.mobilemoney.Banking.Task;

/**
 * Created by antonis on 19/3/2017.
 * This class handles all communication with the platform such as chekc if a given banks actions are still valid,
 * retrieve a user's accounts based on the user's mobile phone, etc.
 */

public class Platform
{
    public static List<BankDTO> GetAllBanks()
    {
        return Arrays.asList(
                new BankDTO( 3, "Alpha Bank", "00A", false,
                             Arrays.asList(
                                     new Task(
                                             Task.Code.TestLogin,
                                             Arrays.asList(
                                                     new Step( Step.Code.LoadLoginScreen, "Load Login Screen" ),
                                                     new Step( Step.Code.FillCredentialsAndLogin, "Fill Credentials And Login" ),
                                                     new Step( Step.Code.Logout, "Logout" )
                                             )
                                     ),
                                     new Task(
                                             Task.Code.ImportAccounts,
                                             Arrays.asList(
                                                     new Step( Step.Code.LoadLoginScreen, "Load Login Screen" ),
                                                     new Step( Step.Code.FillCredentialsAndLogin, "Fill Credentials And Login" ),
                                                     new Step( Step.Code.LoadAccountsScreen, "Load Accounts Screen" ),
                                                     new Step( Step.Code.Logout, "Logout" )
                                             )
                                     )
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
