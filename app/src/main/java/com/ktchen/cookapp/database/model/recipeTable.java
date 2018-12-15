package com.ktchen.cookapp.database.model;

/**
 * This class contains the information for what columns etc are in the database.
 *
 * @author Jessica Ortner
 */
public class recipeTable {
    public static final String TABLE_NAME = "Recipes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_INGREDIENT = "ingredients";
    public static final String COLUMN_DIRECTION = "directions";
    public static final String COLUMN_IMAGE = "image";


    private int id;
    private String title;
    private String ingredient;
    private String directions;
    private String image;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_INGREDIENT + " TEXT,"
                    + COLUMN_IMAGE + " TEXT,"
                    + COLUMN_DIRECTION + " TEXT" + ")";

    /**
     * Default constructor for the table.
     */
    public recipeTable() {
    }

    /**
     * Constructor for a row of the table.
     *
     * @param id         The id of the recipe
     * @param title      The title of the recipe
     * @param ingredient A string with the ingredients.
     * @param direction  A string with the directions.
     */
    public recipeTable(int id, String title, String ingredient, String direction, String imagePath) {
        this.id = id;
        this.title = title;
        this.ingredient = ingredient;
        this.directions = direction;
        this.image = imagePath;
    }

    /**
     * Gets Id.
     *
     * @return an int that holds ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id
     *
     * @param id The int representing the ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the title of recipe.
     *
     * @return String containing the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title String with desired title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the string with ingredients.
     *
     * @return The string with ingredients.
     */
    public String getIngredient() {
        return ingredient;
    }

    /**
     * Sets the ingredient.
     *
     * @param ingredient A string with the desired ingredients.
     */
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Gets the directions.
     *
     * @return A string containing the directions.
     */
    public String getDirections() {
        return directions;
    }

    /**
     * Sets the directions.
     *
     * @param directions A string containing the desired directions.
     */
    public void setDirections(String directions) {
        this.directions = directions;
    }

    /**
     * Gets the image path.
     * @return
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets image path.
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }
}
