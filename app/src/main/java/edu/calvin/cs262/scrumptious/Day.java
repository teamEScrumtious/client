/**
 * Day.java
 *
 * Created by tjluce on 11/10/15.
 *
 * Models a day.  A day will contain multiple recipes.  I'm unsure whether we'll be organizing them
 * by meal, time of day, or some other method, so I'll do meal right now.
 */

package edu.calvin.cs262.scrumptious;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class Day {

    // Data
    private List<Dish> dishes = new ArrayList<Dish>();
    DateTime date; //should be made private after functions are added for getting full dates, and what day of the week it is

    // Constructor
    // Use Calendar.MONTH for the month
    public Day(int month, int day, int year) {
        date = new DateTime(year, month, day, 0, 0);
    }

    // Getters and Setters
    public List<Dish> getDishList() {
        return dishes;
    }

    public void addDish(Dish dish) {
        dishes.add(dish);
    }

    public void removeDish(int index) {
        if (index >= 0 && index < dishes.size()) {
            dishes.remove(index);
        }
    }
}
