package com.example.ny.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NewsItem {

    /*@SerializedName("per_facet")
    private List<String> perFacet;*/

    @SerializedName("subsection")
    private String subsection;

    @SerializedName("item_type")
    private String itemType;

   /* @SerializedName("org_facet")
    private List<String> orgFacet;*/

    @SerializedName("section")
    private String section;

    @SerializedName("abstract")
    private String previewText;

    @SerializedName("related_urls")
    private List<RelatedUrlsItem> relatedUrls;

    @SerializedName("title")
    private String title;

   /* @SerializedName("des_facet")
    private List<String> desFacet;*/

    @SerializedName("url")
    private String url;

    @SerializedName("short_url")
    private String shortUrl;

    @SerializedName("material_type_facet")
    private String materialTypeFacet;

    @SerializedName("thumbnail_standard")
    private String thumbnailStandard;

    @SerializedName("multimedia")
    private List<MultimediaItem> multimedia;

    @SerializedName("geo_facet")
    private List<String> geoFacet;

    @SerializedName("updated_date")
    private String updatedDate;

    @SerializedName("created_date")
    private String createdDate;

    @SerializedName("byline")
    private String byline;

    @SerializedName("published_date")
    private String publishedDate;

    @SerializedName("kicker")
    private String kicker;

    public String getSection() {
        return section;
    }

    public String getPreviewText() {
        return previewText;
    }

    public String getTitle() {
        return title;
    }

    public List<MultimediaItem> getMultimedia() {
        return multimedia;
    }

    public String getImageByType(ImageType type)
    {
        String strImage = "http://www.glumac.com/htdocs/content/plugins/slider/images/noimage.png";

        for (MultimediaItem item : this.getMultimedia()){
            if(item.getFormat().equals(type.getDesc())) {
                strImage = item.getUrl();
                break;
            }
        }

        return strImage;
    }

    public String getPublishedDate() {
        return publishedDate;
    }


   /* @Override
    public String toString() {
        return
                "NewsItem{" +
                        "per_facet = '" + perFacet + '\'' +
                        ",subsection = '" + subsection + '\'' +
                        ",item_type = '" + itemType + '\'' +
                        ",org_facet = '" + orgFacet + '\'' +
                        ",section = '" + section + '\'' +
                        ",abstract = '" + previewText + '\'' +
                        ",related_urls = '" + relatedUrls + '\'' +
                        ",title = '" + title + '\'' +
                        ",des_facet = '" + desFacet + '\'' +
                        ",url = '" + url + '\'' +
                        ",short_url = '" + shortUrl + '\'' +
                        ",material_type_facet = '" + materialTypeFacet + '\'' +
                        ",thumbnail_standard = '" + thumbnailStandard + '\'' +
                        ",multimedia = '" + multimedia + '\'' +
                        ",geo_facet = '" + geoFacet + '\'' +
                        ",updated_date = '" + updatedDate + '\'' +
                        ",created_date = '" + createdDate + '\'' +
                        ",byline = '" + byline + '\'' +
                        ",published_date = '" + publishedDate + '\'' +
                        ",kicker = '" + kicker + '\'' +
                        "}";
    }*/
}