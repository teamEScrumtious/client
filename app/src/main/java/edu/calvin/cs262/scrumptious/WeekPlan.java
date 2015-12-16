/**
 * WeekPlan.java
 *
 * Created by tjluce on 11/10/15.
 *
 * Models a week plan.  The week plan uses a list of dishes and figures out what day it needs to put it in.
 * The week is modeled as a list of Day objects.
 */

package edu.calvin.cs262.scrumptious;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.List;

public class WeekPlan {

    // Data
    private ArrayList<Day> dayList = new ArrayList<Day>();

    // Constructor
    public WeekPlan(ArrayList<Dish> newDishes) {
        DateTime localDate = new DateTime();
        ArrayList<Dish> dishes = newDishes;

        // Add new Day objects for the next seven days to the object
        for(int i = 0; i < 7; i++) {
            dayList.add(new Day(localDate.getMonthOfYear(), localDate.getDayOfMonth(), localDate.getYear()));
            localDate = localDate.plusDays(1);
        }

        // Reset the calendar to today
        localDate = localDate.plusDays(-7);

        // Loop through each dish and put it in the correct day
        for(int i = 0; i < dishes.size(); i++) {
            DateTime dishDate = dishes.get(i).getDate();

            int dateDifference = Days.daysBetween(localDate.toLocalDate(), dishDate.toLocalDate()).getDays();
            // Only add the dish if it's within the current week...
            if (dateDifference >= 0 && dateDifference < 7) {
                dayList.get(dateDifference).addDish(dishes.get(i));
            }
        }
    }

    // Getters and Setters
    public List<Day> getDayList() {
        return dayList;
    }

    public Day getDay(int index) {
        if (index >= 0 && index < dayList.size()) {
            return dayList.get(index);
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
}
