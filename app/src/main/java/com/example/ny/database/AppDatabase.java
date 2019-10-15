package com.example.ny.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NewsEntity.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

	private static AppDatabase singleton;
	private static final String DATABASE_NAME = "news.db";

	public abstract NewsDao newsDao();

	public static AppDatabase getInstance(Context context){
		if (singleton == null){
			synchronized (AppDatabase.class) {
				if (singleton == null){
					singleton = Room.databaseBuilder(context.getApplicationContext(),
							AppDatabase.class,
							DATABASE_NAME)
							.allowMainThreadQueries()
							.build();
				}
			}
		}

		return singleton;
	}


}
