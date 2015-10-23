package edu.calvin.cs262.scrumptious;

import android.os.Bundle;
import android.app.Activity;

public class ShoppingListMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_menu);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
