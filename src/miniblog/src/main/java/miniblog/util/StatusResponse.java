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
    private String descrition;
    
    @XmlElement(name = "message")
    private String message;

    public StatusResponse(int code,String descrition, String message ) {
        this.code = code;
        this.descrition = descrition;
        this.message = message;
    }
}