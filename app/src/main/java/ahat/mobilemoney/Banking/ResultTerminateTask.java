package ahat.mobilemoney.Banking;

/**
 * Created by antonis on 26/3/2017.
 */

public class ResultTerminateTask implements IResultStepAction
{
    @Override
    public void execute( Step step, BankTaskRunner runner )
    {
        runner.setExpliciltyFinished( true );
    }
}
