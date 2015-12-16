package edu.calvin.cs262.scrumptious;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Created by tjluce on 11/23/15.
 *
 * NOTE: Do we want the ability to bookmark the recipe from in here too?
 * Yes, I added a checkbox so can you make it bookmark a recipe? - Lia
 */
public class DisplayRecipe extends Activity implements AsyncResponse<String> {

    // Set up variables for accessing RESTful web service
    private static String SERVER_URI = "http://10.0.2.2:9998/scrumptious/";
    private static String DATA_URI = "recipe/bookmark/";
    private String webResults = "";
    private String[] splitWebResults = null;
    int recipeID, recipeServings;
    String recipeName, recipeInstructions;
    boolean recipeBookmarked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_recipe);

        // Change the title
        ActionBar ab = getActionBar();
        ab.setTitle("                                           ");

        // Get the intent and get the recipe data from the intent's extras
        Intent intent = getIntent();
        recipeID = intent.getIntExtra("recipeID", -1);
        recipeName = intent.getStringExtra("recipeName");
        recipeInstructions = intent.getStringExtra("recipeInstructions");
        recipeServings = intent.getIntExtra("recipeServings", 0);
        recipeBookmarked = intent.getBooleanExtra("recipeBookmarked", false);

        // Get the fields from the xml layout and set them with the data received from the intent
        TextView tvRecipeName = (TextView) findViewById(R.id.tvRecipeName);
        TextView tvRecipeInstructions = (TextView) findViewById(R.id.tvRecipeInstructions);
        TextView tvRecipeServings = (TextView) findViewById(R.id.tvRecipeServings);
        TextView tvRecipeIngredients = (TextView) findViewById(R.id.tvRecipeIngredients);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        Button createDishButton = (Button) findViewById(R.id.createDish);

        tvRecipeName.setText(recipeName);
        tvRecipeIngredients.setText("Ingredients: \n");
        tvRecipeInstructions.setText("Instructions: \n" + recipeInstructions);
        tvRecipeServings.setText(String.valueOf("Servings: " + recipeServings));
        checkBox.setChecked(Boolean.valueOf(recipeBookmarked));

        // Set up the AsyncTask for when the checkBox is checked/unchecked
        final MyAsyncTask asyncTask = new MyAsyncTask(SERVER_URI + DATA_URI + recipeID);
        asyncTask.delegate = this;

        // Listen for the checkbox to be pushed
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //asyncTask.execute();  Breaks if you do it more than once...
            }
        });

        // Listen for the add dish button to be pushed
        createDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayRecipe.this, AddDish.class);
                intent.putExtra("recipeID", recipeID);
                intent.putExtra("recipeName", recipeName);
                intent.putExtra("recipeInstructions", recipeInstructions);
                intent.putExtra("recipeServings", recipeServings);
                intent.putExtra("recipeBookmakred", recipeBookmarked);
                startActivity(intent);
                finish();
            }
        });

    }

    public void processFinish(String output) {
        Log.d(Scrumptious.class.getSimpleName(), "webResults: " + output);
        //asyncTask = new MyAsyncTask(SERVER_URI + DATA_URI + recipeID);
    }
}
