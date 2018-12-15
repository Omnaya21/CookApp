package com.ktchen.cookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ktchen.cookapp.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for handling the meal planning functionality.
 * It has to check if there is enough recipes stored on the database and
 * then create a plan. Inc ase there is not enough recipes, it will only
 * plan for the number of recipes (1 per day)
 */
public class CreatePlanActivity extends AppCompatActivity {
    public static final String EXTRA_DAYS = "com.ktchen.cookapp/mealPlanDays";
    private List<Recipe> recipes = new ArrayList<>();
    private EditText days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        days = findViewById(R.id.input_days);

        /// Load database to check if we have enough recipes
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        recipes.addAll(db.getAllRecipes());
        db.close();
        final int nRecipes = recipes.size();
        if (nRecipes == 0){
            Toast.makeText(this, "Recipes database is empty", Toast.LENGTH_SHORT).show();
            finish();   // Finish the activity and return to previous activity. This probably won't even display the activity.
        }

        Button createBtn = findViewById(R.id.create_plan_button);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (days.getText().toString().matches(""))
                Toast.makeText(CreatePlanActivity.this, "Type number of days to plan.", Toast.LENGTH_SHORT).show();
            else {
                // Validate the number of days
                int nDays = Integer.parseInt(days.getText().toString());
                if (nDays == 0)
                    Toast.makeText(CreatePlanActivity.this, "Number of days must be greater than 0.", Toast.LENGTH_SHORT).show();
                else {
                    if (nRecipes < nDays) {
                        Toast.makeText(CreatePlanActivity.this, "Number of days is less than recipes. Creating " + nRecipes + " days plan.", Toast.LENGTH_SHORT).show();
                        nDays = nRecipes;
                    }

                    Intent intent = new Intent(CreatePlanActivity.this, MealPlanActivity.class);
                    intent.putExtra(EXTRA_DAYS, nDays);
                    finish();
                    startActivity(intent);
                }
            }
            }
        });
    }

}
