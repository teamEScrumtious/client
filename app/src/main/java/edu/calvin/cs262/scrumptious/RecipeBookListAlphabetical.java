/**
 * RecipeBookListAlphabetical.java
 *
 * Displays all available Recipes in alphabetical order in a list.  Uses RecipeAdapter to adapt
 * the Recipe array to a ListView
 *
 * Used tutorial on https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
package edu.calvin.cs262.scrumptious;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RecipeBookListAlphabetical extends Activity implements AsyncResponse<String> {

    // Set up variables for accessing RESTful web service
    private static String SERVER_URI = "http://10.0.2.2:9998/scrumptious/";
    private static String DATA_URI = "recipes/";
    private String webResults = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_book_list_alphabetical);

        // Start the async thread to connect to the server and get the recipes
        MyAsyncTask asyncTask = new MyAsyncTask(SERVER_URI + DATA_URI);

        asyncTask.delegate = this;

        // Execute asyncTask and wait for it to return
        try {
            asyncTask.execute().get(); // Changes this to no timer?
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_book_list_alphabetical, menu);
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

    public void processFinish(String output) {
        webResults = output;
        Log.d(Scrumptious.class.getSimpleName(), "webResults: " + webResults);

        // Split the server data's individual lines into separate strings
        String[] splitWebResults = null;
        splitWebResults = webResults.split("\n");



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
                Intent intent = new Intent(RecipeBookListAlphabetical.this, DisplayRecipe.class);

                // Gets needed info from recipe and puts it in the intent
                Recipe recipe = (Recipe) adapter.getItemAtPosition(position);
                intent.putExtra("recipeName", recipe.getName());
                intent.putExtra("recipeInstructions", recipe.getInstructions());
                intent.putExtra("recipeServings", recipe.getServings());

                // Starts the intent
                startActivity(intent);
            }
        });
    }
}
