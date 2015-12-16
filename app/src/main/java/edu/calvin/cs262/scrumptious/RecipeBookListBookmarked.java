/**
 * RecipeBookListBookmarked.java
 *
 * Displays all bookmarked Recipes in alphabetical order in a list.  Uses RecipeAdapter to adapt
 * the Recipe array to a ListView
 *
 * DOES NOT CURRENTLY WORK
 *
 * Used tutorial on https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
package edu.calvin.cs262.scrumptious;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipeBookListBookmarked extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_book_list_bookmarked);

        // Change the title
        ActionBar ab = getActionBar();
        ab.setTitle("                                           ");

        // Access global arrays
        ArrayList<Recipe> arrayOfRecipes = (((Scrumptious)getApplicationContext()).arrayOfRecipes);
        ArrayList<Ingredient> arrayOfIngredients = (((Scrumptious)getApplicationContext()).arrayOfIngredients);



        // Create the adapter to convert the array to views
        RecipeAdapter adapter = new RecipeAdapter(this, arrayOfRecipes);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listViewAlphaItems);
        listView.setAdapter(adapter);

        //Listens for a click on a recipe in the listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Intent intent = new Intent(RecipeBookListBookmarked.this, DisplayRecipe.class);

                // Gets needed info from recipe and puts it in the intent
                Recipe recipe = (Recipe)adapter.getItemAtPosition(position);
                intent.putExtra("recipeName", recipe.getName());
                intent.putExtra("recipeInstructions", recipe.getInstructions());
                intent.putExtra("recipeServings", recipe.getServings());

                // Starts the intent
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_book_list_bookmarked, menu);
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



}
