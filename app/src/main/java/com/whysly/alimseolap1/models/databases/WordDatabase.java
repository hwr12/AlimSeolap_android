package com.whysly.alimseolap1.models.databases;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.whysly.alimseolap1.DataTransformer;
import com.whysly.alimseolap1.models.daos.WordDao;
import com.whysly.alimseolap1.models.entities.WordEntity;

@Database(entities = {WordEntity.class}, version = 1)
@TypeConverters({DataTransformer.class})
public abstract class  WordDatabase extends RoomDatabase {
    private static WordDatabase wordDatabase;

    public abstract WordDao wordDao();

    public static WordDatabase getWordDatabase(Context context) {
        if (wordDatabase == null) {
            wordDatabase = Room.databaseBuilder(context.getApplicationContext(), WordDatabase.class, "word-db")
                    .allowMainThreadQueries()
                    .build();
        }
        return wordDatabase;
    }

    public static void destroyInstance() {
        wordDatabase = null;
    }
}