/**
 * WeeklyPlanMenu.java
 *
 * Display weekly recipies.. possibly by days or other time periods..
 *
 * [UNDER CONSTRUCTION]
 */

package edu.calvin.cs262.scrumptious;

import android.os.Bundle;
import android.app.Activity;

public class WeeklyPlanMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_plan_menu);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
