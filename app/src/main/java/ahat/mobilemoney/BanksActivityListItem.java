package ahat.mobilemoney;

/**
 * Created by antonis on 14/3/2017.
 */

public class BanksActivityListItem
{
    private String bankName;
    private int bankLogo;

    public BanksActivityListItem( String bankName, int bankLogo )
    {
        this.bankName = bankName;
        this.bankLogo = bankLogo;
    }

    public String getBankName()
    {
        return bankName;
    }

    public int getBankLogo()
    {
        return bankLogo;
    }
}
