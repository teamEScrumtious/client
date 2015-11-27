package edu.calvin.cs262.scrumptious;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private List<Dish> dishes = new ArrayList<Dish>();  //To be used later, until it's made we have a function to return dishes as a list
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
    public List<Dish> getDishList() {
        List<Dish> dishList = new ArrayList<Dish>();
        dishList.add(breakfast);
        dishList.add(lunch);
        dishList.add(dinner);
        return dishList;
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    public void removeDish(int index) {
        if (index >= 0 && index < dishes.size()) {
            dishes.remove(index);
        }
    }

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
