package com.example.ny.data;

public enum ImageType {
	STANDART_THUMBNAIL("Standard Thumbnail"),
	THUMBLARGE("thumbLarge");

	private String strType;
	ImageType(String strType) {
		this.strType=strType;
	}

	public String getDesc() {
		return strType;
	}
}
