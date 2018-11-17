package com.ktchen.cookapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert
    void insert(Recipe recipe);

    @Insert
    void insert(List<Recipe> recipes);

    @Query("SELECT * from recipe_table ORDER BY title ASC")
    LiveData<List<Recipe>> getAllRecipes();

    @Update
    void updateRecipe(Recipe recipe);

    @Query("DELETE FROM recipe_table")
    void deleteAll();

    @Delete
    void deleteRecipe(Recipe recipe);
}
