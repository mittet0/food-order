package com.edd.food.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.*;
import org.primefaces.context.RequestContext;

import com.edd.food.jdbc.FoodJDBCDriver;
import com.edd.food.jdbc.PurchaseJDBCDriver;
import com.edd.food.jdbc.UserJDBCDriver;
import com.edd.food.pojo.Food;

@ManagedBean
@SessionScoped
public class FoodBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(FoodBean.class);
	
	private List <Food> foods = new ArrayList<Food>();

	private List <Food> selectedFoods = new ArrayList<Food>();
	
	String foodName;
	
	String foodDescription;
	
	Long foodCost;
	
	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getFoodDescription() {
		return foodDescription;
	}

	public void setFoodDescription(String foodDescription) {
		this.foodDescription = foodDescription;
	}

	public Long getFoodCost() {
		return foodCost;
	}

	public void setFoodCost(Long foodCost) {
		this.foodCost = foodCost;
	}
	
	public void setSelectedFoods(List<Food> selectedFoods) {
		this.selectedFoods = selectedFoods;
	}

	public List<Food> getSelectedFoods() {
		return selectedFoods;
	}
	
	@PostConstruct
	public void init() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedFood", selectedFoods);
	}
	
	public List<Food> getFoods() {
		FoodJDBCDriver foodJdbc = new FoodJDBCDriver();
		this.foods = foodJdbc.getFoods();
		return foods;
	}
	
	public void addToCart(ActionEvent actionEvent) {
		selectedFoods = (List <Food>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFood");
		String foodName = (String) actionEvent.getComponent().getAttributes().get("foodName");
		String foodDescription = (String) actionEvent.getComponent().getAttributes().get("foodDescription");
		Long foodCost = (Long) actionEvent.getComponent().getAttributes().get("foodCost");
		int foodQuantity = (Integer) actionEvent.getComponent().getAttributes().get("foodQuantity"); 
		
		List <Food> foodsToRemove = new ArrayList<Food>();
		Iterator <Food> iterator = selectedFoods.iterator();
		while (iterator.hasNext()) {
		   Food food = iterator.next();
		   if (food.getName().equalsIgnoreCase(foodName)) {
			   foodQuantity++;
//			   foodsToRemove.add(food);
		   } 
		}

		Food selectedFood = new Food();
		selectedFood.setName(foodName);
		selectedFood.setDescription(foodDescription);
		selectedFood.setCost(foodCost);
//		selectedFood.setQuantity(foodQuantity);
		selectedFoods.removeAll(foodsToRemove);
		selectedFoods.add(selectedFood);
		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedFood", selectedFoods);
	}
	
	public void cartBtnAction(ActionEvent actionEvent) {
		log.info("Cart selected");
		selectedFoods = (List <Food>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFood");
	}
	
	public void removeFoodAction(ActionEvent actionEvent) {
		log.info("Selected food removed");
		selectedFoods = (List <Food>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFood");
		String foodName = (String) actionEvent.getComponent().getAttributes().get("foodName");
		String foodDescription = (String) actionEvent.getComponent().getAttributes().get("foodDescription");
		Long foodCost = (Long) actionEvent.getComponent().getAttributes().get("foodCost");
		int foodQuantity = (Integer) actionEvent.getComponent().getAttributes().get("foodQuantity"); 
		
		Iterator <Food> iterator = selectedFoods.iterator();
		while (iterator.hasNext()) {
		   Food food = iterator.next();
		   if (food.getName().equalsIgnoreCase(foodName)) {
			   foodQuantity--; 
			   iterator.remove();
			   log.info("+++++++++++++++"+ food.getQuantity() + "////////////////" + foodQuantity);
		   } 
		}
		Food selectedFood = new Food();
		selectedFood.setName(foodName);
		selectedFood.setDescription(foodDescription);
		selectedFood.setCost(foodCost);
		selectedFood.setQuantity(foodQuantity);
		//selectedFoods.re
		selectedFoods.add(selectedFood);
//		selectedFoods.removeAll(remsoveUseless(foodName));
		if (foodQuantity <= 1) {
			Iterator <Food> iterator2 = selectedFoods.iterator();
			while (iterator2.hasNext()) {
			   Food food = iterator2.next();
			   if (food.getName().equalsIgnoreCase(foodName)) {
				   iterator2.remove();
			   } 
			}
		}
//		
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedFood", selectedFoods);
		log.info("Selected food removed");
	}
	
	public void addPurchase(ActionEvent actionEvent) {

		String userName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("app.user.name");
		selectedFoods = (List <Food>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFood");
		PurchaseJDBCDriver purchaseDriver = new PurchaseJDBCDriver();
		boolean isSuccessful = false;
				
		Iterator <Food> iterator = selectedFoods.iterator();
		while (iterator.hasNext()) {
		   Food food = iterator.next();
		   isSuccessful = purchaseDriver.addNewOrder(food, userName);
		}
		if(isSuccessful) {
			FacesContext.getCurrentInstance().addMessage("growlBuy", new FacesMessage("Successful order!") );
		}
	}
	
	public void addFood() {
		Food food = new Food(this.foodName, this.foodDescription, this.foodCost);
		FoodJDBCDriver foodJDBC = new FoodJDBCDriver();
		foodJDBC.addFood(food);	
	}
	
	public void deleteFood(ActionEvent actionEvent) {
		String foodName = (String) actionEvent.getComponent().getAttributes().get("foodName");
		FoodJDBCDriver foodJDBC = new FoodJDBCDriver();
		foodJDBC.deleteFood(foodName);
	}
}
