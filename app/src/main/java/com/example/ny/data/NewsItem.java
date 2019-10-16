package com.example.ny.data;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NewsItem {

    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //private final String noImageUrl = "http://www.glumac.com/htdocs/content/plugins/slider/images/noimage.png";
    private final String noImageUrl = "https://images.squarespace-cdn.com/content/v1/5c590782fd67932345578da8/1555397697745-72CTNERMZGQVH3IRDXNW/ke17ZwdGBToddI8pDm48kBj6cSKTxOtsOD0_pLNALzNZw-zPPgdn4jUwVcJE1ZvWQUxwkmyExglNqGp0IvTJZUJFbgE-7XRK3dMEBRBhUpwx9iYN7JS2RffJg2KS8Btv6O5MsxWvOsIWg8TvrrZk4xqfQhasIc600mZrnQN4idI/black-square.jpg?format=500w";

    public NewsItem() {
        //imageDetailUrl = imagePrevUrl = noImageUrl;
    }

    @SerializedName("section")
    private String section;

    private String imagePrevUrl = "";
    private String imageDetailUrl = "";;

    @SerializedName("abstract")
    private String previewText;

    @SerializedName("title")
    private String title;

    @SerializedName("short_url")
    private String shortUrl;

    @SerializedName("multimedia")
    private List<MultimediaItem> multimedia;

    @SerializedName("published_date")
    private String publishedDate;

    public void setSection(String section) {
        this.section = section;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getSection() {
        return section;
    }

    public String getPreviewText() {
        return previewText;
    }

    public void setImagePrevUrl(String imageUrl) {
        this.imagePrevUrl = imageUrl;
    }


    public void setImageDetailUrl(String imageDetailUrl) {
        this.imageDetailUrl = imageDetailUrl;
    }

    public String getImagePrevUrl() {
        if (!imagePrevUrl.isEmpty())
            return imagePrevUrl;
        return getImageByFormat("mediumThreeByTwo210");
    }

    public String getImageDetailUrl() {
        if (!imageDetailUrl.isEmpty())
            return imageDetailUrl;
        return getImageByFormat("superJumbo");
    }

    private String getImageByFormat(final String format){
        List<MultimediaItem> multimediaItems = getMultimedia();
        if (multimediaItems != null) {
            for (MultimediaItem multimediaItem : multimediaItems) {
                if (multimediaItem.getFormat().equals(format)) {
                    return multimediaItem.getUrl();
                }
            }
        }
        return this.noImageUrl;
    }



    public String getTitle() {
        return title;
    }

    private List<MultimediaItem> getMultimedia() {
        return multimedia;
    }

    @NotNull
    @Override
    public String toString() {
        return "NewsItem{" +
                "section='" + section + '\'' +
                ", previewText='" + previewText + '\'' +
                ", title='" + title + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", multimedia=" + multimedia +
                ", publishedDate='" + publishedDate + '\'' +
                '}';
    }

    public String getPublishedDate() {
        return publishedDate;
    }
}