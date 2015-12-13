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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class WeeklyPlanMenu extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Dish>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_plan_menu);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weekly_plan_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_settings:
//                // User chose the "Settings" item, show the app settings UI...
//                return true;

            case R.id.action_add:
                // User chose the "Add" action, let them add a new recipe
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Dish>>();
        WeekPlan weekPlan = (((Scrumptious)getApplicationContext()).weekPlan);
        List<Day> listOfDays = new ArrayList<Day>();
        listOfDays.add(weekPlan.getDay(0));
        listOfDays.add(weekPlan.getDay(1));
        listOfDays.add(weekPlan.getDay(2));
        listOfDays.add(weekPlan.getDay(3));
        listOfDays.add(weekPlan.getDay(4));
        listOfDays.add(weekPlan.getDay(5));
        listOfDays.add(weekPlan.getDay(6));

        // Adding child data
        for(int i = 0; i < listOfDays.size(); i++) {
            listDataHeader.add(listOfDays.get(i).date.toString().substring(0, 10));
        }

        // Adding child data
        List<Dish> sundayDishes = new ArrayList<Dish>();
        for(int i = 0; i < listOfDays.get(0).getDishList().size(); i++) {
            sundayDishes.add(listOfDays.get(0).getDishList().get(i));
        }

        List<Dish> mondayDishes = new ArrayList<Dish>();
        for(int i = 0; i < listOfDays.get(1).getDishList().size(); i++) {


            mondayDishes.add(listOfDays.get(1).getDishList().get(i));
        }

        List<Dish> tuesdayDishes = new ArrayList<Dish>();
        for(int i = 0; i < listOfDays.get(2).getDishList().size(); i++) {
            tuesdayDishes.add(listOfDays.get(2).getDishList().get(i));
        }

        List<Dish> wednesdayDishes = new ArrayList<Dish>();
        for(int i = 0; i < listOfDays.get(3).getDishList().size(); i++) {
            wednesdayDishes.add(listOfDays.get(3).getDishList().get(i));
        }

        List<Dish> thursdayDishes = new ArrayList<Dish>();
        for(int i = 0; i < listOfDays.get(4).getDishList().size(); i++) {
            thursdayDishes.add(listOfDays.get(4).getDishList().get(i));
        }

        List<Dish> fridayDishes = new ArrayList<Dish>();
        for(int i = 0; i < listOfDays.get(5).getDishList().size(); i++) {
            fridayDishes.add(listOfDays.get(5).getDishList().get(i));
        }

        List<Dish> saturdayDishes = new ArrayList<Dish>();
        for(int i = 0; i < listOfDays.get(6).getDishList().size(); i++) {
            saturdayDishes.add(listOfDays.get(6).getDishList().get(i));
        }



        listDataChild.put(listDataHeader.get(0), sundayDishes); // Header, Child data
        listDataChild.put(listDataHeader.get(1), mondayDishes); // Header, Child data
        listDataChild.put(listDataHeader.get(2), tuesdayDishes); // Header, Child data
        listDataChild.put(listDataHeader.get(3), wednesdayDishes); // Header, Child data
        listDataChild.put(listDataHeader.get(4), thursdayDishes); // Header, Child data
        listDataChild.put(listDataHeader.get(5), fridayDishes); // Header, Child data
        listDataChild.put(listDataHeader.get(6), saturdayDishes); // Header, Child data
    }

}
