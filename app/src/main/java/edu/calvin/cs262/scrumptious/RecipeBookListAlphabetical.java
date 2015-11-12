/**
 * RecipeBookListAlphabetical.java
 *
 * display recipies alphabetically
 *
 * Used tutorial on https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 *
 * [UNDER CONSTRUCTION]
 */
package edu.calvin.cs262.scrumptious;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class RecipeBookListAlphabetical extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_book_list_alphabetical);

        // Access global arrays
        ArrayList<Recipe> arrayOfRecipes = (((Scrumptious)getApplicationContext()).arrayOfRecipes);
        ArrayList<Ingredient> arrayOfIngredients = (((Scrumptious)getApplicationContext()).arrayofIngredients);

        // Create the adapter to convert the array to views
        RecipeAdapter adapter = new RecipeAdapter(this, arrayOfRecipes);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listViewAlphaItems);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_book_list_alphabetical, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
