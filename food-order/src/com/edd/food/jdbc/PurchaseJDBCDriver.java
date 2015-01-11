package com.edd.food.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.edd.food.pojo.Food;

public class PurchaseJDBCDriver extends JDBCDriver {
	private static final String ADD_NEW_ORDER = "INSERT INTO ORDERS (\"FOOD_NAME\", \"FOOD_COST\", \"USER_NAME\", \"QUANTITY\") values (?, ?, ?, ?)";

	public boolean addNewOrder(Food food, String userName) {
		log.debug(CONNECTING);
		try {
			Class.forName(DERBY_JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = conn.prepareStatement(ADD_NEW_ORDER);
			stmt.setString(1, food.getName());
			stmt.setLong(2, food.getCost());
			stmt.setString(3, userName);
			stmt.setInt(4, food.getQuantity());
			
			int result = stmt.executeUpdate();
			if (result == 1) {
				log.info("Successfully added a new order");
			    FacesContext.getCurrentInstance()
			    	.addMessage(null, new FacesMessage("Successful order!") );
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
}
