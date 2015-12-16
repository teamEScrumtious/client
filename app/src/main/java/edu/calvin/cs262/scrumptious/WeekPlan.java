package edu.calvin.cs262.scrumptious;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tjluce on 11/10/15.
 */
public class WeekPlan {
    // Data
    private ArrayList<Day> dayList = new ArrayList<Day>();
    private ArrayList<Dish> dishes;

    // Constructor
    public WeekPlan(ArrayList<Dish> newDishes) {
        DateTime localDate = new DateTime();
        dishes = newDishes;

        // Add new Day objects for the next seven days to the object
        for(int i = 0; i < 7; i++) {
            dayList.add(new Day(localDate.getMonthOfYear(), localDate.getDayOfMonth(), localDate.getYear()));
            localDate = localDate.plusDays(1);
        }

        // Reset the calendar to today
        localDate = localDate.plusDays(-7);

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
