/**
 * RecipeBookListAlphabetical.java
 *
 * Choose how to view recipies
 * -alphabetically - RecipeBookListAlphabetical.java
 *
 * [UNDER CONSTRUCTION]
 */
package edu.calvin.cs262.scrumptious;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class RecipeBookMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_book_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_book_menu, menu);
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
//go to Recipe Book List Alphabetical menu, where recipes are listed alphabetically...
    public void startRecipeBookMenu(View view){
        Intent intent = new Intent(RecipeBookMenu.this, RecipeBookListAlphabetical.class);
        startActivity(intent);
    }
}
