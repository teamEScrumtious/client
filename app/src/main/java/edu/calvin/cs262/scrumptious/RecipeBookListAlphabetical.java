/**
 * RecipeBookListAlphabetical.java
 *
 * Displays all available Recipes in alphabetical order in a list.  Uses RecipeAdapter to adapt
 * the Recipe array to a ListView
 *
 * Used tutorial on https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
package edu.calvin.cs262.scrumptious;

import android.app.ActionBar;
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
    private String[] splitWebResults = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_book_list_alphabetical);

        // Set up the async thread to connect to the server and get the recipes
        MyAsyncTask asyncTask = new MyAsyncTask(SERVER_URI + DATA_URI);
        asyncTask.delegate = this;

        // Change the title
        ActionBar ab = getActionBar();
        ab.setTitle("                                           ");

        // Execute asyncTask
        asyncTask.execute();
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
        //String[] splitWebResults = null;
        splitWebResults = webResults.split("\n");

        // Get global array for recipes after resettting it (to prevent duplicate recipes)
        (((Scrumptious) getApplicationContext()).arrayOfRecipes) = new ArrayList<Recipe>();
        ArrayList<Recipe> arrayOfRecipes = (((Scrumptious) getApplicationContext()).arrayOfRecipes);

        // Create new variables to be used for making recipes
        ArrayList<IngredientQuantity> arrayOfIngredientQuantities = new ArrayList<IngredientQuantity>();

        boolean skipLine = false;
        boolean recipeRead = false;

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
        for (int i = 0; i < splitWebResults.length; i++) {

            // Skip line if it's an ampersand
            if (skipLine) {
                skipLine = false;
            } else {
                // Check if still reading in inital recipe data
                if (!recipeRead) {
                    // If still reading, check which one to read in next and do it
                    if (recipeID == -1) {
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
                        recipeRead = true;
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
                            arrayOfRecipes.add(new Recipe(recipeName, recipeID, recipeInstructions, arrayOfIngredientQuantities, recipeBookmarked, recipeServings, note));

                            // Resetting variables
                            recipeID = -1;
                            recipeName = null;
                            recipeServings = -1;
                            recipeInstructions = null;
                            note = null;
                            recipeBookmarked = false;

                            recipeRead = false;
                            skipLine = true;
                        }
                    }
                }
            }
        }


        // Access global arrays
        ArrayList<Ingredient> arrayOfIngredients = (((Scrumptious) getApplicationContext()).arrayOfIngredients);

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
                intent.putExtra("recipeID", recipe.getId());
                intent.putExtra("recipeName", recipe.getName());
                intent.putExtra("recipeServings", recipe.getServings());
                intent.putExtra("recipeInstructions", recipe.getInstructions());
                intent.putExtra("recipeNote", recipe.getNote());
                intent.putExtra("recipeBookmarked", recipe.isBookmarked());
                //intent.putExtra("recipeIngredients", recipe.getIngredients());

                // Starts the intent
                startActivity(intent);
            }
        });
    }
}
