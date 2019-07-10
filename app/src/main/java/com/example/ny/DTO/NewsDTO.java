
package com.example.myapplication.DTO;

import java.util.Date;
import java.util.List;

import com.bumptech.glide.util.Util;
import com.example.myapplication.Utils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsDTO {

    final static String NO_IMAGE_PATH = "https://zabavnik.club/wp-content/uploads/Kartinka_9_26040230-768x768.jpg";

    @SerializedName("section")
    @Expose
    private String section;

    @SerializedName("subsection")
    @Expose
    private String subsection;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("abstract")
    @Expose
    private String _abstract;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("byline")
    @Expose
    private String byline;

    @SerializedName("item_type")
    @Expose
    private String itemType;

    @SerializedName("updated_date")
    @Expose
    private String updatedDate;

    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("published_date")
    @Expose
    private String publishedDate;

    @SerializedName("material_type_facet")
    @Expose
    private String materialTypeFacet;

    @SerializedName("kicker")
    @Expose
    private String kicker;

    @SerializedName("des_facet")
    @Expose
    private List<String> desFacet = null;

    @SerializedName("org_facet")
    @Expose
    private List<String> orgFacet = null;

    @SerializedName("per_facet")
    @Expose
    private List<String> perFacet = null;

    @SerializedName("geo_facet")
    @Expose
    private List<String> geoFacet = null;

    @SerializedName("multimedia")
    @Expose
    private List<MultimediaDTO> multimedia = null;

    @SerializedName("short_url")
    @Expose
    private String shortUrl;

    public String getUrl() {
        return url;
    }

    public String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }

    public String get_abstract() {
        return _abstract;
    }

    public Date getPublishedDate() {
        return Utils.getFormatedDate(publishedDate);
    }

    public String getSubsection() {
        return subsection;
    }

    public String getSrcThumbnail(){
        String path = null;
        List<MultimediaDTO> multimedia = getMultimedia();

        for (MultimediaDTO obj : multimedia){
            if (obj.getFormat().equals("Normal"))
            {
                path = obj.getUrl();
            }
        }
        if (path == null)
            path = NO_IMAGE_PATH;

        return path;
    }

    public String getSrcThumbnailByType (String itemType){
        String path = null;
        List<MultimediaDTO> multimedia = getMultimedia();

        for (MultimediaDTO obj : multimedia){
            if (obj.getFormat().equals(itemType))
            {
                path = obj.getUrl();
            }
        }
        if (path == null)
            path = NO_IMAGE_PATH;

        return path;
    }

    private List<MultimediaDTO> getMultimedia() {
        return multimedia;
    }
}
