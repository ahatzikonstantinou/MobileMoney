package ahat.mobilemoney.Banking;

/**
 * Created by antonis on 26/3/2017.
 */

public class ResultGotoStep implements IResultStepAction
{

    private Step.Code code;

    public ResultGotoStep( Step.Code code )
    {

        this.code = code;
    }
    @Override
    public void execute( Step step, BankTaskRunner runner )
    {
        runner.gotoStep( code );
    }
}
