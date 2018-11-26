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

//@Entity(tableName= "recipe_table")
public class Recipe implements Serializable {
  //  @PrimaryKey
   // @NonNull
    private String title;
    private String ingredients= null;
   // @ColumnInfo(name= "ingredients")
   // private List<Ingredient> ingredients =new ArrayList<Ingredient>();
   // @ColumnInfo(name= "directions")
    private String directions= null;
    private int id=0;

   // private Image picture;
   // private List<CalendarContract.Reminders> reminders;

    public Recipe(String name){
       title = name;
    }

    public Recipe(){title = "New Recipe"; id=id; id++;}

    public Recipe(String title, String ingredient, String directions, int id){
        this.title=title;
        this.ingredients=ingredient;
        this.directions= directions;
        this.id= id;

    }

    public Recipe(String title, String ingredient, String directons){
        this.title=title;
        this.ingredients=ingredient;
        this.directions= directions;
        this.id= id;
        id++;
    }

    public void setID(int id) {this.id=id; }
    public int getID() {return id;}

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

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
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
