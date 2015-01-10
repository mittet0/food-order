package com.edd.food.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.edd.utils.BCrypt;

public class UserJDBCDriver extends JDBCDriver {

	private static final String ADD_NEW_USER = "INSERT INTO USERS (\"USER_NAME\", \"NAME\", \"PASSWORD\", \"USER_ADDRESS\", \"PHONE_NUMBER\", \"EMAIL\") values (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_USER_BY_USERNAME_AND_PASSWORD = "SELECT password FROM USERS WHERE USER_NAME=?";
	private static final String SELECT_USER = "SELECT name FROM USERS WHERE USER_NAME=?";
	private static final String SELECT_USER_ADMIN = "SELECT is_admin_role FROM USERS WHERE USER_NAME=?";

	public boolean addNewUser(String userName, String name, String password,
			String address, String phoneNumber, String email) {
		System.out.println("Connecting to database...");
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = conn.prepareStatement(ADD_NEW_USER);
			stmt.setString(1, userName);
			stmt.setString(2, name);
			stmt.setString(3, BCrypt.hashpw(password, BCrypt.gensalt()));
			stmt.setString(4, address);
			stmt.setString(5, phoneNumber);
			stmt.setString(6, email);
			
			int result = stmt.executeUpdate();
			if (result == 1) {
				System.out.println("Successfully added a new user");
			    FacesContext.getCurrentInstance()
			    	.addMessage(null, new FacesMessage("Successful registration!") );
			    System.out.println("End of SQL execution");
			    return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("End of SQL execution");
	    FacesContext.getCurrentInstance()
    		.addMessage(null, new FacesMessage(
    			FacesMessage.SEVERITY_ERROR,"Unsuccessful registration!", ""));
	    return false;
	}
	
	public boolean checkIfUserExist(String userName) {
		System.out.println("Connecting to database...");
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt1 = conn.prepareStatement(SELECT_USER);
			stmt1.setString(1, userName);
			ResultSet res = stmt1.executeQuery();
			while(res.next()) {
				System.out.println("End of SQL execution");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("End of SQL execution");
		return false;
	}

	public boolean isUserExist(String userName, String password) {
		System.out.println("Connecting to database...");
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = conn.prepareStatement(SELECT_USER_BY_USERNAME_AND_PASSWORD);
			stmt.setString(1, userName);
			ResultSet result = stmt.executeQuery();
			while (result.next()){
				System.out.println("Validating user credentials ...");
				if (BCrypt.checkpw(password, result.getString("password"))) {
					System.out.println("User credential are valid.");
					System.out.println("End of SQL execution");
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("End of SQL execution");
		return false;
	}
	public boolean isUserAdmin(String userName) {
		System.out.println("Connecting to database...");
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = conn.prepareStatement(SELECT_USER_ADMIN);
			stmt.setString(1, userName);
			ResultSet result = stmt.executeQuery();
			while (result.next()){
				System.out.println("Validating user credentials ...");
				if (result.getInt("is_admin_role") == 1) {
					log.info("The user is admin.");
					log.debug("End of SQL execution");
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		log.debug("End of SQL execution");
		return false;
	}
}
