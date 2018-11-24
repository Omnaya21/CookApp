package com.ktchen.cookapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecipeBook implements Serializable {
    List<Recipe> recipeBook= new ArrayList<Recipe>();

    public void addRecipe (Recipe item) {
        recipeBook.add(item);
    }

    public List<Recipe> getRecipeBook(){
        return recipeBook;
    }

}
