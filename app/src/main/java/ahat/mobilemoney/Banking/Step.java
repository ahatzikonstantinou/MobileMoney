package ahat.mobilemoney.Banking;

import java.io.Serializable;

/**
 * Created by antonis on 19/3/2017.
 * A Step is a batch of code to be executed when the webview finishes loading e.g. LoadLoginScreen, FillCredentialsAndLogin, etc.
 */

public class Step implements Serializable
{
    public Step( Code code, String name )
    {
        this.code = code;
        this.name = name;
    }

    public Code getCode()
    {
        return code;
    }

    public void setCode( Code code )
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public enum Code { LoadLoginScreen, FillCredentialsAndLogin, Logout, LoadAccountsScreen};
    private Code code;

    private String name;
}
