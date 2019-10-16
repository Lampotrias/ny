package com.example.ny.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

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
	@ColumnInfo(name = "image_prev_url")
	private String imagePrevUrl;

	@NonNull
	@ColumnInfo(name = "image_detail_url")
	private String imageDetailUrl;

	@NonNull
	@ColumnInfo(name = "date_create")
	private String dateCreate;

	@NotNull
	@ColumnInfo(name = "detail_url")
	private String detailUrl;

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

	@NotNull
	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(@NotNull String detailUrl) {
		this.detailUrl = detailUrl;
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
	public String getImagePrevUrl() {
		return imagePrevUrl;
	}

	public void setImagePrevUrl(@NonNull String imagePrevUrl) {
		this.imagePrevUrl = imagePrevUrl;
	}

	@NonNull
	public String getImageDetailUrl() {
		return imageDetailUrl;
	}

	public void setImageDetailUrl(@NonNull String imageDetailUrl) {
		this.imageDetailUrl = imageDetailUrl;
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
				", dateCreate='" + dateCreate + '\'' +
				'}';
	}
}
