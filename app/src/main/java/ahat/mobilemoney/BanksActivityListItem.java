package ahat.mobilemoney;

import ahat.mobilemoney.Banking.BankDTO;

/**
 * Created by antonis on 14/3/2017.
 */

public class BanksActivityListItem
{
    private BankDTO bankDTO;
    private int bankLogo;

    public BanksActivityListItem( BankDTO bankDTO, int bankLogo )
    {
        this.bankDTO = bankDTO;
        this.bankLogo = bankLogo;
    }

    public String getBankName()
    {
        return bankDTO.getName();
    }

    public int getBankLogo()
    {
        return bankLogo;
    }

    public BankDTO getBankDTO() { return bankDTO; }
}
