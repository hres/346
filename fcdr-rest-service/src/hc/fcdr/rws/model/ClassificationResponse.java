package hc.fcdr.rws.model;

import javax.xml.bind.annotation.XmlRootElement;

import hc.fcdr.rws.domain.Classification;
import hc.fcdr.rws.domain.Product;

@XmlRootElement
public class ClassificationResponse
{
    private String  classification_number;
    private String  classification_name;
    private String  classification_type;

    public ClassificationResponse()
    {
        super();
        this.classification_number = "";
        this.classification_name = "";
        this.classification_type = "";
    }

    public ClassificationResponse(String classificationNumber,
            String classificationName, String classificationType)
    {
        super();
        this.classification_number = classificationNumber;
        this.classification_name = classificationName;
        this.classification_type = classificationType;
    }
    
    public ClassificationResponse(Classification classification)
    {
        super();
        this.classification_number = classification.getClassificationNumber();
        this.classification_name = classification.getClassificationName();
        this.classification_type = classification.getClassificationType();
    }

    public String getClassification_number()
    {
        return classification_number;
    }

    public void setClassification_number(String classification_number)
    {
        this.classification_number = classification_number;
    }

    public String getClassification_name()
    {
        return classification_name;
    }

    public void setClassification_name(String classification_name)
    {
        this.classification_name = classification_name;
    }

    public String getClassification_type()
    {
        return classification_type;
    }

    public void setClassification_type(String classification_type)
    {
        this.classification_type = classification_type;
    }

    public void setClassificationType(String classificationType)
    {
        this.classification_type = classificationType;
    }

}
