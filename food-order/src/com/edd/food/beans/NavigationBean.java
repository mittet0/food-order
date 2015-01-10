package com.edd.food.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *  * Simple navigation bean  * @author dgangov  *  
 */
@ManagedBean
@SessionScoped
public class NavigationBean implements Serializable {

	private static final long serialVersionUID = 1520318172495977648L;

	public NavigationBean() {
		
	}
	public String redirectToLogin() {
		return "/login.xhtml?faces-redirect=true";
	}
	
	public String forwardToHome() {
		return "/userHome";
	}

}
