package com.edd.food.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.edd.food.jdbc.UserJDBCDriver;

@ManagedBean(name="regBean")
@SessionScoped()
public class RegistrationBean {
	
	private	String userName;
	private	String password;	    
	private	String name;	    	    
	private	String email; 	    
	private	String address;
	private String phoneNumber;
	private boolean passwordMatches;
	
	public boolean isPasswordMatches() {
		return passwordMatches;
	}
	public void setPasswordMatches(boolean passwordMatches) {
		this.passwordMatches = passwordMatches;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public RegistrationBean() {
		
	}
	
	public void checkPassword() {
		
	}
	
	public void setNewUser() {
		UserJDBCDriver userDriver = new UserJDBCDriver();
		
		userDriver.addNewUser(userName, name, password, address, phoneNumber, email);
	}
}
