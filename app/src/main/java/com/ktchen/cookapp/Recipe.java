package com.ktchen.cookapp;

import android.media.Image;
import android.provider.CalendarContract;

import java.util.List;

public class Recipe {

    private String name;
    private Image picture;
    private List<Ingredient> ingredients;
    private List<String> directions;
    private List<CalendarContract.Reminders> reminders;

    Recipe(String name){
       this.name=name;
    }

    public Recipe(String name, Image picture, List<Ingredient> ingredients, List<String> directions) {
        this.name = name;
        this.picture = picture;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }

    public List<CalendarContract.Reminders> getReminders() {
        return reminders;
    }

    public void setReminders(List<CalendarContract.Reminders> reminders) {
        this.reminders = reminders;
    }
    public void addItem (List<Recipe> item) {

    }
    public void removeItem (List<Recipe> item) {

    }
    public void clearList (List<Recipe> list){

    }
}
