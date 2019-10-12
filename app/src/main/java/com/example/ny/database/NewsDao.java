package com.example.ny.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface NewsDao {
	@Query("SELECT * FROM news")
	List<NewsEntity> getAll();

	@Query("SELECT * FROM news")
	Flowable<List<NewsEntity>> getAllFromDatabase();

	@Query("SELECT * FROM news WHERE id = :id")
	NewsEntity getNewsByID(int id);

	@Query("SELECT * FROM news WHERE section LIKE :section")
	Flowable<List<NewsEntity>> getBySectionName(String section);

	@Insert (onConflict = OnConflictStrategy.REPLACE)
	void insertAll(List<NewsEntity> newsEntities);

	@Insert (onConflict = OnConflictStrategy.REPLACE)
	void insert(NewsEntity newsEntities);

	@Delete
	void delete (NewsEntity newsEntities);

	@Query("DELETE FROM news")
	void deleteAll();


}
