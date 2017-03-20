package ahat.mobilemoney.Banking;

import java.util.List;

/**
 * Created by antonis on 19/3/2017.
 */

public class Bank
{
    /*
     * A banks version will change whenever something about this bank needs to change e.g. the bank login page has
     * changed so the corresponding step and actions need to be updated.
     */
    private long version;
    private String name;

    /*
     * A code is a short representatio for a bank such as 00A could be Piraeus, 00B could be Eurobank etc.
     */
    private String code;
    private String username;
    private String password;
    private List<Task> tasks;

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public List<Task> getTasks()
    {
        return tasks;
    }

    public void setTasks( List<Task> tasks )
    {
        this.tasks = tasks;
    }

    public long getVersion()
    {
        return version;
    }

    public void setVersion( long version )
    {
        this.version = version;
    }
}
