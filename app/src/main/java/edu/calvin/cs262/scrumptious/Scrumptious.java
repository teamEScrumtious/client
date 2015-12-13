package edu.calvin.cs262.scrumptious;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
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
    public void processFinish(String output){

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

                String[] numberSplitWebResults = null;

                Log.d(Scrumptious.class.getSimpleName(), "splitWebResults: " + splitWebResults[i]);

                // Get only the number of the recipe (it should be at an index of 0
                // Note: Testing for numbers with more than 1 digits have not happened yet
                numberSplitWebResults = splitWebResults[i].split("\\D");
                //Log.d(Scrumptious.class.getSimpleName(), "numberSplitWebResults: " + numberSplitWebResults[0]);
            }
        }

        //Create the Week Plan
        weekPlan = new WeekPlan(arrayOfDishes);
    }

    public Scrumptious() {

        // Set up the thread that retreives data from the server
        MyAsyncTask asyncTask =new MyAsyncTask(SERVER_URI + DATA_URI);

        asyncTask.delegate = this;

        // Execute asyncTask and wait for it to return
        try {
            asyncTask.execute().get(5000, TimeUnit.MILLISECONDS); // Changes this to no timer?
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

//        // Add to the array of ingredients
//        arrayOfIngredients.add(new Ingredient("Salt", "Spices"));
//        arrayOfIngredients.add(new Ingredient("Human Souls", "Spices"));
//        arrayOfIngredients.add(new Ingredient("Eye of Newt", "Meats"));
//        arrayOfIngredients.add(new Ingredient("Fluxweed", "Spices"));
//        arrayOfIngredients.add(new Ingredient("Knotgrass", "Produce"));
//        arrayOfIngredients.add(new Ingredient("Lacewing Flies", "Meats"));
//        arrayOfIngredients.add(new Ingredient("Leeches", "Meats"));
//        arrayOfIngredients.add(new Ingredient("Horn of Bicorn", "Dairy"));
//        arrayOfIngredients.add(new Ingredient("Boomslang Skin", "Meats"));
//        arrayOfIngredients.add(new Ingredient("Human Hair", "Spices"));
//        arrayOfIngredients.add(new Ingredient("Bezoar", "Spices"));
//        arrayOfIngredients.add(new Ingredient("Mistletoe Berries", "Spices"));
//        arrayOfIngredients.add(new Ingredient("Unicorn Horn", "Spices"));
//        arrayOfIngredients.add(new Ingredient("Standard Ingredient", "Spices"));
//
//        // Add to the array of recipes
//        immortalityIngredients.add(new IngredientQuantity(arrayOfIngredients.get(0), "tbsp", 3));
//        immortalityIngredients.add(new IngredientQuantity(arrayOfIngredients.get(1), "", 3));
//        immortalityIngredients.add(new IngredientQuantity(arrayOfIngredients.get(2), "cup", 3));
//        polyjuiceIngredients.add(new IngredientQuantity(arrayOfIngredients.get(3), "measures", 3));
//        polyjuiceIngredients.add(new IngredientQuantity(arrayOfIngredients.get(4), "bundles", 2));
//        polyjuiceIngredients.add(new IngredientQuantity(arrayOfIngredients.get(5), "cups", 3));
//        polyjuiceIngredients.add(new IngredientQuantity(arrayOfIngredients.get(6), "", 4));
//        arrayOfRecipes.add(new Recipe("Immortality", "Throw it into a pot add some black magic and boom... Literally.", immortalityIngredients, true, 2));
//        arrayOfRecipes.add(new Recipe("Polyjuice Potion", "1. Add the fluxweed to the cauldron 2. Add the knot grass 3. Stir 3 times clockwise 4. Wave your wand then let the potion brew for 80 minutes 5. Add the leeches, 6. Crush two cups of lacewing flies in a mortar then add, 7. Heat for 30 seconds on low heat.", polyjuiceIngredients, false, 3));
//
//        // Create some test dishes
//        Dish testDish1 = new Dish(arrayOfRecipes.get(0), 4, "2015-12-15 08:00:00.0");
//        Dish testDish2 = new Dish(arrayOfRecipes.get(0), 2, "2015-12-16 08:00:00.0");
//        Dish testDish3 = new Dish(arrayOfRecipes.get(0), 1, "2015-12-17 08:00:00.0");
//        Dish testDish4 = new Dish(arrayOfRecipes.get(1), 8, "2015-12-18 08:00:00.0");
//        Dish testDish5 = new Dish(arrayOfRecipes.get(1), 2, "2015-12-19 08:00:00.0");
//        Dish testDish6 = new Dish(arrayOfRecipes.get(1), 5, "2015-12-20 08:00:00.0");
//
//        // Create some test days
//        Day testSunday = new Day(Calendar.NOVEMBER, 15, 2015);
//        testSunday.setBreakfast(testDish1);
//        testSunday.setLunch(testDish2);
//        testSunday.setDinner(testDish3);
//
//        Day testMonday = new Day(Calendar.NOVEMBER, 16, 2015);
//        testMonday.setBreakfast(testDish4);
//        testMonday.setLunch(testDish5);
//        testMonday.setDinner(testDish6);
//
//        Day testTuesday = new Day(Calendar.NOVEMBER, 17, 2015);
//        testTuesday.setBreakfast(testDish1);
//        testTuesday.setLunch(testDish3);
//        testTuesday.setDinner(testDish5);
//
//        Day testWednesday = new Day(Calendar.NOVEMBER, 18, 2015);
//        testWednesday.setBreakfast(testDish2);
//        testWednesday.setLunch(testDish4);
//        testWednesday.setDinner(testDish6);
//
//        Day testThursday = new Day(Calendar.NOVEMBER, 19, 2015);
//        testThursday.setBreakfast(testDish1);
//        testThursday.setLunch(testDish5);
//        testThursday.setDinner(testDish1);
//
//        Day testFriday = new Day(Calendar.NOVEMBER, 20, 2015);
//        testFriday.setBreakfast(testDish4);
//        testFriday.setLunch(testDish4);
//        testFriday.setDinner(testDish4);
//
//        Day testSaturday = new Day(Calendar.NOVEMBER, 21, 2015);
//        testSaturday.setBreakfast(testDish5);
//        testSaturday.setLunch(testDish3);
//        testSaturday.setDinner(testDish2);
//
//        // Create a test week plan
//        weekPlan.setSunday(testSunday);
//        weekPlan.setMonday(testMonday);
//        weekPlan.setTuesday(testTuesday);
//        weekPlan.setWednesday(testWednesday);
//        weekPlan.setThursday(testThursday);
//        weekPlan.setFriday(testFriday);
//        weekPlan.setSaturday(testSaturday);

    }

    // Uses http://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
    private class MyAsyncTask extends AsyncTask<Void, Void, String> {

        public AsyncResponse delegate=null;
        private String URI = "";

        public MyAsyncTask(String newURI) {
            URI = newURI;
        }

        /**
         * This method extracts text from the HTTP response entity.
         *
         * @param entity
         * @return
         * @throws IllegalStateException
         * @throws IOException
         */
        protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n = 1;
            while (n > 0) {
                byte[] b = new byte[4096];
                n = in.read(b);
                if (n > 0) out.append(new String(b, 0, n));
            }
            return out.toString();
        }

        /**
         * This method issues the HTTP GET request.
         *
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(URI);
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                text = getASCIIContentFromEntity(entity);
                Log.d(Scrumptious.class.getSimpleName(), "Received data from server at address " + URI);
                Log.d(Scrumptious.class.getSimpleName(), "Data received is:\n" + text);
            } catch (Exception e) {
                Log.d(Scrumptious.class.getSimpleName(), "Failed to retrieve data from the server.");
                return e.getLocalizedMessage();
            }
            return text;
        }

        /**
         * The method takes the results of the request, when they arrive, and updates the interface.
         *
         * @param results
         */
        @Override
        protected void onPostExecute(String results) {
            super.onPostExecute(results);
            if (results != null) {
                delegate.processFinish(results);
            } else {
                delegate.processFinish("Nothing found.");
            }
        }

    }
}
