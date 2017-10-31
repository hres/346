package hc.fcdr.rws.model.product;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductUpdateRequest
{
    @XmlElement
    public String  product_manufacturer;
    @XmlElement
    public String  product_brand;
    @XmlElement
    public String  product_description;
    @XmlElement
    public String  product_comment;
    @XmlElement
    public Integer cnf_code;
    @XmlElement
    public Double  cluster_number;
    @XmlElement
    public String  restaurant_type;
    @XmlElement
    public String  type;
    @XmlElement
    public String  edited_by;
    @XmlElement
    public Integer    product_id;
    @XmlElement
    public Double  classification_number;
    @XmlElement
    public String  classification_name;
    @XmlElement
    public String  classification_type;

    public List<Object> getProductFieldList()
    {
        List<Object> productFieldList = new ArrayList<Object>();
        productFieldList.add(product_manufacturer);
        productFieldList.add(product_brand);
        productFieldList.add(product_description);
        productFieldList.add(product_comment);
        productFieldList.add(cnf_code);
        productFieldList.add(cluster_number);
        productFieldList.add(restaurant_type);
        productFieldList.add(type);
        productFieldList.add(edited_by);
        productFieldList.add(product_id);
        
        return productFieldList;
    }

    public List<Object> getClassificationFieldList()
    {
        List<Object> classificationFieldList = new ArrayList<Object>();
        classificationFieldList.add(classification_number);
        classificationFieldList.add(classification_name);
        classificationFieldList.add(classification_type);
        
        return classificationFieldList;
    }

    @Override
    public String toString()
    {
        return "ProductUpdateRequest [product_id=" + product_id
                + ", product_manufacturer=" + product_manufacturer
                + ", product_brand=" + product_brand + ", product_description="
                + product_description + ", product_comment=" + product_comment
                + ", cnf_code=" + cnf_code + ", cluster_number="
                + cluster_number + ", classification_number="
                + classification_number + ", classification_name="
                + classification_name + ", classification_type="
                + classification_type + ", restaurant_type=" + restaurant_type
                + ", type=" + type + ", edited_by=" + edited_by + "]";
    }

}
