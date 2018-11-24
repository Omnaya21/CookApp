package com.ktchen.cookapp;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {
   @TypeConverter
    public static List<Ingredient> fromIngredientString(String text){
       List<Ingredient> ingredientList= new ArrayList<Ingredient>();
     Ingredient ingredient= new Gson().fromJson(text,Ingredient.class);
     ingredientList.add(ingredient);
     return ingredientList;

    }

    @TypeConverter
    public static List<String> fromString(String text){
        Type listType= new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(text,listType);
    }

    @TypeConverter
    public static String fromIngredientList(List<Ingredient> ingredients){
    Gson gson = new Gson();
    List<String> ingredientList = new ArrayList<String>();

    for(int i=0; i< ingredients.size(); i++){
        ingredientList.add(ingredients.get(i).toString());
    }

    String json= gson.toJson(ingredientList);
    return json;
    }

    @TypeConverter
    public static String fromStringList(List<String> directions){
        Gson gson= new Gson();
    String json = gson.toJson(directions);
    return json;
    }
}
