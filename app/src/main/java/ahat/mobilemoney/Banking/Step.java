package ahat.mobilemoney.Banking;

import java.io.Serializable;

/**
 * Created by antonis on 19/3/2017.
 * A Step is a batch of code to be executed when the webview finishes loading e.g. LoadLoginScreen, FillCredentialsAndLogin, etc.
 */

public abstract class Step implements Serializable
{
    public Step( String code, String name, IResultStepAction onSuccess, IResultStepAction onFail )
    {
        this.code = code;
        this.name = name;
        this.onSuccess = onSuccess;
        this.onFail = onFail;
    }

    // TODO: remove Code, does not seem to be necesasry
    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
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

    public enum Code
    {
        LoadLoginScreen, FillCredentialsAndLogin, Logout, LoadAccountsScreen
    }

    public Step()
    {
    }

    protected String code;

    protected String name;
    protected IResultStepAction onSuccess;
    protected IResultStepAction onFail;

    public abstract void run( BankTaskRunner runner );

    public enum Result{ Continue, EndTask, GoToLast }

}

