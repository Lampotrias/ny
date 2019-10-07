package com.example.ny.data;

import android.util.Log;

import com.example.ny.database.NewsEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConverterData {

	public static NewsEntity toDatabase(@NotNull NewsItem newsItem){
		NewsEntity newsEntity = new NewsEntity();
		newsEntity.setTitle(newsItem.getTitle());
		newsEntity.setSection(newsItem.getSection());
		newsEntity.setPreviewText(newsItem.getPreviewText());
		newsEntity.setFullText(newsItem.getPreviewText());
		newsEntity.setImageLink(newsItem.getImageByType(ImageType.STANDART_THUMBNAIL));
		newsEntity.setDateCreate(newsItem.getPublishedDate());

		return newsEntity;
	}

	public static NewsItem fromDatabase(@NotNull NewsEntity newsEntity){
		NewsItem newsItem = new NewsItem();
		newsItem.setId(newsEntity.getId());
		newsItem.setTitle(newsEntity.getTitle());
		newsItem.setSection(newsEntity.getSection());
		newsItem.setPreviewText(newsEntity.getPreviewText());
		newsItem.setPreviewText(newsEntity.getPreviewText());
		newsItem.setImageUrl(newsEntity.getImageLink());
		newsItem.setPublishedDate(newsEntity.getDateCreate());
		return newsItem;
	}
	public static List<NewsEntity> fromListToDatabase(@NotNull List<NewsItem> newsItemList){
		List<NewsEntity> list = new ArrayList<>();
		for (NewsItem it: newsItemList) {
			list.add(ConverterData.toDatabase(it));
		}
		return list;
	}

	public static List<NewsItem> fromDatabaseToList(@NotNull List<NewsEntity> newsItemList){
		List<NewsItem> list = new ArrayList<>();
		for (NewsEntity it: newsItemList) {
			list.add(ConverterData.fromDatabase(it));
		}
		return list;
	}
}
