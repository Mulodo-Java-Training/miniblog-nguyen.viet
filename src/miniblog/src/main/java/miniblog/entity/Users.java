package miniblog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class Users {
     
    @Id
    @Column(name="id")
    private int id;
    
    @Column(name="username")
	private String username;
    
    @Column(name="password")
	private String password;
    
    @Column(name="firstname")
	private String firstname;
    
    @Column(name="lastname")
	private String lastname;
    
    @Column(name="birthday")
	private Date birthday;
    
    @Column(name="email")
	private String email;
    
    @Column(name="status")
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
	
	@Override
	public String toString() {
		return "id: "+getId()
		        +"--Username: "+getUsername()
		        +"--Password: " +getPassword()
		        +"--Lastname: "+getLastname()
		        +"--Firstname: " + getFirstname()
		        +"--Status: " +getStatus()
		        +"--Birthday: " +getBirthday()
		        +"--Email: " +getEmail();
	}
}