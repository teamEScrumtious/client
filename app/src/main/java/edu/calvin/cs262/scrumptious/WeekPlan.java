package edu.calvin.cs262.scrumptious;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tjluce on 11/10/15.
 */
public class WeekPlan {
    // Data
    private Day Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;

    // Constructor
    public WeekPlan() {
    }

    // Getters and Setters
    public List<Day> getDayList() {
        List<Day> dayList = new ArrayList<Day>();
        dayList.add(Sunday);
        dayList.add(Monday);
        dayList.add(Tuesday);
        dayList.add(Wednesday);
        dayList.add(Thursday);
        dayList.add(Friday);
        dayList.add(Saturday);
        return dayList;
    }

    public Day getSunday() {
        return Sunday;
    }

    public void setSunday(Day sunday) {
        Sunday = sunday;
    }

    public Day getMonday() {
        return Monday;
    }

    public void setMonday(Day monday) {
        Monday = monday;
    }

    public Day getTuesday() {
        return Tuesday;
    }

    public void setTuesday(Day tueday) {
        Tuesday = tueday;
    }

    public Day getWednesday() {
        return Wednesday;
    }

    public void setWednesday(Day wednesday) {
        Wednesday = wednesday;
    }

    public Day getThursday() {
        return Thursday;
    }

    public void setThursday(Day thursday) {
        Thursday = thursday;
    }

    public Day getFriday() {
        return Friday;
    }

    public void setFriday(Day friday) {
        Friday = friday;
    }

    public Day getSaturday() {
        return Saturday;
    }

    public void setSaturday(Day saturday) {
        Saturday = saturday;
    }
}
