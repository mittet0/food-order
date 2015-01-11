package com.edd.food.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.edd.food.jdbc.UserJDBCDriver;
import com.edd.food.pojo.User;

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
		User newUser = new User(name, userName, password, address, phoneNumber, email);
		if (userDriver.checkIfUserExist(userName)) {
			FacesContext.getCurrentInstance().addMessage(
					null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Username is in use! Please specify another one!", ""));
		} else if( userDriver.addNewUser(newUser)) {
			RequestContext.getCurrentInstance().execute("PF('dlg2').show()");
		}
	}
	
	public void editUser() {
		String userNameForEdit = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("app.user.name");
		UserJDBCDriver userDriver = new UserJDBCDriver();
		User editedUser = new User(name, userNameForEdit, password, address, phoneNumber);
		if( userDriver.editUser(editedUser)) {
			RequestContext.getCurrentInstance().execute("PF('dlg3').show()");
		}
	}
}
