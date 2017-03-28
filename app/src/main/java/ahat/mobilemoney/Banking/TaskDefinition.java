package ahat.mobilemoney.Banking;

import java.io.Serializable;
import java.util.List;

/**
 * Created by antonis on 28/3/2017.
 */

public class TaskDefinition implements Serializable
{
    public TaskDefinition( String code, List<StepDefinition> steps )
    {
        this.code = code;
        this.steps = steps;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public List<StepDefinition> getSteps()
    {
        return steps;
    }

    public void setSteps( List<StepDefinition> steps )
    {
        this.steps = steps;
    }

    public enum Code { TestLogin, ImportAccounts };
    private String  code;
    private List<StepDefinition> steps;
}
