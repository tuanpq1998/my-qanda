/**
 * @author admin
 * @date 25-10-2019
 */

package com.tuanpq.myaskfm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.tuanpq.myaskfm.utility.DateTimeHandler;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email")
	@Email(message="Email invalid format!")
	@NotBlank(message="Email is required!")
	private String email;
	
	@Column(name="fullname")
	private String fullname;
	
	@Column(name="joinDate")
	private String joinDate;
	
	@Column(name="location")
	private String location;
	
	@Column(name="workplace")
	private String workplace;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getJoinDate() {
		return DateTimeHandler.convertToDisplayDate(joinDate);
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", email=" + email + ", fullname=" + fullname
				+ ", joinDate=" + joinDate + ", location=" + location
				+ ", workplace=" + workplace + "]";
	}

	public User(int id, String username, String password, String email,
			String fullname, String joinDate, String location,
			String workplace) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullname = fullname;
		this.joinDate = joinDate;
		this.location = location;
		this.workplace = workplace;
	}

	public User() {
		super();
	}
	
}
