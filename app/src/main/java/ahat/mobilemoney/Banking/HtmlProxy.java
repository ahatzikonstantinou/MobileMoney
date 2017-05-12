package ahat.mobilemoney.Banking;

import android.webkit.JavascriptInterface;

/**
 * Created by ahat on 3/27/17.
 * This class is used to receive the source html after a webview has loaded a url. Then it should be passed to a urlstep
 * in order to perform checks and verify that the step was executed successfully e.g. use regex to see if the logout button
 * is present
 */

public class HtmlProxy
{
    private UrlStep step;
    public HtmlProxy( UrlStep step )
    {
        this.step = step;
    }

    public UrlStep getStep()
    {
        return step;
    }

    public void setStep( UrlStep step )
    {
        this.step = step;
    }

    @JavascriptInterface
    public void processHtml( String html ) throws Exception
    {
        step.onHtmlReady( html );
    }
}
