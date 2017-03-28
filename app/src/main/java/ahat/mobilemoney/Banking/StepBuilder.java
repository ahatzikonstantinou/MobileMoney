package ahat.mobilemoney.Banking;

import android.content.Context;

/**
 * Created by ahat on 3/27/17.
 */

public class StepBuilder
{
    private StepDefinition stepDefinition;
    private final CredentialsProvider credentialsProvider;
    private final Context context;

    public StepBuilder( StepDefinition stepDefinition, CredentialsProvider credentialsProvider, Context context )
    {
        this.stepDefinition = stepDefinition;
        this.credentialsProvider = credentialsProvider;
        this.context = context;
    }

    public UrlStep build()
    {
        return new UrlStep(
            stepDefinition.code,
            stepDefinition.name,
            stepDefinition.regex,
            buildUrlProvider( stepDefinition ),
            buildResultStepAction( stepDefinition.onSuccess ),
            buildResultStepAction( stepDefinition.onFail ),
            context
        );
    }

    private IResultStepAction buildResultStepAction( StepDefinition.ResultAction resultAction )
    {
        switch( resultAction )
        {
            case Continue:
                return new ResultContinue();
            case TerminateTask:
                return new ResultTerminateTask();
            case GotoLast:
                return new ResultGotoLast();
            default:
                return new ResultContinue();
        }
    }

    private IUrlProvider buildUrlProvider( StepDefinition stepDefinition )
    {
        switch( stepDefinition.urlProvider )
        {
            case Static:
                return new StaticUrlProvider( stepDefinition.url );
            case Credentials:
                return new CredentialsUrlProvider( stepDefinition.url, credentialsProvider.getUsername(), credentialsProvider.getPassword() );
            default:
                return new StaticUrlProvider( stepDefinition.url );
        }
    }
}
