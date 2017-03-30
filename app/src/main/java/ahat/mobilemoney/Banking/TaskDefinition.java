package ahat.mobilemoney.Banking;

import java.io.Serializable;
import java.util.List;

/**
 * Created by antonis on 28/3/2017.
 */

public class TaskDefinition implements Serializable
{
    public TaskDefinition( Task.Code code, List<StepDefinition> steps )
    {
        this.code = code;
        this.steps = steps;
    }

    public Task.Code getCode()
    {
        return code;
    }

    public void setCode( Task.Code code )
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

    private Task.Code  code;
    private List<StepDefinition> steps;
}
