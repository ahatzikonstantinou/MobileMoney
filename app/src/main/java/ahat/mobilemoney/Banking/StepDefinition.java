package ahat.mobilemoney.Banking;

import java.io.Serializable;

/**
 * Created by antonis on 27/3/2017.
 */
public class StepDefinition implements Serializable
{
    public String code;
    public String name;
    public String url;
    public String regex;
    public final UrlProvider urlProvider;
    public final ResultAction onSuccess;
    public final ResultAction onFail;

    public enum UrlProvider
    {
        Static, Credentials
    }

    public enum ResultAction { Continue, TerminateTask, GotoLast }
    public StepDefinition( String code, String name, String url, String regex, UrlProvider urlProvider, ResultAction onSuccess, ResultAction onFail )
    {
        this.code = code;
        this.name = name;
        this.url = url;
        this.regex = regex;
        this.urlProvider = urlProvider;
        this.onSuccess = onSuccess;
        this.onFail = onFail;
    }
}
