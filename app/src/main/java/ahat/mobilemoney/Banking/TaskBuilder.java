package ahat.mobilemoney.Banking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by antonis on 12/5/2017.
 */

public class TaskBuilder
{
    private StepBuilder stepBuilder;

    public TaskBuilder( StepBuilder stepBuilder )
    {
        this.stepBuilder = stepBuilder;
    }

    public Task build( TaskDefinition taskDefinition )
    {
        List<Step> steps = new ArrayList<>();
        taskDefinition.getSteps().forEach( sd -> steps.add( stepBuilder.build( sd ) ) );
        return new Task( taskDefinition.getCode(), steps );
    }
}
