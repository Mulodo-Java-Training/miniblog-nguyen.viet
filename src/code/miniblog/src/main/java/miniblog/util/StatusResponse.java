package miniblog.util;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

  
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "meta")
public class StatusResponse implements Serializable{
  
    private static final long serialVersionUID = 1L;
  
    @XmlAttribute(name = "code")
    private int code;
    
    @XmlElement(name = "description")
    private String description;
    
    @XmlElement(name = "message")
    private String message;

    public StatusResponse(int code,String description, String message ) {
        this.code = code;
        this.description = description;
        this.message = message;
    }
    public StatusResponse() {
        
    }
    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
    
}