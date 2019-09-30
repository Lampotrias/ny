package com.example.ny.data;

import com.example.ny.database.NewsEntity;

public class ConverterData {

	public static NewsEntity toDatabase(NewsItem newsItem){
		NewsEntity newsEntity = new NewsEntity();
		newsEntity.setTitle(newsItem.getTitle());
		newsEntity.setSection(newsItem.getSection());
		newsEntity.setPreviewText(newsItem.getPreviewText());
		newsEntity.setFullText(newsItem.getPreviewText());
		newsEntity.setImageLink(newsItem.getImageByType(ImageType.STANDART_THUMBNAIL));
		newsEntity.setDateCreate(newsItem.getPublishedDate());

		return newsEntity;
	}

	public static NewsItem fromDatabase(NewsEntity newsEntity){
		NewsItem newsItem = new NewsItem();
		newsItem.setTitle(newsEntity.getTitle());
		newsItem.setSection(newsEntity.getSection());
		newsItem.setPreviewText(newsEntity.getPreviewText());
		newsItem.setPreviewText(newsEntity.getPreviewText());
		newsItem.setImageUrl(newsEntity.getImageLink());
		newsItem.setPublishedDate(newsEntity.getDateCreate());
		return newsItem;
	}
}
