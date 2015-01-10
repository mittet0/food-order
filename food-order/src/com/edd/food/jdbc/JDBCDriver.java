package com.edd.food.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.edd.food.beans.FoodBean;

public class JDBCDriver {
		
   // JDBC driver name and database URL
   public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   public static final String DB_URL = "jdbc:derby://localhost:1527/derbyDatabase";
   //  Database credentials
   public static final String USER = "user";
   public static final String PASS = "123";
   private static Logger log = Logger.getLogger(JDBCDriver.class);
   
   //connects to DB and returns result from the sqlStatement
   public ResultSet executeSQLStatement(String sqlStatement) {
	   ResultSet rs = null;
	   log.debug("Connecting to database...");
	   try {
		    Class.forName("org.apache.derby.jdbc.ClientDriver");
	    	Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	   log.debug("End of SQL execution");
	   return rs;
	}
}
