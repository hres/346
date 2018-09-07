package hc.fcdr.rws.model.pkg;

public class ComponentName
{

    private String component_name;

    public ComponentName()
    {

        component_name = null;
    }

    public ComponentName(final String component_name)
    {
        super();
        this.component_name = component_name;
    }

    public String getComponent_name()
    {
        return component_name;
    }

    public void setComponent_name(final String component_name)
    {
        this.component_name = component_name;
    }

}
