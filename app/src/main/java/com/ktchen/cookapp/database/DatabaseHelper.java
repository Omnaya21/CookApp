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
import static com.ktchen.cookapp.database.model.recipeTable.COLUMN_IMAGE;
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
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "recipes_db";
    private static DatabaseHelper sInstance = null;

    /**
     * Checks if there is an existing database, and if not creates it, else
     * returns the instance of it.
     *
     * @param context current context
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
        values.put(COLUMN_IMAGE, recipe.getImagePath());

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
        Recipe recipe = null;
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_INGREDIENT, COLUMN_DIRECTION, COLUMN_IMAGE},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null ) {
            cursor.moveToFirst();

            //prepares new Recipe object.
            recipe = new Recipe(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENT)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DIRECTION)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
            );
            cursor.close();
        }
        return recipe;
    }

    /**
     * Gets all the Recipes in the database.
     *
     * @return Returns a list containing of all the recipes.
     */
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_TITLE + " ASC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_INGREDIENT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DIRECTION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                        );
                recipes.add(recipe);
            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return recipes;
    }

    /**
     * Updates the recipe in the database.
     *
     * @param recipe Takes the new version of the recipe.
     * @return Returns number of rows updated.
     */
    public int updateRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, recipe.getTitle());
        values.put(COLUMN_INGREDIENT, recipe.getIngredients());
        values.put(COLUMN_DIRECTION, recipe.getDirections());
        values.put(COLUMN_IMAGE, recipe.getImagePath());


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

    /**
     * Deletes whole table.
     */
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
}