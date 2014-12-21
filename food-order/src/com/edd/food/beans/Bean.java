package com.edd.food.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.edd.food.pojo.Food;

@ManagedBean
@SessionScoped
public class Bean {
	private List <Food> foods = new ArrayList<Food>();;

	@PostConstruct
 	public void populateEmployeeList(){
        for(int i = 1 ; i <= 10 ; i++){
            Food food = new Food();
            food.setName("Name:#" + String.valueOf(i));
            food.setDescription("Description#"+i);
            this.foods.add(food);
        }
    }
	public List<Food> getFoods() {
		return foods;
	}
}
