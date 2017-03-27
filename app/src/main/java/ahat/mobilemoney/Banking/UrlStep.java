package ahat.mobilemoney.Banking;

import android.content.Context;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class UrlStep extends Step
{
    private final IUrlProvider urlProvider;
    private WebView webView;
    private android.content.Context context;

    public UrlStep( Code code, String name, IUrlProvider urlProvider, IResultStepAction onSuccess, IResultStepAction onFail, Context context )
    {
        super( code, name, onSuccess, onFail );
        this.urlProvider = urlProvider;
        this.context = context;
//        this.regex = regex;
        webView = null;
    }

    @Override
    public void run( BankTaskRunner runner )
    {
        if( null == webView )
        {
            setupWebView();
        }

        //load url
        webView.setWebViewClient(
            new WebViewClient()
            {
                @Override
                public void onPageFinished( WebView view, String url )
                {
                    super.onPageFinished( view, url );
                    onFinish( runner );
                }

            } );
        webView.loadUrl( urlProvider.getUrl() );
    }

    public void onFinish( BankTaskRunner runner )
    {
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
        runner.run();
    }

    private void setupWebView()
    {
        WebView wv = new WebView( context );
        wv.getSettings().setUseWideViewPort( true );
        wv.getSettings().setLoadWithOverviewMode( true );
        wv.setInitialScale( 70 );
        wv.getSettings().setJavaScriptEnabled( true );
        wv.getSettings().setDomStorageEnabled( true );
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
