package ahat.mobilemoney.Banking;

import android.webkit.WebView;

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
        stepResultCallback = onStepResultCallback;
        currentStep = null;
        explicitlyFinished = false;
        if( null != task.getSteps() && task.getSteps().size() > 0 )
        {
            currentStep = task.getSteps().get( 0 );
        }
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

    public void stepSucceeded()
    {
        stepResultCallback.onSuccess();
    }

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
