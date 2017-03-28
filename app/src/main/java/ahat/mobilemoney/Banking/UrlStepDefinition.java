package ahat.mobilemoney.Banking;

/**
 * Created by antonis on 27/3/2017.
 */
public class UrlStepDefinition
{
    public String code;
    public String name;
    public String url;
    public String regex;
    private final UrlProvider urlProvider;
    private final ResultAction onSuccess;
    private final ResultAction onFail;

    public enum UrlProvider
    {
        Static, Credentials
    }

    public enum ResultAction { Continue, TerminateTask, GotoLast }
    public UrlStepDefinition( String code, String name, String url, String regex, UrlProvider urlProvider, ResultAction onSuccess, ResultAction onFail )
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
