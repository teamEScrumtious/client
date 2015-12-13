package edu.calvin.cs262.scrumptious;

import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    public Dish(Recipe newRecipe, int newServings, String date ) {
        recipe = newRecipe;
        servings = newServings;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(date);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
        }catch(Exception e){
            Log.d(Scrumptious.class.getSimpleName(), e.toString());
        }
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
