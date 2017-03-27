package ahat.mobilemoney.Banking;

/**
 * Created by ahat on 3/27/17.
 */

public class CredentialsProvider implements ICredentialsProvider
{
    private String username;
    private String password;
    public CredentialsProvider()
    {
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }
}
