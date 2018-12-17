package com.ktchen.cookapp;

import java.io.Serializable;

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
     * @param name Recipe's name
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
     * @param title Recipe's name
     * @param ingredient Ingredients
     * @param directions Directions
     */
    public Recipe(String title, String ingredient, String directions) {
        this.title = title;
        this.ingredients = ingredient;
        this.directions = directions;
    }

    /**
     * Constructor that takes a title, ingredient, directions, and user ID.
     *
     * @param title Recipe's name
     * @param ingredient Ingredients
     * @param directions Directions
     * @param id Recipe's id
     */
    public Recipe(int id, String title, String ingredient, String directions) {
        this.title = title;
        this.ingredients = ingredient;
        this.directions = directions;
        this.id = id;
    }

    public Recipe(String title, String ingredient, String directions, String image) {
        this.title = title;
        this.ingredients = ingredient;
        this.directions = directions;
        this.imagePath = image;
    }

    public Recipe(int id, String title, String ingredient, String directions, String image) {
        this.title = title;
        this.ingredients = ingredient;
        this.directions = directions;
        this.id = id;
        this.imagePath = image;
    }

    /*
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
     * @param name Sets recipe's name
     */
    public void setTitle(String name) {
        title = name;
    }

    /**
     * Getter for title.
     *
     * @return Returns recipe's name
     */
    public String getTitle() {
        return title;
    }

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
     * @param ingredients Set the recipe's ingredients
     */
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Getter for directions.
     *
     * @return Returns recipe's ingredients
     */
    public String getDirections() {
        return directions;
    }

    /**
     * Setter for directions.
     *
     * @param directions Sets recipe's directions
     */
    public void setDirections(String directions) {
        this.directions = directions;
    }

    /**
     * Returns the whole recipe.
     *
     * @return Returns a Recipe instance
     */
    public Recipe getRecipe() {
        return this;
    }

    /**
     * Sets the imagePath.
     * @param imagePath Image path for the picture
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Returns the imagePath.
     * @return Returns the image path
     */
    public String getImagePath() {
        return imagePath;
    }
}
