/**
 * ShoppingListMenu.java
 *
 * Displays the shopping list populated by the weekly plan, with each ingredient under an expandable
 * list corresponding to its type and with all identical ingredients merged into one quantity
 */

package edu.calvin.cs262.scrumptious;

import android.app.ActionBar;
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
        // Change the title
        ActionBar ab = getActionBar();
        ab.setTitle("                                           ");
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
        WeekPlan weekPlan = (((Scrumptious) getApplicationContext()).weekPlan);

        // Data to be used for looping
        List<Day> dayList = new ArrayList<Day>(weekPlan.getDayList());
        List<Dish> dishList;
        List<IngredientQuantity> ingredientList;
        IngredientQuantity ingredientToAdd;

        // Loop through every day and find every ingredient, and if it has a new type then add it to the header list
        for (int i = 0; i < dayList.size(); i++) {
            dishList = new ArrayList<Dish>(dayList.get(i).getDishList());
            // Looping through dishes in the current day
            for (int j = 0; j < dishList.size(); j++) {
                ingredientList = new ArrayList<IngredientQuantity>(dishList.get(j).getRecipe().getIngredients());
                // Looping through ingredients in the current dish
                for (int k = 0; k < ingredientList.size(); k++) {
                    //if the ingredient type matches any current categories ("listDataHeader" or alternatively "listDataChild.containsKey")
                    if (listDataChild.containsKey(ingredientList.get(k).getIngredient().getType())) {
                        /** create temp list copy of listDataChild's appropriate list, which is the value to the key found above **/
                        List<IngredientQuantity> tempChildList = new ArrayList<IngredientQuantity>(listDataChild.get((ingredientList.get(k).getIngredient().getType())));
                        boolean searchSucceeded = false;
                        /** Search through temp list for matching name **/
                        for (int h = 0; h < tempChildList.size(); h++) {
                            if (tempChildList.get(h).getIngredient().getName().equals(ingredientList.get(k).getIngredient().getName())) {
                                /** name matched, now combine ingredient quantities **/
                                tempChildList.get(h).setQuantity(tempChildList.get(h).getQuantity() + ingredientList.get(k).getQuantity());
                                listDataChild.remove(ingredientList.get(k).getIngredient().getType());
                                listDataChild.put(ingredientList.get(k).getIngredient().getType(), tempChildList);
                                searchSucceeded = true;
                                break;
                            }
                        }
                        /** if the search didn't find a matching name, searchSucceeded will still be false **/
                        if (!searchSucceeded) {
                            //The category existed but there were no matching ingredients by that name, so make a new one.
                            ingredientToAdd = ingredientList.get(k);
                            listDataChild.get(ingredientList.get(k).getIngredient().getType()).add(new IngredientQuantity(ingredientToAdd.getIngredient(), ingredientToAdd.getId(), ingredientToAdd.getUnit(), ingredientToAdd.getQuantity()));
                        }
                    } else {
                        //No category matches the current ingredient type, so make a new category and add the ingredient to it.
                        listDataHeader.add(ingredientList.get(k).getIngredient().getType());
                        // Also add in a new arraylist for the child data list to use (corresponds to the list just added for the header data list)
                        listDataChild.put(ingredientList.get(k).getIngredient().getType(), new ArrayList<IngredientQuantity>());
                        ingredientToAdd = ingredientList.get(k);
                        listDataChild.get(ingredientList.get(k).getIngredient().getType()).add(new IngredientQuantity(ingredientToAdd.getIngredient(), ingredientToAdd.getId(), ingredientToAdd.getUnit(), ingredientToAdd.getQuantity()));
                    }

                }
            }
        }
    }

}