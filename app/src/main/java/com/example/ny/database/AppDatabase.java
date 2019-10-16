package com.example.ny.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {NewsEntity.class}, version = 4, exportSchema = false)
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
							.fallbackToDestructiveMigration()
							.addMigrations(MIGRATION_2_3)
							//.allowMainThreadQueries()
							.build();
				}
			}
		}

		return singleton;
	}

	private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
		@Override
		public void migrate(final SupportSQLiteDatabase database) {
			database.execSQL("ALTER TABLE news ADD COLUMN detail_url TEXT DEFAULT '' NOT NULL");
		}
	};


}
