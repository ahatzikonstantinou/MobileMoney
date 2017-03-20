package ahat.mobilemoney;

import java.util.Arrays;
import java.util.List;

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
    /*
     * Retrieves all banks that are not present in the param list or have a version higher than in the param list.
     */
    public static List<BankDTO> GetBankList( List<BankShort> currentBanks )
    {
        // TODO: replace with that actually gets this information from the platform with a web request
        return Arrays.asList(
                new BankDTO( 1, "Alpha Bank", "00A",
                             Arrays.asList(
                                     new Task(
                                             Task.Code.TestLogin,
                                             Arrays.asList(
                                                     new Step( Step.Code.LoadLoginScreen ),
                                                     new Step( Step.Code.FillCredentialsAndLogin ),
                                                     new Step( Step.Code.Logout )
                                             )
                                     )
                             )
                ),
                new BankDTO( 1, "Piraeus", "00P", null ),
                new BankDTO( 1, "Eurobank", "0EU", null ),
                new BankDTO( 1, "Ethniki", "00E", null )
        );
    }


}
