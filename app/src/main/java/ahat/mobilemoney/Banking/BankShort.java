package ahat.mobilemoney.Banking;

/**
 * Created by antonis on 19/3/2017.
 * Short class for quickly getting minimal bank information.
 */

public class BankShort
{
    private long version;
    private String code;

    public long getVersion()
    {
        return version;
    }

    public void setVersion( long version )
    {
        this.version = version;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public BankShort( long version, String code )
    {
        this.version = version;
        this.code = code;
    }
}
