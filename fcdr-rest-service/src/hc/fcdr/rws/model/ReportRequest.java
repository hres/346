package hc.fcdr.rws.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReportRequest
{
    @XmlElement
    public String  inputDir;
   
    @XmlElement
    public boolean sendMail;

    @Override
    public String toString()
    {
        return "ImportRequest [inputDir=" + inputDir + ", sendMail=" + sendMail
                + "]";
    }

    

}
