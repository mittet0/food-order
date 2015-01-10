package com.edd.food.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.edd.food.jdbc.UserJDBCDriver;

@ManagedBean
@SessionScoped
public class UserView {

	public static final String AUTH_KEY = "app.user.name";
	private String username;
	private String password;
	private String name;
	private String address;
	private String phoneNumber;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void save() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Welcome " + username + " " + password));
    }
	
	public String login() {
		//must be added logic to evaluate user from the database
		 if (new UserJDBCDriver().isUserExist(username, password)) {
			 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
				        AUTH_KEY, username);
			 if (new UserJDBCDriver().isUserAdmin(username)){
				 return "/restricted/admin";
			 } else {
				 return "/restricted/home"; 
			 }	
		 } else {
			 FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
		        .remove(AUTH_KEY);
			 FacesContext.getCurrentInstance().addMessage(null, 
					 new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password!", 
							 ""));
			 return "login";
		 }
	}
	public boolean isLoggedIn() {
		return FacesContext.getCurrentInstance().getExternalContext()
		        .getSessionMap().get(AUTH_KEY) != null;
	}
	
	public String logout() {
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
	        .remove(AUTH_KEY);
	    return "/index?faces-redirect=true";
	}
}