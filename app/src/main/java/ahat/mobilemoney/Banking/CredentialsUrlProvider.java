package ahat.mobilemoney.Banking;

/**
 * Created by ahat on 3/27/17.
 */

public class CredentialsUrlProvider implements IUrlProvider
{
    private final String url;
    private final String username;
    private final String password;
    public static final String UsernamePlaceholder = "#username#";
    public static final String PasswordPlaceholder = "#password#";

    public CredentialsUrlProvider( String url, String username, String password )
    {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUrl()
    {
        return url.replace( UsernamePlaceholder, username ).replace( PasswordPlaceholder, password );
    }
}
