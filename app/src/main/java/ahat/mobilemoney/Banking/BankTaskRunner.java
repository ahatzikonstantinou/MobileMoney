package ahat.mobilemoney.Banking;

import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import ahat.mobilemoney.OnStepResult;

/**
 * Created by antonis on 26/3/2017.
 * Runs a task. Starts with the first step. If step succeeds moves to the next one. If it fails, it calls
 * the step's onfail method
 */

public class BankTaskRunner
{
    private boolean initialised;
    private Task task;
    private OnStepResult stepResultCallback;

    private Step currentStep;
    private WebView webView;

    public HtmlProxy getHtmlProxy()
    {
        return htmlProxy;
    }

//    public void setHtmlProxy( HtmlProxy htmlProxy )
//    {
//        this.htmlProxy = htmlProxy;
//    }

    private HtmlProxy htmlProxy;

    public Step getCurrentStep() { return currentStep; }

    private boolean explicitlyFinished;

    public BankTaskRunner()
    {
        initialised = false;
    }

    public void init( WebView webView, Task task, OnStepResult onStepResultCallback )
    {
        initialised = true;
        this.task = task;
        this.webView = webView;
        setupWebView( this.webView );
        htmlProxy = new HtmlProxy();
        stepResultCallback = onStepResultCallback;
        currentStep = null;
        explicitlyFinished = false;
        if( null != task.getSteps() && task.getSteps().size() > 0 )
        {
            currentStep = task.getSteps().get( 0 );
        }
    }

    private void setupWebView( WebView webView )
    {
        webView.getSettings().setUseWideViewPort( true );
        webView.getSettings().setLoadWithOverviewMode( true );
        webView.setInitialScale( 70 );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setDomStorageEnabled( true );
        webView.addJavascriptInterface( htmlProxy, "HTMLOUT" );
        webView.setWebViewClient(
            new WebViewClient()
            {
                @Override
                public void onPageFinished( WebView view, String url )
                {
                    super.onPageFinished( view, url );
                    if( Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
                    {
                        webView.loadUrl("javascript:window.HTMLOUT.processHtml('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                    }
                    else
                    {
                        webView.evaluateJavascript(
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
                                        if( reader.peek() != JsonToken.NULL) {
                                            if( reader.peek() == JsonToken.STRING) {
                                                String msg = reader.nextString();
                                                if(msg != null) {
                                                    htmlProxy.processHtml( msg );
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
    }

    public Task getTask()
    {
        return task;
    }

    public void setTask( Task task )
    {
        this.task = task;
    }

//    public void Start()
//    {
//        if( null != task.getSteps() && task.getSteps().size() > 0 )
//        {
//            currentStep = task.getSteps().get( 0 );
//            run();
//        }
//    }

    public void run() throws Exception
    {
        if( !initialised )
        {
            throw new Exception( new String( "Not initialised. Cannot run." ) );
        }
        if( !finished() && !explicitlyFinished )
        {
            if( null != currentStep )
            {
                currentStep.run( this );
            }
        }
    }

    public void stepFailed()
    {
        stepResultCallback.onFail();
    }

    public void stepSucceeded() { stepResultCallback.onSuccess(); }

    public void gotoNextStep( Step step )
    {
        List<Step> steps = task.getSteps();
        for( Step s : steps )
        {
            if( null != s && s.equals( step ) && steps.indexOf( step ) < steps.size() - 1 )
            {
                currentStep = steps.get( steps.indexOf( step ) + 1 );
                return;
            }
        }
        currentStep = null;
    }

    public void gotoStep( Step.Code code )
    {
        List<Step> steps = task.getSteps();
        for( Step s : steps )
        {
            if( null != s && s.getCode().equals( code ) )
            {
                currentStep = s;
                return;
            }
        }
        currentStep = null;
    }

    public void gotoLastStep()
    {
        currentStep = null;
        List<Step> steps = task.getSteps();
        if( null != steps && steps.size() > 0 )
        {
            currentStep = steps.get( steps.size() - 1 );
        }
    }

    public boolean finished()
    {
        return null == currentStep;
    }

    public boolean isExplicitlyFinished()
    {
        return explicitlyFinished;
    }

    public void setExplicitlyFinished( boolean explicitlyFinished )
    {
        this.explicitlyFinished = explicitlyFinished;
    }

    public int getCurrentStepIndex()
    {
        return task.getSteps().indexOf( currentStep );
    }

    public WebView getWebView()
    {
        return webView;
    }
}
