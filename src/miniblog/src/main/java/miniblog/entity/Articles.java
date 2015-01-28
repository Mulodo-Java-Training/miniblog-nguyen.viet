package miniblog.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Articles")
public class Articles {

    @Id
    @Column(name = "id")
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
    private String date_create;

    @Column(name = "date_modify")
    private String date_modify;

    public Articles() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        // get current date time with Date()
        Date date = new Date();
        // set date_create a post
        this.date_create = dateFormat.format(date).toString();
    }
    public Articles(int users_id, String title, String description, int status, String date_modify) {
        this.users_id = users_id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.date_modify = date_modify;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        // get current date time with Date()
        Date date = new Date();
        // set date_create a post
        this.date_create = dateFormat.format(date).toString();
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

    @Column(name = "date_create")
    public String getDate_create()
    {
        // Format date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        // get current date time with Date()
        Date date = new Date();
        // set date_create a post
        return this.date_create = dateFormat.format(date).toString();
    }

    public String getDate_modify()
    {
        return date_modify;
    }

    public void setDate_modify(String date_modify)
    {
        this.date_modify = date_modify;
    }

}
