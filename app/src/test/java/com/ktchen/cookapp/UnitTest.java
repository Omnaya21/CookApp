package com.ktchen.cookapp;

import android.content.Context;

import com.ktchen.cookapp.database.DatabaseHelper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.*;


/**
 * This class has the unit tests for our app.
 */
@RunWith(RobolectricTestRunner.class)

public class UnitTest {
    private Recipe recipe;
    private DatabaseHelper db;

    @Before
    public void setUp() throws Exception {
        recipe= new Recipe("soup","ingredients", "directions");
        Context context = ApplicationProvider.getApplicationContext();
        db = DatabaseHelper.getInstance(context);
        long id= db.insertRecipe(recipe);
        recipe.setID(id);
    }

    @After
    public void cleanup(){
        db.deleteAll();
        db.close();
    }
    /**
     * Tests that recipe was created with the expected values.
     */
    @Test
    public void CreateRecipeTest(){
        Assert.assertEquals("soup", recipe.getTitle());
        Assert.assertEquals("ingredients", recipe.getIngredients());
        Assert.assertEquals("directions", recipe.getDirections());

    }

    /**
     * Tests that the setters work as expected.
     */
    @Test
    public void ChangeRecipeTest(){
        recipe.setTitle("pasta");
        recipe.setIngredients("cheese, pasta");
        recipe.setDirections("new directions");
        Assert.assertEquals("pasta", recipe.getTitle());
        Assert.assertEquals("cheese, pasta", recipe.getIngredients());
        Assert.assertEquals("new directions", recipe.getDirections());
    }

    /**
     * Tests inserting into database.
     */
    @Test
    public void InsertDatabaseTest() {
        Assert.assertEquals(recipe.getTitle(), db.getRecipe(recipe.getID()).getTitle());
    }
    /**
     * Tests deleting recipe from database.
     */
    @Test(expected = Exception.class)
    public void DeleteDatabaseTest(){
        Recipe newRecipe= new Recipe("Test");
        long id= db.insertRecipe(newRecipe);
        newRecipe.setID(id);
        db.deleteRecipe(newRecipe);
        //this should throw an exemption because it no longer exists.
        db.getRecipe(id).getTitle();
    }

}
