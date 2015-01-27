package miniblog.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.annotate.JsonIgnore;

import miniblog.util.DateAdapter;


@Entity
@Table(name="Users")
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "data")
public class Users implements Serializable{
    private static final long serialVersionUID = 2L;
    
    @Id
    @Column(name="id")
    @XmlAttribute(name = "id")
    private int id;
    
    @Column(name="username")
    @XmlElement(name = "username")
	private String username;
    
    @Column(name="password")
    @XmlElement(name = "password")
	private String password;
    
    @Column(name="firstname")
    @XmlElement(name = "firstname")
	private String firstname;
    
    @Column(name="lastname")
    @XmlElement(name = "lastname")
	private String lastname;
    
    @Column(name="birthday")
    @XmlElement(name = "birthday")
    @XmlJavaTypeAdapter(DateAdapter.class)
	private Date birthday;
    
    @Column(name="email")
    @XmlElement(name = "email")
	private String email;
    
    @Column(name="status")
    @XmlElement(name = "status")
	private int status;

    
    public Users() {
	}
    
	public Users(String username, String password, String firstname,
			String lastname, Date birthday, String email, int status) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthday = birthday;
		this.email = email;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}