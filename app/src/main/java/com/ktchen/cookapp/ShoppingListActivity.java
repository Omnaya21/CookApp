package com.ktchen.cookapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ktchen.cookapp.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * This class handles the Shopping List functionality.
 */
public class ShoppingListActivity extends AppCompatActivity {
    private List<Recipe> recipes = new ArrayList<>();
    private List<String> ingredientsList;
    private ArrayAdapter<String> adapter;
    private String textNewItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        DatabaseHelper db = DatabaseHelper.getInstance(this);
        recipes = db.getAllRecipes();

        /// Code to show the toolbar
        Toolbar shoppingToolbar = findViewById(R.id.shopping_list_toolbar);
        setSupportActionBar(shoppingToolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Shopping List");

        final ListView shoppingList = findViewById(R.id.shopping_listview);

        /// Before showing the list we have to search into our meal plan
        /// and grab the ingredients we need to show
        String[] ingredients = GetIngredientsFromDatabase();
        ingredientsList = new ArrayList<>(Arrays.asList(ingredients));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
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

        /// Set the onClick listener for the FAB Add recipe button
        FloatingActionButton fabNewShoppingListItem = findViewById(R.id.fab_new_grocery_item);
        fabNewShoppingListItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddItem(v);
                //ingredientsList.add("New item");
                //adapter.notifyDataSetChanged();
            }
        });
    }

    private void AddItem(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("New item");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        dialog.setView(input);

        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textNewItem = input.getText().toString();
                if (!textNewItem.matches("")) {
                    ingredientsList.add(textNewItem);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private String[] GetIngredientsFromDatabase() {
        // Use a HashSet to avoid duplicates automatically
        HashSet uniqueIngredients = new HashSet<>();
        if (recipes != null) {
            for (Recipe recipe : recipes) {
                if (!recipe.getIngredients().matches("")) {
                    String[] items = recipe.getIngredients().split(",");
                    for (String item: items) {
                        uniqueIngredients.add(item.trim()); // use trim() to remove any leading and trailing spaces
                    }
                }
            }
        }
        return (String[]) uniqueIngredients.toArray(new String[uniqueIngredients.size()]);
    }
}
