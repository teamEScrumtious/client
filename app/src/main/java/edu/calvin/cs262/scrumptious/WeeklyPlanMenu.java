/**
 * WeeklyPlanMenu.java
 *
 * Display weekly recipies.. possibly by days or other time periods..
 *
 * [UNDER CONSTRUCTION]
 *
 * Adapted code from http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 */

package edu.calvin.cs262.scrumptious;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class WeeklyPlanMenu extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Dish>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_plan_list_layout);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // preparing list data
        prepareListData();

        listAdapter = new WeeklyPlanAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Dish>>();
        WeekPlan weekPlan = (((Scrumptious)getApplicationContext()).weekPlan);
        List<Day> listOfDays = new ArrayList<Day>();
        listOfDays.add(weekPlan.getSunday());
        listOfDays.add(weekPlan.getMonday());
        listOfDays.add(weekPlan.getTuesday());
        listOfDays.add(weekPlan.getWednesday());
        listOfDays.add(weekPlan.getThursday());
        listOfDays.add(weekPlan.getFriday());
        listOfDays.add(weekPlan.getSaturday());

        // Adding child data
        for(int i = 0; i < listOfDays.size(); i++) {
            listDataHeader.add(listOfDays.get(i).date.toString().substring(0, 10));
        }

        // Adding child data
        List<Dish> sundayDishes = new ArrayList<Dish>();
        sundayDishes.add(listOfDays.get(0).getBreakfast());
        sundayDishes.add(listOfDays.get(0).getLunch());
        sundayDishes.add(listOfDays.get(0).getDinner());

        List<Dish> mondayDishes = new ArrayList<Dish>();
        mondayDishes.add(listOfDays.get(1).getBreakfast());
        mondayDishes.add(listOfDays.get(1).getLunch());
        mondayDishes.add(listOfDays.get(1).getDinner());

        List<Dish> tuesdayDishes = new ArrayList<Dish>();
        tuesdayDishes.add(listOfDays.get(2).getBreakfast());
        tuesdayDishes.add(listOfDays.get(2).getLunch());
        tuesdayDishes.add(listOfDays.get(2).getDinner());

        List<Dish> wednesdayDishes = new ArrayList<Dish>();
        wednesdayDishes.add(listOfDays.get(3).getBreakfast());
        wednesdayDishes.add(listOfDays.get(3).getLunch());
        wednesdayDishes.add(listOfDays.get(3).getDinner());

        List<Dish> thursdayDishes = new ArrayList<Dish>();
        thursdayDishes.add(listOfDays.get(4).getBreakfast());
        thursdayDishes.add(listOfDays.get(4).getLunch());
        thursdayDishes.add(listOfDays.get(4).getDinner());

        List<Dish> fridayDishes = new ArrayList<Dish>();
        fridayDishes.add(listOfDays.get(5).getBreakfast());
        fridayDishes.add(listOfDays.get(5).getLunch());
        fridayDishes.add(listOfDays.get(5).getDinner());

        List<Dish> saturdayDishes = new ArrayList<Dish>();
        saturdayDishes.add(listOfDays.get(6).getBreakfast());
        saturdayDishes.add(listOfDays.get(6).getLunch());
        saturdayDishes.add(listOfDays.get(6).getDinner());



        listDataChild.put(listDataHeader.get(0), sundayDishes); // Header, Child data
        listDataChild.put(listDataHeader.get(1), tuesdayDishes); // Header, Child data
        listDataChild.put(listDataHeader.get(2), wednesdayDishes); // Header, Child data
        listDataChild.put(listDataHeader.get(3), wednesdayDishes); // Header, Child data
        listDataChild.put(listDataHeader.get(4), thursdayDishes); // Header, Child data
        listDataChild.put(listDataHeader.get(5), fridayDishes); // Header, Child data
        listDataChild.put(listDataHeader.get(6), saturdayDishes); // Header, Child data
    }

}
