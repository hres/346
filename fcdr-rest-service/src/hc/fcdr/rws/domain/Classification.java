package hc.fcdr.rws.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "classification")
public class Classification implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -2364195962877145135L;
    private Long              id;
    private String            classificationNumber;
    private String            classificationName;
    private String            classificationType;

    public Classification()
    {
        super();
        this.id = 0L;

    }

    public Classification(final Long id, final String classificationNumber,
            final String classificationName, final String classificationType)
    {
        super();
        this.id = id;
        this.classificationNumber = classificationNumber;
        this.classificationName = classificationName;
        this.classificationType = classificationType;
    }

    public Classification(final Classification classification)
    {
        super();
        this.id = classification.getId();
        this.classificationNumber = classification.getClassificationNumber();
        this.classificationName = classification.getClassificationName();
        this.classificationType = classification.getClassificationType();
    }

    public Long getId()
    {
        return id;
    }

    @XmlElement
    public void setId(final Long id)
    {
        this.id = id;
    }

    public String getClassificationNumber()
    {
        return classificationNumber;
    }

    @XmlElement
    public void setClassificationNumber(final String classificationNumber)
    {
        this.classificationNumber = classificationNumber;
    }

    public String getClassificationName()
    {
        return classificationName;
    }

    @XmlElement
    public void setClassificationName(final String classificationName)
    {
        this.classificationName = classificationName;
    }

    public String getClassificationType()
    {
        return classificationType;
    }

    @XmlElement
    public void setClassificationType(final String classificationType)
    {
        this.classificationType = classificationType;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((classificationName == null) ? 0
                : classificationName.hashCode());
        result = (prime * result) + ((classificationNumber == null) ? 0
                : classificationNumber.hashCode());
        result = (prime * result) + ((classificationType == null) ? 0
                : classificationType.hashCode());
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
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
        final Classification other = (Classification) obj;
        if (classificationName == null)
        {
            if (other.classificationName != null)
                return false;
        }
        else if (!classificationName.equals(other.classificationName))
            return false;
        if (classificationNumber == null)
        {
            if (other.classificationNumber != null)
                return false;
        }
        else if (!classificationNumber.equals(other.classificationNumber))
            return false;
        if (classificationType == null)
        {
            if (other.classificationType != null)
                return false;
        }
        else if (!classificationType.equals(other.classificationType))
            return false;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Classification [id=" + id + ", classificationNumber="
                + classificationNumber + ", classificationName="
                + classificationName + ", classificationType="
                + classificationType + "]";
    }

}
