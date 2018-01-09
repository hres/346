package hc.fcdr.rws.model.pkg;

import java.util.ArrayList;
import java.util.List;

public class NftRequest
{

    private Boolean        flag;
    private List<NftModel> nft;
    private Integer        package_id;

    public NftRequest()
    {

        this(null, new ArrayList<NftModel>(), 0);

    }

    public NftRequest(final Boolean flag, final ArrayList<NftModel> nft,
            final Integer package_id)
    {
        super();
        this.flag = flag;
        this.nft = nft;
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

    public List<NftModel> getNft()
    {
        return nft;
    }

    public void setNft(final List<NftModel> nft)
    {
        this.nft = nft;
    }

    public Integer getPackage_id()
    {
        return package_id;
    }

    public void setPackage_id(final Integer package_id)
    {
        this.package_id = package_id;
    }

}
