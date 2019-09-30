package com.example.ny.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.ny.data.NewsItem;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface NewsDao {
	@Query("SELECT * FROM news")
	Observable<List<NewsEntity>> getAll();

	@Query("SELECT * FROM news WHERE id = :id")
	NewsEntity getNewsByID(int id);

	@Insert (onConflict = OnConflictStrategy.REPLACE)
	void insertAll(NewsEntity... newsEntities);

	@Insert (onConflict = OnConflictStrategy.REPLACE)
	void insert(NewsEntity newsEntities);

	@Delete
	void delete (NewsEntity newsEntities);


}
