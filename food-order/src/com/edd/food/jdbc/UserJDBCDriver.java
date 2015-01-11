package com.edd.food.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.edd.food.pojo.User;
import com.edd.utils.BCrypt;

public class UserJDBCDriver extends JDBCDriver {

	private static final String ADD_NEW_USER = "INSERT INTO USERS (\"USER_NAME\", \"NAME\", \"PASSWORD\", \"USER_ADDRESS\", \"PHONE_NUMBER\", \"EMAIL\") values (?, ?, ?, ?, ?, ?)";
	
	private static final String SELECT_USER_BY_USERNAME_AND_PASSWORD = "SELECT password FROM USERS WHERE USER_NAME=?";
	
	private static final String SELECT_USER = "SELECT name FROM USERS WHERE USER_NAME=?";
	
	private static final String SELECT_USER_ADMIN = "SELECT is_admin_role FROM USERS WHERE USER_NAME=?";
	
	private static final String UPDATE_USER = "UPDATE USERS SET \"PASSWORD\"=?, \"NAME\"=?, \"USER_ADDRESS\"=?, \"PHONE_NUMBER\"=? WHERE \"USER_NAME\"=?";

	public boolean addNewUser(User user) {
		log.debug(CONNECTING);
		try {
			Class.forName(DERBY_JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = conn.prepareStatement(ADD_NEW_USER);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getName());
			stmt.setString(3, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			stmt.setString(4, user.getAddress());
			stmt.setString(5, user.getPhoneNumber());
			stmt.setString(6, user.getEmail());
			
			int result = stmt.executeUpdate();
			if (result == 1) {
				log.info("Successfully added a new user");
			    FacesContext.getCurrentInstance()
			    	.addMessage(null, new FacesMessage("Successful registration!") );
			    log.debug(END_OF_EXECUTION);
			    return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		log.debug(END_OF_EXECUTION);
	    FacesContext.getCurrentInstance()
    		.addMessage(null, new FacesMessage(
    			FacesMessage.SEVERITY_ERROR,"Unsuccessful registration!", ""));
	    return false;
	}
	
	public boolean checkIfUserExist(String userName) {
		log.debug(CONNECTING);
		try {
			Class.forName(DERBY_JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt1 = conn.prepareStatement(SELECT_USER);
			stmt1.setString(1, userName);
			ResultSet res = stmt1.executeQuery();
			while(res.next()) {
				log.debug(END_OF_EXECUTION);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		log.debug(END_OF_EXECUTION);
		return false;
	}

	public boolean isUserExist(String userName, String password) {
		log.debug(CONNECTING);
		try {
			Class.forName(DERBY_JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = conn.prepareStatement(SELECT_USER_BY_USERNAME_AND_PASSWORD);
			stmt.setString(1, userName);
			ResultSet result = stmt.executeQuery();
			while (result.next()){
				log.info("Validating user credentials ...");
				if (BCrypt.checkpw(password, result.getString("password"))) {
					log.info("User credential are valid.");
					log.debug(END_OF_EXECUTION);
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		log.debug(END_OF_EXECUTION);
		return false;
	}
	public boolean isUserAdmin(String userName) {
		log.debug(CONNECTING);
		try {
			Class.forName(DERBY_JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = conn.prepareStatement(SELECT_USER_ADMIN);
			stmt.setString(1, userName);
			ResultSet result = stmt.executeQuery();
			while (result.next()){
				log.info("Validating user credentials ...");
				if (result.getInt("is_admin_role") == 1) {
					log.info("The user is admin.");
					log.debug(END_OF_EXECUTION);
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		log.debug(END_OF_EXECUTION);
		return false;
	}
	
	public boolean editUser(User user) {
		log.debug(CONNECTING);
		try {
			Class.forName(DERBY_JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = conn.prepareStatement(UPDATE_USER);
			stmt.setString(1, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getAddress());
			stmt.setString(4, user.getPhoneNumber());
			stmt.setString(5, user.getUsername());
			
			int result = stmt.executeUpdate();
			if (result == 1) {
				log.info("Successfully edited a new user");
			    FacesContext.getCurrentInstance()
			    	.addMessage(null, new FacesMessage("Successful Edit!") );
			    log.debug(END_OF_EXECUTION);
			    return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		log.debug(END_OF_EXECUTION);
	    FacesContext.getCurrentInstance()
    		.addMessage(null, new FacesMessage(
    			FacesMessage.SEVERITY_ERROR,"Unsuccessful edit!", ""));
	    return false;
	}
}
