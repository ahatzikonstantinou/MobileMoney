package ahat.mobilemoney;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ahat.mobilemoney.Banking.BankDTO;
import ahat.mobilemoney.Banking.BankService;

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
}
