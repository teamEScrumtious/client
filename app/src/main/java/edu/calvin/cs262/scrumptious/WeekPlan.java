package edu.calvin.cs262.scrumptious;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by tjluce on 11/10/15.
 */
public class WeekPlan {
    // Data
    private Day Day1, Day2, Day3, Day4, Day5, Day6, Day7;
    private ArrayList<Day> dayList = new ArrayList<Day>();
    //private ArrayList<Dish> dishes = new ArrayList<Dish>();

    // Constructor
    public WeekPlan(ArrayList<Dish> dishes) {
        Calendar cal = Calendar.getInstance();

        // Add new Day objects for the next seven days to the object
        for(int i = 0; i < 7; i++) {
            dayList.add(new Day(cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR)));
            cal.add(Calendar.DATE, 1); //Advance one day forward
        }

        // Reset the calendar to today
        cal.add(Calendar.DATE, -7);

        for(int i = 0; i < dishes.size(); i++) {
            Calendar dishDate = dishes.get(i).getDate();

            // Set every value not necessary for calculating dates to 0
            dishDate.set(Calendar.MILLISECOND, 0);
            dishDate.set(Calendar.SECOND, 0);
            dishDate.set(Calendar.MINUTE, 0);
            dishDate.set(Calendar.HOUR, 0);
            cal.set(Calendar.MILLISECOND, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.HOUR, 0);

            int dateDifference = dishDate.compareTo(cal);
            dayList.get(dateDifference).addDish(dishes.get(i));
        }
    }

    // Getters and Setters
    public List<Day> getDayList() {
//        List<Day> dayList = new ArrayList<Day>();
//        dayList.add(Day1);
//        dayList.add(Day2);
//        dayList.add(Day3);
//        dayList.add(Day4);
//        dayList.add(Day5);
//        dayList.add(Day6);
//        dayList.add(Day7);
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
