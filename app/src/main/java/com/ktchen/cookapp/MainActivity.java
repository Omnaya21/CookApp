package com.ktchen.cookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;


/**
 * MainActivity is the first screen where we can interact and select what to do.
 *
 * @author Omar
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("ActivityInfo", "Main created");

        /// Define cards
        CardView recipeCard = findViewById(R.id.recipes_card);
        CardView calendarCard = findViewById(R.id.calendar_card);
        CardView planCard = findViewById(R.id.create_plan_card);
        CardView shoppingCard = findViewById(R.id.shopping_list_card);
        CardView addCard = findViewById(R.id.add_recipe_card);

        /// Add onclick event listener
        recipeCard.setOnClickListener(this);
        calendarCard.setOnClickListener(this);
        planCard.setOnClickListener(this);
        shoppingCard.setOnClickListener(this);
        addCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.recipes_card: // Recipes option
                i = new Intent(v.getContext(), RecipesActivity.class);
                startActivityForResult(i, 0);
                break;

            case R.id.calendar_card: // Calendar option
                i = new Intent(v.getContext(), ViewCalendar.class);
                startActivityForResult(i, 0);
                break;

            case R.id.create_plan_card: // Make a Plan option
                i = new Intent(v.getContext(), CreatePlanActivity.class);
                startActivityForResult(i, 0);
                break;

            case R.id.shopping_list_card: // Shopping List option
                i = new Intent(v.getContext(), ShoppingListActivity.class);
                startActivityForResult(i, 0);
                break;

            case R.id.add_recipe_card: // Add New Recipe option
                i = new Intent(v.getContext(), AddRecipe.class);
                startActivityForResult(i, 0);
                break;
        }
    }
}
