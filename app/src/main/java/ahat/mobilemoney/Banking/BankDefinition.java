package ahat.mobilemoney.Banking;

import java.io.Serializable;
import java.util.List;

/**
 * Created by antonis on 28/3/2017.
 * These are the objects exchanged between the application and the platform server
 */

public class BankDefinition implements Serializable
{
    private long       version;
    private String     name;
    private String     code;
    private boolean    active;
    private List<TaskDefinition> tasks;

    public BankDefinition()
    {
    }

    public BankDefinition( long version, String name, String code, boolean active, List<TaskDefinition> tasks )
    {
        this.version = version;
        this.name = name;
        this.code = code;
        this.active = active;
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

    public List<TaskDefinition> getTasks()
    {
        return tasks;
    }

    public void setTasks( List<TaskDefinition> tasks )
    {
        this.tasks = tasks;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive( boolean active )
    {
        this.active = active;
    }
}
