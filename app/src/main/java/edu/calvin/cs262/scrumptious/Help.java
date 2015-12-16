/**
 * Help.java
 *
 * Created by leg4 on 12/14/2015.
 *
 * Displays the help documentation.
 */

package edu.calvin.cs262.scrumptious;

import android.app.Activity;
import android.app.ActionBar;
import android.os.Bundle;

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
