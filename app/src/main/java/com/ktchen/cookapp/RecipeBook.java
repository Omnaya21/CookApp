package com.ktchen.cookapp;

import java.util.ArrayList;
import java.util.List;

public class RecipeBook {
    List<Recipe> recipeBook= new ArrayList<Recipe>();

    public void addRecipe (Recipe item) {
        recipeBook.add(item);
    }

    public List<Recipe> getRecipeBook(){
        return recipeBook;
    }

}
