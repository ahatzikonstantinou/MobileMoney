package ahat.mobilemoney.Banking;

/**
 * Created by antonis on 27/3/2017.
 */

public class ResultGotoLast implements IResultStepAction
{
    @Override
    public void execute( Step step, BankTaskRunner runner )
    {
        runner.gotoLastStep();
    }
}
