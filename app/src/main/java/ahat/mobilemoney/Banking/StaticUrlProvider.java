package ahat.mobilemoney.Banking;

/**
 * Created by ahat on 3/27/17.
 */

public class StaticUrlProvider implements IUrlProvider
{
    private String url;

    public StaticUrlProvider( String url )
    {
        this.url = url;
    }

    @Override
    public String getUrl()
    {
        return null;
    }
}
