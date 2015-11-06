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

        // Construct the array of ingredients
        ArrayList<Ingredient> arrayofIngredients = new ArrayList<Ingredient>();
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
        // Construct the array of recipes
        ArrayList<Recipe> arrayOfRecipes = new ArrayList<Recipe>();
        ArrayList<IngredientQuantity> immortalityIngredients = new ArrayList<IngredientQuantity>();
        ArrayList<IngredientQuantity> polyjuiceIngredients = new ArrayList<IngredientQuantity>();
        immortalityIngredients.add(new IngredientQuantity(arrayofIngredients.get(0), "tbsp", 3));
        immortalityIngredients.add(new IngredientQuantity(arrayofIngredients.get(1), "", 3));
        immortalityIngredients.add(new IngredientQuantity(arrayofIngredients.get(2), "cup", 3));
        polyjuiceIngredients.add(new IngredientQuantity(arrayofIngredients.get(3), "measures", 3));
        polyjuiceIngredients.add(new IngredientQuantity(arrayofIngredients.get(4), "bundles", 2));
        polyjuiceIngredients.add(new IngredientQuantity(arrayofIngredients.get(5), "cups", 3));
        polyjuiceIngredients.add(new IngredientQuantity(arrayofIngredients.get(6), "", 4));
        arrayOfRecipes.add(new Recipe("Immortality", "Throw it into a pot add some black magic and boom... Literally.", immortalityIngredients, true, 2));
        arrayOfRecipes.add(new Recipe("Polyjuice Potion", "Polyjuice Potion', '1. Add the fluxweed to the cauldron 2. Add the knto grass 3. Stir 3 times clockwise 4. Wave your wand then let the potion brew for 80 minutes 5. Add the leeches, 6. Crush two cups of lacewing flies in a mortar then add, 7. Heat for 30 seconds on low heat.", polyjuiceIngredients, false, 3));

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
