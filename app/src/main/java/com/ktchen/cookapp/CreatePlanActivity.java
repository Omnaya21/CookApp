package com.ktchen.cookapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class is responsible for handling the meal planning functionality.
 * It has to check if there is enough recipes stored on the database and
 * then create a plan. Inc ase there is not enough recipes, it will only
 * plan for the number of recipes (1 per day)
 */
public class CreatePlanActivity extends AppCompatActivity {
    public static final String EXTRA_DAYS = "com.ktchen.cookapp/mealPlanDays";
    private Button createBtn;
    private EditText days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);

        days = findViewById(R.id.input_days);

        createBtn = findViewById(R.id.create_plan_button);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String daysString = days.getText().toString();
                Intent intent = new Intent(CreatePlanActivity.this, MealPlanActivity.class);
                intent.putExtra(EXTRA_DAYS, daysString);
                startActivity(intent);
                Toast.makeText(CreatePlanActivity.this, "Creating a plan for " + daysString + " days", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
