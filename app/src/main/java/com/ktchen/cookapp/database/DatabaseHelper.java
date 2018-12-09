package com.ktchen.cookapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ktchen.cookapp.Recipe;
import com.ktchen.cookapp.database.model.recipeTable;

import java.util.ArrayList;
import java.util.List;

import static com.ktchen.cookapp.database.model.recipeTable.COLUMN_DIRECTION;
import static com.ktchen.cookapp.database.model.recipeTable.COLUMN_ID;
import static com.ktchen.cookapp.database.model.recipeTable.COLUMN_INGREDIENT;
import static com.ktchen.cookapp.database.model.recipeTable.COLUMN_TITLE;
import static com.ktchen.cookapp.database.model.recipeTable.TABLE_NAME;

/**
 * This class translates functions into SQL commands.
 * <p>
 * It extends SQLiteOpenHelper, and allows user to do things like insert stuff in the database.
 *
 * @author Jessica Ortner
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "recipes_db";
    private static DatabaseHelper sInstance = null;

    /**
     * Checks if there is an existing database, and if not creates it, else
     * returns the instance of it.
     *
     * @param context
     * @return DatabaseHelper
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     * Creates the database.
     *
     * @param db Passes in a SQL Database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(recipeTable.CREATE_TABLE);
    }

    /**
     * Upgrades the database.
     *
     * @param db         Passes in the Database.
     * @param oldVersion An int representing the old version number.
     * @param newVersion An int representign new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + recipeTable.TABLE_NAME);
        onCreate(db);
    }

    /**
     * Inserts a recipe into the database.
     *
     * @param recipe Takes a Recipe as the parameter.
     * @return returns a long representing an id.
     */
    public long insertRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, recipe.getTitle());
        values.put(COLUMN_INGREDIENT, recipe.getIngredients());
        values.put(COLUMN_DIRECTION, recipe.getDirections());

        long id = db.insert(TABLE_NAME, null, values);

        db.close();
        return id;
    }

    /**
     * Gets a recipe from the database.
     *
     * @param id Takes a long that represents the id.
     * @return Returns the recipe.
     */
    public Recipe getRecipe(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_INGREDIENT, COLUMN_DIRECTION},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        //prepares new Recipe object.
        Recipe recipe = new Recipe(
                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENT)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DIRECTION)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        cursor.close();
        return recipe;
    }

    /**
     * Gets all the Recipes in the database.
     *
     * @return Returns a list containing of all the recipes.
     */
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<Recipe>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_TITLE + " DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DIRECTION)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                recipes.add(recipe);
            } while (cursor.moveToNext());

        }
        db.close();
        return recipes;
    }

    /**
     * Updates the recipe in the database.
     *
     * @param recipe Takes the new version of the recipe.
     * @return Returns ID of the recipe.
     */
    public int updateRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, recipe.getTitle());
        values.put(COLUMN_INGREDIENT, recipe.getIngredients().toString());
        values.put(COLUMN_DIRECTION, recipe.getDirections().toString());

        return db.update(TABLE_NAME, values, COLUMN_ID + " =?", new String[]{String.valueOf(recipe.getID())});
    }

    /**
     * Deletes a recipe.
     *
     * @param recipe Takes the recipe to be deleted.
     */
    public void deleteRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " =?",
                new String[]{String.valueOf(recipe.getID())});
        db.close();
    }


}


