package com.ktchen.cookapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ShoppingListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        setTitle("Shopping List");

        final ListView shoppingList = (ListView) findViewById(R.id.shoppingListView);

        /// Before showing the list we have to search into our meal plan
        /// and grab the ingredients we need to show
        String[] ingredients = new String[] {
                "Milk",
                "Eggs",
                "Cream cheese",
                "Pepper",
                "Soy sauce",
                "Oil",
                "Flour",
                "Jello",
                "Cinnamon"
        };

        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, ingredients);
        shoppingList.setAdapter(adapter);
        shoppingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue = (String) shoppingList.getItemAtPosition(position);
                Toast.makeText(ShoppingListActivity.this, itemValue, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
