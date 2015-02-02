package miniblog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import miniblog.util.DateAdapter;

@Entity
@Table(name = "Articles")
public class Articles {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name = "users_id")
    private int users_id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private int status;

    @Column(name = "date_create")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date date_create;

    @Column(name = "date_modify")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date date_modify;

    public Articles() {
        Date date = new Date();
        // set date_create a post
        this.date_create = date;
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
        return users_id;
    }

    public void setUsers_id(int users_id)
    {
        this.users_id = users_id;
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
