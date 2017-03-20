package ahat.mobilemoney.Banking;

/**
 * Created by antonis on 19/3/2017.
 * A Step is a batch of code to be executed when the webview finishes loading e.g. LoadLoginScreen, FillCredentialsAndLogin, etc.
 */

public class Step
{
    public Step( Code code )
    {
        this.code = code;
    }

    public Code getCode()
    {
        return code;
    }

    public void setCode( Code code )
    {
        this.code = code;
    }

    public enum Code { LoadLoginScreen, FillCredentialsAndLogin, Logout};
    private Code code;
}
