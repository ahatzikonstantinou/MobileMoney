package ahat.mobilemoney.Banking;

import java.io.Serializable;
import java.util.List;

/**
 * Created by antonis on 19/3/2017.
 * A Task is one of those things you do such as "Retrieve Accounts and Balances", "Pay money", etc.
 * A Task comprises of steps.
 */

public class Task implements Serializable
{
    public Task( Code code, List<Step> steps )
    {
        this.code = code;
        this.steps = steps;
    }

    public Code getCode()
    {
        return code;
    }

    public void setCode( Code code )
    {
        this.code = code;
    }

    public List<Step> getSteps()
    {
        return steps;
    }

    public void setSteps( List<Step> steps )
    {
        this.steps = steps;
    }

    public enum Code { TestLogin, ImportAccounts };
    private Code       code;
    private List<Step> steps;
}
