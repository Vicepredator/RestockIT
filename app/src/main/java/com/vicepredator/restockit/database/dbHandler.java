package com.vicepredator.restockit.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
public abstract class dbHandler extends RoomDatabase {
    private static dbHandler INSTANCE;
    public abstract ProductsDao ProductsDao();
    public static dbHandler getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), dbHandler.class, "Products")
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }
}