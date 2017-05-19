package ahat.mobilemoney.Banking;

import android.content.Context;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlStep extends Step
{
    private static final long serialVersionUID = 1l;

    private final IUrlProvider            urlProvider;
    private       WebView                 webView;
    private       HtmlProxy               htmlProxy;
    private       android.content.Context context;
    private       BankTaskRunner          runner;

    public UrlStep( String code, String name, String regex, IUrlProvider urlProvider, IResultStepAction onSuccess, IResultStepAction onFail, Context context )
    {
        super( code, name, onSuccess, onFail );
        this.urlProvider = urlProvider;
        this.context = context;
        this.regex = regex;
        webView = null;
        htmlProxy = null;
        runner = null;
    }

    @Override
    public void run( BankTaskRunner runner )
    {
        this.runner = runner;
        if( null == webView )
        {
//            setupWebView();
            webView = runner.getWebView();
        }

        //prepare the htmlproxy that will call back this step when the url/page has loaded
//        htmlProxy.setStep( this );
        this.runner.getHtmlProxy().setStep( this );
        //load url
        webView.loadUrl( urlProvider.getUrl() );
    }

    public void onHtmlReady( String html ) throws Exception
    {
        //check success
        boolean success = Pattern.compile( regex, Pattern.MULTILINE | Pattern.DOTALL ).matcher( html ).matches();

        Step nextStep;
        if( success )
        {
            runner.stepSucceeded();
            if( null != onSuccess )
            {
                onSuccess.execute( this, runner );
            }
        }
        else
        {
            runner.stepFailed();
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
//        runner.run();
    }

    private void setupWebView()
    {
        if( null == htmlProxy )
        {
            htmlProxy = new HtmlProxy();
        }
//        else
//        {
//            htmlProxy.setStep( this );
//        }
        WebView wv = new WebView( context );
        wv.getSettings().setUseWideViewPort( true );
        wv.getSettings().setLoadWithOverviewMode( true );
        wv.setInitialScale( 70 );
        wv.getSettings().setJavaScriptEnabled( true );
        wv.getSettings().setDomStorageEnabled( true );
        wv.addJavascriptInterface( htmlProxy, "HTMLOUT" );
        wv.setWebViewClient(
            new WebViewClient()
            {
                @Override
                public void onPageFinished( WebView view, String url )
                {
                    super.onPageFinished( view, url );
//                    wv.loadUrl("javascript:window.HTMLOUT.processHtml('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    {
                        wv.evaluateJavascript(
                            "(function() { return decodeURIComponent('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                            new ValueCallback<String>()
                            {
                                @Override
                                public void onReceiveValue( String html )
                                {
                                    //ahat: see http://stackoverflow.com/questions/19788294/how-does-evaluatejavascript-work
                                    JsonReader reader = new JsonReader( new StringReader( html ));
                                    // Must set lenient to parse single values
                                    reader.setLenient(true);
//                                Log.d("HTML", html);
                                    try
                                    {
                                        if(reader.peek() != JsonToken.NULL) {
                                            if( reader.peek() == JsonToken.STRING) {
                                                String msg = reader.nextString();
                                                if(msg != null) {
                                                    onHtmlReady( html );
//                                                    Toast.makeText( getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }

                                    }
                                    catch( Exception e )
                                    {
                                        e.printStackTrace();
                                    }
                                    finally
                                    {
                                        try {
                                            reader.close();
                                        } catch (IOException e) {
                                            // NOOP
                                        }
                                    }
                                }
                            } );
                    }
                }

            } );
        this.webView = wv;
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
