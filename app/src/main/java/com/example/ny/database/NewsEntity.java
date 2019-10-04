package com.example.ny.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news")
public class NewsEntity {

	@PrimaryKey(autoGenerate = true)
	@NonNull
	private int id;

	@NonNull
	@ColumnInfo(name = "title")
	private String title;

	@NonNull
	@ColumnInfo(name = "preview_text")
	private String previewText;

	@NonNull
	@ColumnInfo(name = "full_text")
	private String fullText;

	@NonNull
	@ColumnInfo(name = "section")
	private String section;

	@NonNull
	@ColumnInfo(name = "image_link")
	private String imageLink;

	@NonNull
	@ColumnInfo(name = "date_create")
	private String dateCreate;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public NewsEntity() {
	}

	@NonNull
	public String getTitle() {
		return title;
	}

	public void setTitle(@NonNull String title) {
		this.title = title;
	}

	@NonNull
	public String getPreviewText() {
		return previewText;
	}

	public void setPreviewText(@NonNull String previewText) {
		this.previewText = previewText;
	}

	@NonNull
	public String getFullText() {
		return fullText;
	}

	public void setFullText(@NonNull String fullText) {
		this.fullText = fullText;
	}

	@NonNull
	public String getSection() {
		return section;
	}

	public void setSection(@NonNull String section) {
		this.section = section;
	}

	@NonNull
	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(@NonNull String imageLink) {
		this.imageLink = imageLink;
	}

	@NonNull
	public String getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(@NonNull String dateCreate) {
		this.dateCreate = dateCreate;
	}

	@Override
	public String toString() {
		return "NewsEntity{" +
				"id=" + id +
				", title='" + title + '\'' +
				", previewText='" + previewText + '\'' +
				", fullText='" + fullText + '\'' +
				", section='" + section + '\'' +
				", imageLink='" + imageLink + '\'' +
				", dateCreate='" + dateCreate + '\'' +
				'}';
	}
}
