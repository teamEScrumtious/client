package edu.calvin.cs262.scrumptious;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tjluce on 11/4/15.
 * RecipeAdapter adapts an array of Recipes to be displayed in a ListView
 *
 * Used tutorial at https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */
public class RecipeAdapter extends ArrayAdapter<Recipe> {
    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        super(context, 0, recipes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Recipe recipe = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_list_layout, parent, false);
        }

        // Lookup view for data population
        TextView tvRecipeName = (TextView) convertView.findViewById(R.id.tvRecipeName);
        TextView tvRecipeServings = (TextView) convertView.findViewById(R.id.tvRecipeServings);

        // Populate the data into the template view using the data object
        tvRecipeName.setText(recipe.getName());
        tvRecipeServings.setText(String.valueOf("Servings: " + recipe.getServings()));

        // Return the completed view to render on screen
        return convertView;
    }
}