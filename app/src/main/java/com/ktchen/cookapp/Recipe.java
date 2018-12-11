package com.ktchen.cookapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.media.Image;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to define a Recipe item.
 */
public class Recipe implements Serializable {

    private String title;
    private String ingredients = null;
    private String directions = null;
    private String imagePath = null;
    private long id = 0;

    // private Image picture;
    // private List<CalendarContract.Reminders> reminders;

    /**
     * Constructor taking just a name.
     *
     * @param name
     */
    public Recipe(String name) {
        title = name;
    }

    /**
     * Default Constructor.
     */
    public Recipe() {
        title = "New Recipe";
    }

    /**
     * Constructor taking a title, ingredient, and directions.
     *
     * @param title
     * @param ingredient
     * @param directions
     */
    public Recipe(String title, String ingredient, String directions) {
        this.title = title;
        this.ingredients = ingredient;
        this.directions = directions;
    }

    /**
     * Constructor that takes a title, ingredient, directions, and user ID.
     *
     * @param title
     * @param ingredient
     * @param directions
     * @param id
     */
    public Recipe(int id, String title, String ingredient, String directions) {
        this.title = title;
        this.ingredients = ingredient;
        this.directions = directions;
        this.id = id;
    }

    public Recipe(int id, String title, String ingredient, String directions, String image) {
        this.title = title;
        this.ingredients = ingredient;
        this.directions = directions;
        this.id = id;
        this.imagePath = image;
    }

    /**
     * Setter for ID.
     *
     * @param id (a long).
     */
    public void setID(long id) {
        this.id = id;
    }

    /**
     * Getter for ID
     *
     * @return a long ID.
     */
    public long getID() {
        return id;
    }

    /**
     * Setter for title.
     *
     * @param name
     */
    public void setTitle(String name) {
        title = name;
    }

    /**
     * Getter for title.
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    // public Image getPicture() {
    //   return picture;
    //}

    // public void setPicture(Image picture) {
    //   this.picture = picture;
    //}

    /**
     * Getter for ingredients.
     *
     * @return a String with ingredients.
     */
    public String getIngredients() {
        return ingredients;
    }

    /**
     * Setter for ingredients.
     *
     * @param ingredients
     */
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Getter for directions.
     *
     * @return
     */
    public String getDirections() {
        return directions;
    }

    /**
     * Setter for directions.
     *
     * @param directions
     */
    public void setDirections(String directions) {
        this.directions = directions;
    }

    /**
     * Returns the whole recipe.
     *
     * @return
     */
    public Recipe getRecipe() {
        return this;
    }

    public void setImagePath(String imagePath) {this.imagePath = imagePath;}
    public String getImagePath() { return imagePath;}
}
