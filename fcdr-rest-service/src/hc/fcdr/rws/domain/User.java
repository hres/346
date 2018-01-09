package hc.fcdr.rws.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
        name = "user")
public class User implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -6231683984902258920L;
    private int               id;
    private String            name;
    private String            profession;

    public User()
    {
        super();
        id = 0;
        name = "";
        profession = "";
    }

    public User(final int id, final String name, final String profession)
    {
        super();
        this.id = id;
        this.name = name;
        this.profession = profession;
    }

    public int getId()
    {
        return id;
    }

    @XmlElement
    public void setId(final int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    @XmlElement
    public void setName(final String name)
    {
        this.name = name;
    }

    public String getProfession()
    {
        return profession;
    }

    @XmlElement
    public void setProfession(final String profession)
    {
        this.profession = profession;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + id;
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        result =
                (prime * result)
                        + ((profession == null) ? 0 : profession.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final User other = (User) obj;
        if (id != other.id)
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (profession == null)
        {
            if (other.profession != null)
                return false;
        }
        else if (!profession.equals(other.profession))
            return false;
        return true;
    }

}
