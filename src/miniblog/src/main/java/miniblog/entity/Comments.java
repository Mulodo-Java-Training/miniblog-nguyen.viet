package miniblog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import miniblog.util.DateAdapter;

@Entity
@Table(name = "Comments")
public class Comments {

    @Id
    @Column(name = "id")
    @GeneratedValue
    @XmlAttribute(name = "id")
    private int id;

    @Column(name = "users_id")
    @XmlElement(name = "users_id")
    private int users_id;

    @Column(name = "articles_id")
    @XmlElement(name = "articles_id")
    private int articles_id;

    @Column(name = "description")
    @XmlElement(name = "description")
    private String description;

    @Column(name = "status")
    @XmlElement(name = "status")
    private int status;

    @Column(name = "date_create")
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlElement(name = "date_create")
    private Date date_create;

    @Column(name = "date_modify")
    @XmlElement(name = "date_modify")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date date_modify;

    public Comments() {
        Date date = new Date();
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

    public int getArticles_id()
    {
        return articles_id;
    }

    public void setArticles_id(int article_id)
    {
        this.articles_id = article_id;
    }

    public void setDate_create(Date date_create)
    {
        this.date_create = date_create;
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
