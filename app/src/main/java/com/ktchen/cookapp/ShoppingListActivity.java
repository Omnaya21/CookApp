package com.ktchen.cookapp;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class handles the Shopping List functionality.
 */
public class ShoppingListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        Toolbar shoppingToolbar = findViewById(R.id.shopping_list_toolbar);
        setSupportActionBar(shoppingToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Shopping List");

        final ListView shoppingList = (ListView) findViewById(R.id.shoppingListView);

        /// Before showing the list we have to search into our meal plan
        /// and grab the ingredients we need to show
        String[] ingredients = new String[]{
                "Milk",
                "Eggs",
                "Cream cheese",
                "Pepper",
                "Soy sauce",
                "Oil",
                "Flour",
                "Jello",
                "Cinnamon",
                "Cookies",
                "Mint",
                "Sugar",
                "Apples"
        };

        final List<String> ingredientsList = new ArrayList<>(Arrays.asList(ingredients));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                ingredientsList);
        shoppingList.setAdapter(adapter);
        shoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String itemValue = (String) shoppingList.getItemAtPosition(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(ShoppingListActivity.this);
                dialog.setTitle("Delete?")
                        .setMessage("Are you sure you want to delete " + itemValue)
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int which) {
                                /// Remove item from listview
                                ingredientsList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setIcon(R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
}
