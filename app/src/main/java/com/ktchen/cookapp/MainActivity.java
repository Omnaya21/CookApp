package com.ktchen.cookapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * MainActivity is the first screen where we can interact and select what to do.
 *
 * @author Omar
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView recipeCard;
    private CardView calendarCard;
    private CardView planCard;
    private CardView shoppingCard;
    private CardView addCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("ActivityInfo", "Main created");

        /// Define cards
        recipeCard = (CardView) findViewById(R.id.recipes_card);
        calendarCard = (CardView) findViewById(R.id.calendar_card);
        planCard = (CardView) findViewById(R.id.create_plan_card);
        shoppingCard = (CardView) findViewById(R.id.shopping_list_card);
        addCard = (CardView) findViewById(R.id.add_recipe_card);

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
