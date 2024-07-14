package com.bishnu.Entity;

import jakarta.validation.constraints.*;

public class RegisterDto {
	
	@NotEmpty
	private String fname;
	@NotEmpty
	private String lname;
	@Email
	@NotEmpty
	private String email;
	@NotEmpty
	private String mobile;
	@NotEmpty
	@Size(min=6,message="Minimum 6 char long")
	private String password;

	
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	}
