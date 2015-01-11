package com.edd.food.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.edd.food.pojo.Food;
import com.edd.utils.BCrypt;

public class FoodJDBCDriver extends JDBCDriver {
	
	private static String ADD_FOOD = "INSERT INTO FOODDB (\"FOOD_NAME\", \"FOOD_DESCRIPTION\", \"FOOD_COST\", \"FOOD_CATEGORY\") values (?, ?, ?, ?)";
	
	private static String DELETE_FOOD = "DELETE FROM FOODDB WHERE FOOD_NAME=?";
	
	private static String FOOD_BY_CATEGORY = "SELECT * FROM FOODDB WHERE FOOD_CATEGORY=?";
	
	private static String SELECT_ALL_FOODS = "SELECT * FROM foodDB";
	
	public List<Food> getFoods () {
		List<Food> foods = new ArrayList<Food>();
		JDBCDriver jdbc = new JDBCDriver();
		ResultSet rs = jdbc.executeSQLStatement(SELECT_ALL_FOODS);
		try {
			while (rs.next()) {
				Food usr = new Food();
				usr.setName(rs.getString("food_name"));
				usr.setDescription(rs.getString("food_description"));
				usr.setCost(rs.getLong("food_cost"));
				usr.setCategory(rs.getString("food_category"));
				foods.add(usr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return foods;
	}
	
	public List<Food> getFoods(String category) {
		List<Food> foods = new ArrayList<Food>();
		log.debug(CONNECTING);
		try {
			Class.forName(DERBY_JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = conn.prepareStatement(FOOD_BY_CATEGORY);
			stmt.setString(1, category);
			log.info("category: " + category);
			
			ResultSet rs = stmt.executeQuery();
			log.debug("Result...");
			
			while (rs.next()) {
				Food usr = new Food();
				usr.setName(rs.getString("food_name"));
				usr.setDescription(rs.getString("food_description"));
				usr.setCost(rs.getLong("food_cost"));
				usr.setCategory(rs.getString("food_category"));
				foods.add(usr);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		log.debug(END_OF_EXECUTION);
		
		return foods;
	}
	
	public void addFood(Food food) {
		log.debug(CONNECTING);
		try {
			Class.forName(DERBY_JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = conn.prepareStatement(ADD_FOOD);
			stmt.setString(1, food.getName());
			stmt.setString(2, food.getDescription());
			stmt.setDouble(3, food.getCost());
			stmt.setString(4, food.getCategory());
			
			int result = stmt.executeUpdate();
			if (result == 1) {
				log.info("Successfully added a new food");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		log.info(END_OF_EXECUTION);
	}
	public void deleteFood(String foodName) {
		log.debug(CONNECTING);
		try {
			Class.forName(DERBY_JDBC_DRIVER);
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = conn.prepareStatement(DELETE_FOOD);
			stmt.setString(1, foodName);
			
			int result = stmt.executeUpdate();
			if (result == 1) {
				log.info("Successfully deleted a food");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		log.info(END_OF_EXECUTION);
	}
}
