package com.ktchen.cookapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShoppingListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        setTitle("Shopping List");

        /// Before showing the list we have to search into our meal plan
        /// and grab the ingredients we need to show
    }
}
