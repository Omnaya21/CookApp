package com.ktchen.cookapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Recipe.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class RecipeRoomDatabase extends RoomDatabase {

    public abstract RecipeDao RecipeDao();

    private static volatile RecipeRoomDatabase INSTANCE;

    static RecipeRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RecipeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeRoomDatabase.class, "recipe_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}