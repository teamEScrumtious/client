/**
 * RecipeBookMenu.java
 *
 * Choose how to view the recipes
 * -alphabetically - RecipeBookListAlphabetical.java
 * -bookmarked - RecipeBookListBookmarked.java
 * -recents - RecipeBookListRecent.java
 *
 */
package edu.calvin.cs262.scrumptious;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RecipeBookMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_book_menu);

        // Change the title
        ActionBar ab = getActionBar();
        ab.setTitle("                                           ");
    }

//go to Recipe Book List Alphabetical menu, where recipes are listed alphabetically...
    public void startRecipeBookAlphabetical(View view){
        Intent intent = new Intent(RecipeBookMenu.this, RecipeBookListAlphabetical.class);
        startActivity(intent);
    }

    //go to Recipe Book List Alphabetical menu, where recipes are listed alphabetically...
    public void startRecipeBookBookmarked(View view){
//        Intent intent = new Intent(RecipeBookMenu.this, RecipeBookListBookmarked.class);
//        startActivity(intent);
    }

    //go to Recipe Book List Alphabetical menu, where recipes are listed alphabetically...
    public void startRecipeBookRecent(View view){
//        Intent intent = new Intent(RecipeBookMenu.this, RecipeBookListRecent.class);
//        startActivity(intent);
    }
}
