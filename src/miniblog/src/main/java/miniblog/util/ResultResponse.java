package miniblog.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import miniblog.entity.Users;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "Result")
public class ResultResponse {
    
    @XmlAttribute(name = "meta")
    private Object meta;
    
    @XmlElement(name = "data")
    private Object data;
    
    public ResultResponse(Object meta, Object data) {
        this.meta = meta;
        if(data instanceof Users){
            ((Users) data).setPassword("********");
        }
            this.data = data;
    }
}
