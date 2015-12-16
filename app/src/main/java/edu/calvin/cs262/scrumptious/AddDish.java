package edu.calvin.cs262.scrumptious;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import org.joda.time.LocalDate;

import java.util.ArrayList;

/**
 * Created by tjluce on 12/15/15.
 */
public class AddDish extends Activity implements AsyncResponse<String> {

    // Set up variables for accessing RESTful web service
    private static String SERVER_URI = "http://10.0.2.2:9998/scrumptious/";
    private static String DATA_URI = "recipes/addDish/";
    private int asyncTasksReturned = 0;
    private String webResults = "";
    private String[] splitWebResults = null;
    Intent mainIntent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dish);

        // Change the title
        ActionBar ab = getActionBar();
        ab.setTitle("                                           ");

        // Get the intent and get the recipe data from the intent's extras
        Intent intent = getIntent();
        final int recipeID = intent.getIntExtra("recipeID", -1);
        final String recipeName = intent.getStringExtra("recipeName");
        final String recipeInstructions = intent.getStringExtra("recipeInstructions");
        final int recipeServings = intent.getIntExtra("recipeServings", 0);
        final boolean recipeBookmarked = intent.getBooleanExtra("recipeBookmarked", false);

        // Get views
        final NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);     // Monday
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.checkBox2);   // Tuesday
        CheckBox checkBox3 = (CheckBox) findViewById(R.id.checkBox3);   // Wednesday
        CheckBox checkBox4 = (CheckBox) findViewById(R.id.checkBox4);   // Thursday
        CheckBox checkBox5 = (CheckBox) findViewById(R.id.checkBox5);   // Friday
        CheckBox checkBox6 = (CheckBox) findViewById(R.id.checkBox6);   // Saturday
        CheckBox checkBox7 = (CheckBox) findViewById(R.id.checkBox7);   // Sunday
        Button addDishButton = (Button) findViewById(R.id.addrecipe);

        // Make an ArrayList of check boxes
        final ArrayList<CheckBox> checkBoxList = new ArrayList<CheckBox>();
        checkBoxList.add(checkBox);
        checkBoxList.add(checkBox2);
        checkBoxList.add(checkBox3);
        checkBoxList.add(checkBox4);
        checkBoxList.add(checkBox5);
        checkBoxList.add(checkBox6);
        checkBoxList.add(checkBox7);

        // Set up views
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(99);
        numberPicker.setWrapSelectorWheel(true);

        // Listen for the add dish button to be pushed
        addDishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 1; i < 8; i++) {
                    if (checkBoxList.get(i-1).isChecked()) {
                        // Set up the current date
                        LocalDate currentDate = new LocalDate();

                        // Get difference in days between days of week (accounts for end/starts of weeks)
                        int daysToAdd = (currentDate.getDayOfWeek() - i);
                        if (daysToAdd <= 0) {
                            daysToAdd = Math.abs(daysToAdd);
                        } else {
                            daysToAdd = 7 - daysToAdd;
                        }

                        // Set up the time for the dish date
                        LocalDate dishDate = currentDate.plusDays(daysToAdd);
                        String dishDateTime = dishDate.getYear() + "-" + dishDate.getMonthOfYear() + "-" + dishDate.getDayOfMonth() + " " +
                                timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ":00";

                        // Set up and execute the AsyncTask for when the add dish button is pressed
                        MyAsyncTask asyncTask = new MyAsyncTask(SERVER_URI + DATA_URI);
                        asyncTask.delegate = AddDish.this;
                        asyncTask.execute(recipeID + " " + numberPicker.getValue() + " " + dishDateTime);
                    } else {
                        asyncTasksReturned++; // Make sure the count of asyncTasks doesn't get messed up

                        // If all asyncTasks are done
                        if (asyncTasksReturned == 7) {
                            // Finish the activity and return to last activity
                            finish();
                        }
                    }
                }
            }
        });

    }

    public void processFinish(String output) {
        //Log.d(Scrumptious.class.getSimpleName(), "webResults: " + output);

        asyncTasksReturned++; // Add to counter

        // If all asyncTasks are done
        if (asyncTasksReturned == 7) {
            // Finish the activity and return to last activity while passing back data
            this.finish();
        }
    }
}
