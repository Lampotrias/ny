package com.example.ny.data;

import com.example.ny.database.NewsEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ConverterData {

	private static NewsEntity toDatabase(@NotNull NewsItem newsItem){
		NewsEntity newsEntity = new NewsEntity();
		newsEntity.setTitle(newsItem.getTitle());
		newsEntity.setSection(newsItem.getSection());
		newsEntity.setPreviewText(newsItem.getPreviewText());
		newsEntity.setFullText(newsItem.getPreviewText());
		newsEntity.setImagePrevUrl(newsItem.getImagePrevUrl());
		newsEntity.setImageDetailUrl(newsItem.getImageDetailUrl());
		newsEntity.setDateCreate(newsItem.getPublishedDate());
		newsEntity.setDetailUrl(newsItem.getShortUrl());
		return newsEntity;
	}

	public static NewsItem fromDatabase(@NotNull NewsEntity newsEntity){
		NewsItem newsItem = new NewsItem();
		newsItem.setId(newsEntity.getId());
		newsItem.setTitle(newsEntity.getTitle());
		newsItem.setSection(newsEntity.getSection());
		newsItem.setPreviewText(newsEntity.getPreviewText());
		newsItem.setPreviewText(newsEntity.getPreviewText());
		newsItem.setImagePrevUrl(newsEntity.getImagePrevUrl());
		newsItem.setImageDetailUrl(newsEntity.getImageDetailUrl());
		newsItem.setPublishedDate(newsEntity.getDateCreate());
		newsItem.setShortUrl(newsEntity.getDetailUrl());
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
