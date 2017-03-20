package ahat.mobilemoney.Banking;

import java.util.List;

/**
 * Created by antonis on 19/3/2017.
 * This is the class that is exchanged during communications between the application and the platform.
 */

public class BankDTO
{
    private long       version;
    private String     name;
    private String     code;
    private List<Task> tasks;

    public BankDTO( long version, String name, String code, List<Task> tasks )
    {
        this.version = version;
        this.name = name;
        this.code = code;
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

    public List<Task> getTasks()
    {
        return tasks;
    }

    public void setTasks( List<Task> tasks )
    {
        this.tasks = tasks;
    }
}
