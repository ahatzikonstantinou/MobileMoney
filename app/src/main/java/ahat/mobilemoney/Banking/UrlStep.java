package ahat.mobilemoney.Banking;

import android.webkit.WebView;

public class UrlStep extends Step
{
    private WebView webView;

    public UrlStep( Code code, String name, IResultStepAction onSuccess, IResultStepAction onFail )
    {
        super( code, name, onSuccess, onFail );
//        this.regex = regex;
        webView = null;
    }

    @Override
    public boolean run( BankTaskRunner runner )
    {
        if( null == webView )
        {
            setupWebView();
        }

        //load url

        //check success
        boolean success = false;
        // TODO

        Step nextStep;
        if( success )
        {
            if( null != onSuccess )
            {
                onSuccess.execute( this, runner );
            }
        }
        else
        {
            if( null != onFail )
            {
                onFail.execute( this, runner );
            }
        }

        nextStep = runner.getCurrentStep();
        if( null != nextStep && nextStep instanceof UrlStep )
        {
            ( ( UrlStep ) nextStep ).setWebView( webView );
        }

        return success;
    }

    private void setupWebView()
    {
        //TODO
    }

    public WebView getWebView()
    {
        return webView;
    }

    public void setWebView( WebView webView )
    {
        this.webView = webView;
    }

    public enum Type { UrlLoad, ExtractAccounts, WaitForSMS }

    private String regex;

    public String getRegex()
    {
        return regex;
    }

    public void setRegex( String regex )
    {
        this.regex = regex;
    }

}
