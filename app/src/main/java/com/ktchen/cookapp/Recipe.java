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

@Entity(tableName= "recipe_table")
public class Recipe implements Serializable {
    @PrimaryKey
    @NonNull
    private String title;
    @ColumnInfo(name= "ingredients")
    private List<Ingredient> ingredients =new ArrayList<Ingredient>();
    @ColumnInfo(name= "directions")
    private List<String> directions= new ArrayList<String>();

   // private Image picture;
   // private List<CalendarContract.Reminders> reminders;

    public Recipe(String name){
       title = name;
    }

    public Recipe(){title = "New Recipe";}

    public Recipe(String name/*, Image picture*/, List<Ingredient> ingredients, List<String> directions) {
        title = name;
       // this.picture = picture;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public void setTitle(String name) {
        title = name;
    }

    public String getTitle() {
        return title;
    }

   // public Image getPicture() {
     //   return picture;
    //}

   // public void setPicture(Image picture) {
     //   this.picture = picture;
    //}

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

    //public List<CalendarContract.Reminders> getReminders() {
      //  return reminders;
    //}

    //public void setReminders(List<CalendarContract.Reminders> reminders) {
      //  this.reminders = reminders;
    //}

    public void removeItem (List<Recipe> item) {

    }
    public void clearList (List<Recipe> list){

    }
    public Recipe getRecipe(){
        return this;
    }

}
