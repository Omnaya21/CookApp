package com.ktchen.cookapp.database.model;

public class recipeTable {
    public static final String TABLE_NAME ="Recipes";
    public static final String COLUMN_ID= "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_INGREDIENT= "ingredients";
    public static final String COLUMN_DIRECTION= "directions";



    private int id;
    private String title;
    private String ingredient;
    private String directions;

                public static final String CREATE_TABLE =
            "CREATE TABLE "+ TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_INGREDIENT + " TEXT,"
                + COLUMN_DIRECTION + " TEXT" + ")";
    public recipeTable() {
    }

    public recipeTable(int id, String title, String ingredient, String direction){
        this.id= id;
        this.title= title;
        this.ingredient= ingredient;
        this.directions= direction;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

}
