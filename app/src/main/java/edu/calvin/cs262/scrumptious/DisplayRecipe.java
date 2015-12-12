package edu.calvin.cs262.scrumptious;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by tjluce on 11/23/15.
 *
 * NOTE: Do we want the ability to bookmark the recipe from in here too?
 * Yes, I added a checkbox so can you make it bookmark a recipe? - Lia
 */
public class DisplayRecipe extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_recipe);

        // Get the intent and get the recipe data from the intent's extras
        Intent intent = getIntent();
        String recipeName = intent.getStringExtra("recipeName");
        String recipeInstructions = intent.getStringExtra("recipeInstructions");
        int recipeServings = intent.getIntExtra("recipeServings", 0);

        // Get the fields from the xml layout and set them with the data received from the intent
        TextView tvRecipeName = (TextView) findViewById(R.id.tvRecipeName);
        TextView tvRecipeInstructions = (TextView) findViewById(R.id.tvRecipeInstructions);
        TextView tvRecipeServings = (TextView) findViewById(R.id.tvRecipeServings);
        TextView tvRecipeIngredients = (TextView) findViewById(R.id.tvRecipeIngredients);
        tvRecipeName.setText(recipeName);
        tvRecipeIngredients.setText("Ingredients: \n");
        tvRecipeInstructions.setText("Instructions: \n" + recipeInstructions);
        tvRecipeServings.setText(String.valueOf("Servings: " + recipeServings));
    }
}
