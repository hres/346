package hc.fcdr.rws.model.pkg;

public class NftGetModel
{

    private Integer package_id;
    private Boolean flag;

    public NftGetModel()
    {
        package_id = null;
        flag = null;
    }

    public NftGetModel(final Integer package_id, final Boolean flag)
    {
        super();
        this.package_id = package_id;
        this.flag = flag;
    }

    public Integer getPackage_id()
    {
        return package_id;
    }

    public void setPackage_id(final Integer package_id)
    {
        this.package_id = package_id;
    }

    public Boolean getFlag()
    {
        return flag;
    }

    public void setFlag(final Boolean flag)
    {
        this.flag = flag;
    }

}
