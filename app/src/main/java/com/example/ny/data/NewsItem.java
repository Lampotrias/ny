package com.example.ny.data;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewsItem {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @SerializedName("section")
    private String section;

    private String imageUrl = "";

    @SerializedName("abstract")
    private String previewText;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

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

    public String getSection() {
        return section;
    }

    public String getPreviewText() {
        return previewText;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getTitle() {
        return title;
    }

    private List<MultimediaItem> getMultimedia() {
        return multimedia;
    }

    public String getImageByType(ImageType type)
    {
        if (this.imageUrl.isEmpty()){
            String strImage = "http://www.glumac.com/htdocs/content/plugins/slider/images/noimage.png";

            for (MultimediaItem item : this.getMultimedia()){
                if(item.getFormat().equals(type.getDesc())) {
                    strImage = item.getUrl();
                    break;
                }
            }

            return strImage;
        }
        else{
            return this.imageUrl;
        }
    }

    @NotNull
    @Override
    public String toString() {
        return "NewsItem{" +
                "section='" + section + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", previewText='" + previewText + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", multimedia=" + multimedia +
                ", publishedDate='" + publishedDate + '\'' +
                '}';
    }

    public String getPublishedDate() {
        return publishedDate;
    }
}