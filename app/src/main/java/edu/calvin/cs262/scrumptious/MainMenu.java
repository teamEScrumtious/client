/**
 * FoodFor Thought Android Application
 * by Team Scrum-tious
 * 8/25/15 - November 2015
 *
 * Main menu acticity for app
 * Links to all features and submenus of the app
 *
 */

package edu.calvin.cs262.scrumptious;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
          //  case R.id.action_search:
          //      openSearch();               //commented out, Prof wanted us to cut out lab stuff
          //      return true;                  //also commented out openSearch()
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void openSearch(){
//        startActivity(new Intent(SearchManager.INTENT_ACTION_GLOBAL_SEARCH));
//    }

    private void openSettings(){
        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }

//    Go to recipe book activity
    public void startRecipeBookMenu(View view){
        Intent intent = new Intent(MainMenu.this, RecipeBookMenu.class);
        startActivity(intent);
    }
//    go to weekly plan activity
    public void startWeeklyPlanMenu(View view){
        Intent intent = new Intent(MainMenu.this, WeeklyPlanMenu.class);
        startActivity(intent);
    }
//    go to shopping list activity
    public void startShoppingListMenu(View view){
        Intent intent = new Intent(MainMenu.this, ShoppingListMenu.class);
        startActivity(intent);
    }
}
