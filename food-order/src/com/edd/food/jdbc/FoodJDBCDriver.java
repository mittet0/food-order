package com.edd.food.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.edd.food.pojo.Food;

public class FoodJDBCDriver {
	
	private List<Food> foods = new ArrayList<Food>();
	
	public List<Food> getFoods () {
		JDBCDriver jdbc = new JDBCDriver();
		ResultSet rs = jdbc.executeSQLStatement("SELECT * FROM foodDB");
		try {
			while (rs.next()) {
				Food usr = new Food();
				usr.setName(rs.getString("food_name"));
				usr.setDescription(rs.getString("food_description"));
				usr.setCost(rs.getLong("food_cost"));
				foods.add(usr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return foods;
	}
}
