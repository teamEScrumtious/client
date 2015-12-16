package edu.calvin.cs262.scrumptious;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by tjluce on 11/9/15.
 */
public class Scrumptious extends Application implements AsyncResponse<String> {

    // Create the global arrays of objects
    public ArrayList<Ingredient> arrayOfIngredients = new ArrayList<Ingredient>();
    public ArrayList<Recipe> arrayOfRecipes = new ArrayList<Recipe>();
    //public ArrayList<IngredientQuantity> immortalityIngredients = new ArrayList<IngredientQuantity>();
    //public ArrayList<IngredientQuantity> polyjuiceIngredients = new ArrayList<IngredientQuantity>();
    public WeekPlan weekPlan;

    // Set up variables for accessing RESTful web service
    private static String SERVER_URI = "http://10.0.2.2:9998/scrumptious/";
    private static String DATA_URI = "recipes/weekplan/";
    private String webResults = "";

    // This will receive result fired from async class of onPostExecute(result) method.
    // This executes after the main thread.
    public void processFinish(String output) {
//
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
            }
        }

        //Create the Week Plan
        weekPlan = new WeekPlan(arrayOfDishes);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Start Joda Time library
        JodaTimeAndroid.init(this);
    }

    public Scrumptious() {

        // Set up the thread that retreives data from the server
        MyAsyncTask asyncTask = new MyAsyncTask(SERVER_URI + DATA_URI);

        asyncTask.delegate = this;

        // Execute asyncTask and wait for it to return
        asyncTask.execute();

    }

}
