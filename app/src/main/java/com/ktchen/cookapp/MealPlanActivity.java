package com.ktchen.cookapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ktchen.cookapp.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * This activity will show the Meal Plan details ina list view control
 */
public class MealPlanActivity extends AppCompatActivity {
    private List<Recipe> recipes = new ArrayList<>();
    private int nDays = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        /// Add toolbar to activity
        Toolbar createPlanToolbar = findViewById(R.id.meal_plan_toolbar);
        setSupportActionBar(createPlanToolbar);

        // Load the Recipe's database to later add random recipes.
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        recipes.addAll(db.getAllRecipes());
        db.close();

        // Get the number of days to plan
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
            nDays = extras.getInt(CreatePlanActivity.EXTRA_DAYS);
        }

        // Set days to plan on a subtitle
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            setTitle("Meal Plan");
            String subtitleStr = nDays + " day";
            if (nDays > 1)
                subtitleStr += "s";
            ab.setSubtitle(subtitleStr);
        }
        final ListView mealPlanListView = findViewById(R.id.meal_plan_listview);

        /// Create the random strings with dates starting today
        String[] createdPlan = CreateRandomRecipes(nDays, recipes);

        final List<String> mealPlanList = new ArrayList<>(Arrays.asList(createdPlan));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                mealPlanList);
        mealPlanListView.setAdapter(adapter);
        mealPlanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String itemValue = (String) mealPlanListView.getItemAtPosition(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(MealPlanActivity.this);
                dialog.setTitle("Send to calendar?")
                        .setMessage("Are you sure you want to add this plan to your calendar " + itemValue)
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int which) {
                                /// Add every item to the calendar here
                                ///
                            }
                        })
                        .setIcon(R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private String[] CreateRandomRecipes(int nDays, List<Recipe> recipes) {
        String[] result = new String[nDays];

        long seed = System.nanoTime();
        Collections.shuffle(recipes, new Random(seed));
        for (int count = 0; count < nDays; count++) {
            result[count] = recipes.get(count).getTitle();
        }

        return result;
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     *
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     *
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     *
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meal_plan_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_to_calendar:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MealPlanActivity.this);
                dialog.setTitle("Send to calendar")
                        .setMessage("Are you sure you want to add this plan to your calendar?")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int which) {
                                /// Add every item to the calendar here
                                ///
                            }
                        })
                        .setIcon(R.drawable.ic_dialog_alert)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
