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
   protected static final String DERBY_JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";  
   protected static final String DB_URL = "jdbc:derby://localhost:1527/derbyDatabase";
   
   //  Database credentials
   protected static final String USER = "user";
   protected static final String PASS = "123";
   
   //Logger
   protected static Logger log = Logger.getLogger(JDBCDriver.class);
   
   protected static final String CONNECTING = "Connecting to database...";
   protected static final String END_OF_EXECUTION = "End of SQL execution";
   
   //connects to DB and returns result from the sqlStatement
   public ResultSet executeSQLStatement(String sqlStatement) {
	   ResultSet rs = null;
	   log.debug(CONNECTING);
	   try {
		    Class.forName(DERBY_JDBC_DRIVER);
	    	Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	   log.debug(END_OF_EXECUTION);
	   return rs;
	}
}
