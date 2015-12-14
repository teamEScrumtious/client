package edu.calvin.cs262.scrumptious;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
    //private Calendar date = Calendar.getInstance();
    private DateTime date;

    // Constructor
    public Dish(Recipe newRecipe, int newID, int newServings, String newDate ) {
        this.recipe = newRecipe;
        this.id = newID;
        this.servings = newServings;
        try{
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
            date = formatter.parseDateTime(newDate);
            //Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            //this.date = new DateTime(parsedDate.getYear(), parsedDate.getMonth(), parsedDate.getDay(), parsedDate.getHours(), parsedDate.getMinutes());
            //this.date.setTimeInMillis(timestamp.getTime());
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

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }
}
