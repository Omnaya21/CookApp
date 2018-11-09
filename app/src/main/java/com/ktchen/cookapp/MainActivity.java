package com.ktchen.cookapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ListView optionsList;
    public static ArrayList<String> options;
    public static ArrayAdapter<String> optionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        optionsList = findViewById(R.id.option_menu_list);
        options = new ArrayList<>();
        options.add("Recipes");
        options.add("Calendar");
        options.add("Shopping List");
        options.add("Add New Recipe");
        options.add("Make a Plan");
        optionsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        optionsList.setAdapter(optionsAdapter);

        TextView label = findViewById(R.id.title);
        label.setVisibility(View.INVISIBLE);
    }
}
