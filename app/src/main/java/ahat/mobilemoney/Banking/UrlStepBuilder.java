package ahat.mobilemoney.Banking;

/**
 * Created by ahat on 3/27/17.
 */

public class UrlStepBuilder
{
    private UrlStepDefinition stepDefinition;

    public UrlStepBuilder( UrlStepDefinition stepDefinition )
    {
        this.stepDefinition = stepDefinition;
    }

    public UrlStep build()
    {
        return new UrlStep(
            stepDefinition.code,
            stepDefinition.name,
            stepDefinition.regex,
        )
    }
}
