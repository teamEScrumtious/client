package edu.calvin.cs262.scrumptious;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tjluce on 11/10/15.
 * Models a dish (or a meal, if you prefer).
 */
public class Dish {

    // Data
    Recipe recipe = null;
    int servings;
    Calendar date = Calendar.getInstance();

    // Constructor
    public Dish(Recipe newRecipe, int newServings, String date) {
        recipe = newRecipe;
        servings = newServings;
        Timestamp timestamp = new Timestamp(date);
    }

    // Getter and Setters
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }
}
