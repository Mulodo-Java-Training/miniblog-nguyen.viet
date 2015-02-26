package miniblog.entity;

import java.util.Date;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import miniblog.api.CommonAPI;
import miniblog.constant.CommonConstant;
import miniblog.util.DateAdapter;
import miniblog.util.ResultResponse;

import org.jboss.resteasy.client.ClientResponse;

@SuppressWarnings("deprecation")
public class Articles {
    private int id;
    private int users_id;
    private Users users;
    private String title;
    private String description;
    private int status;
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date date_create;
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date date_modify;

    public Articles() {
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getUsers_id()
    {
        return this.users_id;
    }

    public void setUsers_id(int users_id)
    {
        this.users_id = users_id;
    }

    public Users getUsers()
    {
        // Create API operation
        CommonAPI api = new CommonAPI();
        // operate via API
        ClientResponse<ResultResponse> response = api.processing(CommonConstant.USER_INFOR_URL + getUsers_id(),
                MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON, null, CommonConstant.METHOD_GET);
        // Create object and get value return
        ResultResponse apiReturn = new ResultResponse();
        apiReturn = api.parseMeta(response);
        this.users = (Users) api.parseData(apiReturn, CommonConstant.TYPE_USER);
        return this.users;
    }

    public void setUsers(Users users)
    {
        this.users = users;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public Date getDate_create()
    {
        return this.date_create;
    }

    public Date getDate_modify()
    {
        return date_modify;
    }

    public void setDate_modify(Date date_modify)
    {
        this.date_modify = date_modify;
    }
}
