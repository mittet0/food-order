package com.edd.food.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.edd.food.jdbc.FoodJDBCDriver;
import com.edd.food.pojo.Food;

@ManagedBean
@SessionScoped
public class FoodBean {
	private List <Food> foods = new ArrayList<Food>();

	public List<Food> getFoods() {
		FoodJDBCDriver foodJdbc = new FoodJDBCDriver();
		this.foods = foodJdbc.getFoods();
		return foods;
	}
}
