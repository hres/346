package hc.fcdr.rws.model.pkg;

import java.util.ArrayList;
import java.util.List;

public class NftView
{
    private int            status;
    private List<NftModel> nft;
    private String         message;

    public NftView()
    {

        this(0, new ArrayList<NftModel>(), null);
    }

    public NftView(final int status, final List<NftModel> nft,
            final String message)
    {
        super();
        this.status = status;
        this.nft = nft;
        this.message = message;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(final int status)
    {
        this.status = status;
    }

    public List<NftModel> getNft()
    {
        return nft;
    }

    public void setNft(final List<NftModel> nft)
    {
        this.nft = nft;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(final String message)
    {
        this.message = message;
    }

}
