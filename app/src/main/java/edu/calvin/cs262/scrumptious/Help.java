package edu.calvin.cs262.scrumptious;

import android.app.Activity;
import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by leg4 on 12/14/2015.
 */
public class Help extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        // Change the title
        ActionBar ab = getActionBar();
        ab.setTitle("                                           ");
    }


}
