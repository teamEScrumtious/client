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

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class WeeklyPlanMenu extends Activity implements AsyncResponse<String> {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<Dish>> listDataChild;

    // Set up variables for accessing RESTful web service
    private static String SERVER_URI = "http://10.0.2.2:9998/scrumptious/";
    private static String DATA_URI = "recipes/weekplan/";
    private String webResults = "";
    private WeekPlan weekPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_plan_menu);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        weekPlan = (((Scrumptious)getApplicationContext()).weekPlan);

        // Recalculate week plan
        // Set up the thread that retreives data from the server
        MyAsyncTask asyncTask = new MyAsyncTask(SERVER_URI + DATA_URI);

        asyncTask.delegate = this;

        // Execute asyncTask and wait for it to return
        asyncTask.execute();
        //((Scrumptious) getApplicationContext()).calculateWeekPlan();

        // Change the title
        ActionBar ab = getActionBar();
        ab.setTitle("                                           ");

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

//        // preparing list data
//        prepareListData();
//
//        listAdapter = new WeeklyPlanAdapter(this, listDataHeader, listDataChild);
//
//        // setting list adapter
//        expListView.setAdapter(listAdapter);
//
//        // Listview Group click listener
//        expListView.setOnGroupClickListener(new OnGroupClickListener() {
//
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//                // Toast.makeText(getApplicationContext(),
//                // "Group Clicked " + listDataHeader.get(groupPosition),
//                // Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//
//        // Listview Group expanded listener
//        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
////                Toast.makeText(getApplicationContext(),
////                        listDataHeader.get(groupPosition) + " Expanded",
////                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // Listview Group collasped listener
//        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
////                Toast.makeText(getApplicationContext(),
////                        listDataHeader.get(groupPosition) + " Collapsed",
////                        Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        // Listview on child click listener
//        expListView.setOnChildClickListener(new OnChildClickListener() {
//
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                // TODO Auto-generated method stub
////                Toast.makeText(
////                        getApplicationContext(),
////                        listDataHeader.get(groupPosition)
////                                + " : "
////                                + listDataChild.get(
////                                listDataHeader.get(groupPosition)).get(
////                                childPosition), Toast.LENGTH_SHORT)
////                        .show();
//                return false;
//            }
//        });
    }

    public void processFinish(String output) {

        webResults = output;
        Log.d(Scrumptious.class.getSimpleName(), "webResults: " + webResults);

        // Split the server data's individual lines into separate strings
        String[] splitWebResults = null;
        splitWebResults = webResults.split("\n");

        // Create new variables to be used for making dishes
        ArrayList<IngredientQuantity> arrayOfIngredientQuantities = new ArrayList<IngredientQuantity>();
        ArrayList<Dish> arrayOfDishes = new ArrayList<Dish>();

        boolean skipLine = false;
        boolean dishRecipeRead = false;

        int dishID = -1;
        int dishServings = -1;
        String dishTimestamp = null;
        int recipeID = -1;
        String recipeName = null;
        int recipeServings = -1;
        String recipeInstructions = null;
        String note = null;
        boolean recipeBookmarked = false;

        int ingredientID = -1;
        String ingredientName = null;
        String ingredientType = null;
        int recipeIngredientID = -1;
        String recipeIngredientUnit = null;
        int recipeIngredientQuantity = -1;

        // Loop through each line of server data
        for(int i = 0; i < splitWebResults.length; i++) {

            // Skip line if it's an ampersand
            if (skipLine) {
                skipLine = false;
            } else {
                // Check if still reading in inital recipe data
                if (!dishRecipeRead) {
                    // If still reading, check which one to read in next and do it
                    if (dishID == -1) {
                        dishID = Integer.valueOf(splitWebResults[i]);
                    } else if (dishServings == -1) {
                        dishServings = Integer.valueOf(splitWebResults[i]);
                    } else if (dishTimestamp == null) {
                        dishTimestamp = splitWebResults[i];
                    } else if (recipeID == -1) {
                        recipeID = Integer.valueOf(splitWebResults[i]);
                    } else if (recipeName == null) {
                        recipeName = splitWebResults[i];
                    } else if (recipeServings == -1) {
                        recipeServings = Integer.valueOf(splitWebResults[i]);
                    } else if (recipeInstructions == null) {
                        recipeInstructions = splitWebResults[i];
                    } else if (note == null) {
                        note = splitWebResults[i];
                    } else {
                        recipeBookmarked = Boolean.valueOf(splitWebResults[i]);
                        dishRecipeRead = true;
                    }
                    // Read in a new ingredient
                } else {
                    if (ingredientID == -1) {
                        ingredientID = Integer.valueOf(splitWebResults[i]);
                    } else if (ingredientName == null) {
                        ingredientName = splitWebResults[i];
                    } else if (ingredientType == null) {
                        ingredientType = splitWebResults[i];
                    } else if (recipeIngredientID == -1) {
                        recipeIngredientID = Integer.valueOf(splitWebResults[i]);
                    } else if (recipeIngredientUnit == null) {
                        recipeIngredientUnit = splitWebResults[i];
                    } else if (recipeIngredientQuantity == -1) {
                        recipeIngredientQuantity = Integer.valueOf(splitWebResults[i]);

                        // Add the new ingredient
                        arrayOfIngredientQuantities.add(new IngredientQuantity(new Ingredient(ingredientName, ingredientID, ingredientType), recipeIngredientID, recipeIngredientUnit, recipeIngredientQuantity));

                        // Reset the recipe variables
                        ingredientID = -1;
                        ingredientName = null;
                        ingredientType = null;
                        recipeIngredientID = -1;
                        recipeIngredientUnit = null;
                        recipeIngredientQuantity = -1;

                        //Check if next line is the end of the ingredients.  If so, add the dish and then reset variables for a new dish
                        if (i == splitWebResults.length - 1 || splitWebResults[i + 1].equals("&")) {
                            // Adding dish
                            arrayOfDishes.add(new Dish(new Recipe(recipeName, recipeID, recipeInstructions, arrayOfIngredientQuantities, recipeBookmarked, recipeServings, note), dishID, dishServings, dishTimestamp));

                            // Resetting variables
                            dishID = -1;
                            dishServings = -1;
                            dishTimestamp = null;
                            recipeID = -1;
                            recipeName = null;
                            recipeServings = -1;
                            recipeInstructions = null;
                            note = null;
                            recipeBookmarked = false;
                            dishRecipeRead = false;

                            skipLine = true;
                        }
                    }
                }

                // This stuff is unneccessary for now, possibly ever

//                String[] numberSplitWebResults = null;
//
//                Log.d(Scrumptious.class.getSimpleName(), "splitWebResults: " + splitWebResults[i]);
//
//                // Get only the number of the recipe (it should be at an index of 0
//                // Note: Testing for numbers with more than 1 digits have not happened yet
//                numberSplitWebResults = splitWebResults[i].split("\\D");
//                //Log.d(Scrumptious.class.getSimpleName(), "numberSplitWebResults: " + numberSplitWebResults[0]);
            }
        }

        //Create the Week Plan
        weekPlan = new WeekPlan(arrayOfDishes);
        (((Scrumptious)getApplicationContext()).weekPlan) = weekPlan;

        // Set up the list

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        prepareListData();

        listAdapter = new WeeklyPlanAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
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
