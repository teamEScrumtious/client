package edu.calvin.cs262.scrumptious;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.Console;
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
    public ArrayList<Ingredient> arrayofIngredients = new ArrayList<Ingredient>();
    public ArrayList<Recipe> arrayOfRecipes = new ArrayList<Recipe>();
    public ArrayList<IngredientQuantity> immortalityIngredients = new ArrayList<IngredientQuantity>();
    public ArrayList<IngredientQuantity> polyjuiceIngredients = new ArrayList<IngredientQuantity>();
    public WeekPlan weekPlan  = new WeekPlan();

    // Set up variables for accessing RESTful web service
    private static String SERVER_URI = "http://10.0.2.2:9998/scrumptious/";
    private static String RECIPES_URI = "recipes/";
    private String webResults = "";

    // This will receive result fired from async class of onPostExecute(result) method.
    // This executes after the main thread.
    public void processFinish(String output){

        webResults = output;
        Log.d(Scrumptious.class.getSimpleName(), "webResults: " + webResults);

        // Split the server data's individual lines into separate strings
        String[] splitWebResults = null;
        splitWebResults = webResults.split("\n");

        // Loop through each line of server data
        for(int i = 0; i < splitWebResults.length; i++) {
            String[] numberSplitWebResults = null;

            Log.d(Scrumptious.class.getSimpleName(), "splitWebResults: " + splitWebResults[i]);

            // Get only the number of the recipe (it should be at an index of 0
            // Note: Testing for numbers with more than 1 digits have not happened yet
            numberSplitWebResults = splitWebResults[i].split("\\D");
            Log.d(Scrumptious.class.getSimpleName(), "numberSplitWebResults: " + numberSplitWebResults[0]);
        }
    }

    public Scrumptious() {

        // Set up the thread that retreives data from the server
        MyAsyncTask asyncTask =new MyAsyncTask(SERVER_URI + RECIPES_URI);

        asyncTask.delegate = this;

        // Execute asyncTask and wait for it to return
        try {
            asyncTask.execute().get(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        // Add to the array of ingredients
        arrayofIngredients.add(new Ingredient("Salt", "Spices"));
        arrayofIngredients.add(new Ingredient("Human Souls", "Spices"));
        arrayofIngredients.add(new Ingredient("Eye of Newt", "Meats"));
        arrayofIngredients.add(new Ingredient("Fluxweed", "Spices"));
        arrayofIngredients.add(new Ingredient("Knotgrass", "Produce"));
        arrayofIngredients.add(new Ingredient("Lacewing Flies", "Meats"));
        arrayofIngredients.add(new Ingredient("Leeches", "Meats"));
        arrayofIngredients.add(new Ingredient("Horn of Bicorn", "Dairy"));
        arrayofIngredients.add(new Ingredient("Boomslang Skin", "Meats"));
        arrayofIngredients.add(new Ingredient("Human Hair", "Spices"));
        arrayofIngredients.add(new Ingredient("Bezoar", "Spices"));
        arrayofIngredients.add(new Ingredient("Mistletoe Berries", "Spices"));
        arrayofIngredients.add(new Ingredient("Unicorn Horn", "Spices"));
        arrayofIngredients.add(new Ingredient("Standard Ingredient", "Spices"));

        // Add to the array of recipes
        immortalityIngredients.add(new IngredientQuantity(arrayofIngredients.get(0), "tbsp", 3));
        immortalityIngredients.add(new IngredientQuantity(arrayofIngredients.get(1), "", 3));
        immortalityIngredients.add(new IngredientQuantity(arrayofIngredients.get(2), "cup", 3));
        polyjuiceIngredients.add(new IngredientQuantity(arrayofIngredients.get(3), "measures", 3));
        polyjuiceIngredients.add(new IngredientQuantity(arrayofIngredients.get(4), "bundles", 2));
        polyjuiceIngredients.add(new IngredientQuantity(arrayofIngredients.get(5), "cups", 3));
        polyjuiceIngredients.add(new IngredientQuantity(arrayofIngredients.get(6), "", 4));
        arrayOfRecipes.add(new Recipe("Immortality", "Throw it into a pot add some black magic and boom... Literally.", immortalityIngredients, true, 2));
        arrayOfRecipes.add(new Recipe("Polyjuice Potion", "1. Add the fluxweed to the cauldron \n2. Add the knot grass \n3. Stir 3 times clockwise \n4. Wave your wand then let the potion brew for 80 minutes \n5. Add the leeches \n6. Crush two cups of lacewing flies in a mortar then add \n7. Heat for 30 seconds on low heat.", polyjuiceIngredients, false, 3));

        // Create some test dishes
        Dish testDish1 = new Dish(arrayOfRecipes.get(0), 4);
        Dish testDish2 = new Dish(arrayOfRecipes.get(0), 2);
        Dish testDish3 = new Dish(arrayOfRecipes.get(0), 1);
        Dish testDish4 = new Dish(arrayOfRecipes.get(1), 8);
        Dish testDish5 = new Dish(arrayOfRecipes.get(1), 2);
        Dish testDish6 = new Dish(arrayOfRecipes.get(1), 5);

        // Create some test days
        Day testSunday = new Day(Calendar.DECEMBER, 13, 2015);
        testSunday.setBreakfast(testDish1);
        testSunday.setLunch(testDish2);
        testSunday.setDinner(testDish3);

        Day testMonday = new Day(Calendar.DECEMBER, 14, 2015);
        testMonday.setBreakfast(testDish4);
        testMonday.setLunch(testDish5);
        testMonday.setDinner(testDish6);

        Day testTuesday = new Day(Calendar.DECEMBER, 15, 2015);
        testTuesday.setBreakfast(testDish1);
        testTuesday.setLunch(testDish3);
        testTuesday.setDinner(testDish5);

        Day testWednesday = new Day(Calendar.DECEMBER, 16, 2015);
        testWednesday.setBreakfast(testDish2);
        testWednesday.setLunch(testDish4);
        testWednesday.setDinner(testDish6);

        Day testThursday = new Day(Calendar.DECEMBER, 17, 2015);
        testThursday.setBreakfast(testDish1);
        testThursday.setLunch(testDish5);
        testThursday.setDinner(testDish1);

        Day testFriday = new Day(Calendar.DECEMBER, 18, 2015);
        testFriday.setBreakfast(testDish4);
        testFriday.setLunch(testDish4);
        testFriday.setDinner(testDish4);

        Day testSaturday = new Day(Calendar.DECEMBER, 19, 2015);
        testSaturday.setBreakfast(testDish5);
        testSaturday.setLunch(testDish3);
        testSaturday.setDinner(testDish2);

        // Create a test week plan
        weekPlan.setSunday(testSunday);
        weekPlan.setMonday(testMonday);
        weekPlan.setTuesday(testTuesday);
        weekPlan.setWednesday(testWednesday);
        weekPlan.setThursday(testThursday);
        weekPlan.setFriday(testFriday);
        weekPlan.setSaturday(testSaturday);

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
