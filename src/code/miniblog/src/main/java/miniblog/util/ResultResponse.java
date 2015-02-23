package miniblog.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "Result")
public class ResultResponse {
    
    @XmlAttribute(name = "meta")
    private StatusResponse meta;
    
    @XmlElement(name = "data")
    private Object data;
    
    public ResultResponse(StatusResponse meta, Object data) {
        this.meta = meta;
        this.data = data;
    }
    
    public ResultResponse() {
    }
    
    public StatusResponse getMeta()
    {
        return meta;
    }

    public void setMeta(StatusResponse meta)
    {
        this.meta = meta;
    }

    public Object getData()
    {
        
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }
    
    
}
