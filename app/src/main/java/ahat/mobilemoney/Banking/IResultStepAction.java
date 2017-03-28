package ahat.mobilemoney.Banking;

/**
 * Created by antonis on 26/3/2017.
 *
 */

public interface IResultStepAction
{
    void execute( Step step, BankTaskRunner runner );
}
