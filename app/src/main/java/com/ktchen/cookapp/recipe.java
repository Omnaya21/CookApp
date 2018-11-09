package com.ktchen.cookapp;

import android.media.Image;
import android.provider.CalendarContract;

import java.util.List;

public class recipe {

    private String name;
    private Image picture;
    private List<ingredient> ingredients;
    private List<String> directions;
    private List<CalendarContract.Reminders> reminders;

    recipe(String name){
       this.name=name;
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

    public List<ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<ingredient> ingredients) {
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
    public void addItem (List<recipe> item) {

    }
    public void removeItem (List<recipe> item) {

    }
    public void clearList (List<recipe> list){

    }
}
