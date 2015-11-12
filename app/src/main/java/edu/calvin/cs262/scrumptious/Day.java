package edu.calvin.cs262.scrumptious;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tjluce on 11/10/15.
 * Models a day.  A day will contain multiple recipes.  I'm unsure whether we'll be organizing them
 * by meal, time of day, or some other method, so I'll do meal right now.
 */
public class Day {

    // Data
    private Dish breakfast = null;
    private Dish lunch = null;
    private Dish dinner = null;
    Date date; //should be made private after functions are added for getting full dates, and what day of the week it is

    // Constructor
    // Use Calendar.MONTH for the month
    public Day(int month, int day, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        date = cal.getTime();
    }

    // Getters and Setters
    public Dish getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Dish breakfast) {
        this.breakfast = breakfast;
    }

    public Dish getLunch() {
        return lunch;
    }

    public void setLunch(Dish lunch) {
        this.lunch = lunch;
    }

    public Dish getDinner() {
        return dinner;
    }

    public void setDinner(Dish dinner) {
        this.dinner = dinner;
    }
}
