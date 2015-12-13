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
    private Recipe recipe = null;
    private int id;
    private int servings;
    private Calendar date = Calendar.getInstance();

    // Constructor
    public Dish(Recipe newRecipe, int newID, int newServings, String newDate ) {
        this.recipe = newRecipe;
        this.id = newID;
        this.servings = newServings;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(newDate);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            this.date.setTimeInMillis(timestamp.getTime());
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
