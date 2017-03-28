package ahat.mobilemoney.Banking;

/**
 * Created by antonis on 29/3/2017.
 */

public class UserCredentials
{
    private String username;

    public UserCredentials()
    {
    }

    private String password;

    public UserCredentials( String username, String password )
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }
}
