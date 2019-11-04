/**
 * @author admin
 * @date 02-11-2019
 */

package com.tuanpq.myaskfm.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import com.tuanpq.myaskfm.validation.FieldMatch;
import com.tuanpq.myaskfm.validation.UniqueUsername;
import com.tuanpq.myaskfm.validation.ValidationGroup.OnChangePassword;

@FieldMatch.List({
	@FieldMatch(
			first = "password", second = "rePassword", 
			message = "Password and repassword field must match!",
			groups = {OnChangePassword.class, Default.class}
	),
	@FieldMatch(
			first = "oldPassword", second = "username", 
			message = "Incorrect password!",
			groups = {OnChangePassword.class},
			isUserValidate = true
	)
})
public class TempUser {

	@NotBlank(message = "Fullname is required!")
	private String fullname;
	
	@NotBlank(message = "Username is required!")
	@Size(min=3, max=10, message="Username length must be between 3 and 10!")
	@Pattern(regexp = "^[A-Za-z0-9]+$", 
		message="Username can only contain character and number")
	@UniqueUsername(message = "Username has already existed!")
	private String username;
	
	@Email(message = "Email must be valid!")
	@NotBlank(message = "Email is required!")
	private String email;
	
	@NotBlank(message = "Password is required!", 
			groups = {OnChangePassword.class, Default.class})
	@Size(min=6, max=15, message="Password length must be between 6 and 15!", 
		groups = {OnChangePassword.class, Default.class})
	private String password;
	
	private String rePassword;
	
	@NotBlank(message = "Password is required!", groups = {OnChangePassword.class})
	@Size(min=6, max=15, message="Password length must be between 6 and 15!", 
		groups = {OnChangePassword.class})
	private String oldPassword;
	
	public String getFullname() {
		return fullname;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRePassword() {
		return rePassword;
	}
	
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public TempUser() {
		super();
	}
	
	public TempUser(
			String fullname, String username, String email, String password, String rePassword) {
		super();
		this.fullname = fullname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.rePassword = rePassword;
	}

	@Override
	public String toString() {
		return "TempUser [fullname=" + fullname + ", username=" + username
				+ ", email=" + email + ", password=" + password
				+ ", rePassword=" + rePassword + ", oldPassword=" + oldPassword
				+ "]";
	}
	
}
