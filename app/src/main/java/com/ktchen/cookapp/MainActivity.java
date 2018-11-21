package com.ktchen.cookapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    public ListView optionsList;
    String[] options = new String[]{"Recipes", "Calendar", "Shopping List",
            "Add New Recipe", "Make a Plan"};
    public static ArrayAdapter<String> optionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("ActivityInfo","Main created");
        // Add optins to ListView
        optionsList = findViewById(R.id.option_menu_list);
        optionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        optionsList.setAdapter(optionsAdapter);

        //Add onClick events to list options
        optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;

                switch (position) {
                    case 0: // Recipes option
                        i = new Intent(view.getContext(), RecipesActivity.class);
                        startActivityForResult(i, 0);
                        break;
                    case 1: // Calendar option

                        break;
                    case 2: // Shopping List option

                        break;
                    case 3: // Add New Recipe option
                        i = new Intent(view.getContext(), AddRecipe.class);
                        startActivityForResult(i, 0);
                        break;
                    case 4: // Make a Plan option

                        break;
                }



            }
        });

    }
}
