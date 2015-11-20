/**
 * ShoppingListMenu.java
 *
 * Displays shopping list, populated by recipies, via check boxes.
 *
 * [UNDER CONSTRUCTION]
 */

package edu.calvin.cs262.scrumptious;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingListMenu extends Activity {

    // Adapter Data
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<IngredientQuantity>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_menu);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // preparing list data
        prepareListData();

        // Creates the list adapater
        listAdapter = new ShoppingListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

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
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

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
        listDataChild = new HashMap<String, List<IngredientQuantity>>();
        WeekPlan weekPlan = (((Scrumptious)getApplicationContext()).weekPlan);

        // Data to be used for looping
        List<String> listOfTypes = new ArrayList<String>();
        List<Day> dayList = weekPlan.getDayList();
        List<Dish> dishList = null;
        List<IngredientQuantity> ingredientList = null;

        // Loop through every day and find every ingredient, and if it has a new type then add it to the header list
        for(int i = 0; i < dayList.size(); i++) {
            dishList = dayList.get(i).getDishList();
            // Looping through dishes in the current day
            for(int j = 0; j < dishList.size(); j++) {
                ingredientList = dishList.get(j).getRecipe().getIngredients();
                // Looping through ingredients in the current dish
                for(int k = 0; k < ingredientList.size(); k++) {
                    // If the type of the ingredient isn't in the header data list yet, add it in
                    if (!listDataHeader.contains(ingredientList.get(k).getIngredient().getType())) {
                        listDataHeader.add(ingredientList.get(k).getIngredient().getType());
                        // Also add in a new arraylist for the child data list to use (corresponds to the list just added for the header data list)
                        listDataChild.put(ingredientList.get(k).getIngredient().getType(), new ArrayList<IngredientQuantity>());
                    }
                    // Adds the ingredient to the appropriate list in the child data
                    // NOTE: We'll have to have an "if contains" statement here, and if it's true, merge the two quantities together
                    listDataChild.get(ingredientList.get(k).getIngredient().getType()).add(ingredientList.get(k));
                }
            }
        }
    }

}
