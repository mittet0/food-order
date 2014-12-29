package com.edd.food.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class UserJDBCDriver extends JDBCDriver {

	private static String ADD_NEW_USER = "INSERT INTO USERS (\"USER_NAME\", \"NAME\", \"PASSWORD\", \"USER_ADDRESS\", \"PHONE_NUMBER\", \"EMAIL\") values (?, ?, ?, ?, ?, ?)";

	public void addNewUser(String userName, String name, String password,
			String address, String phoneNumber, String email) {
		System.out.println("Connecting to database...");
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = conn.prepareStatement(ADD_NEW_USER);
			stmt.setString(1, userName);
			stmt.setString(2, name);
			stmt.setString(3, password);
			stmt.setString(4, address);
			stmt.setString(5, phoneNumber);
			stmt.setString(6, email);
			
			int result = stmt.executeUpdate();
			if (result == 1) {
				System.out.println("Successfully added a new user");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("End of SQL execution");
		
		FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful registration!") );
	}
}
