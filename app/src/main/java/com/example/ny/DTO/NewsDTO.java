
package com.example.ny.DTO;

import java.util.List;

import com.example.ny.data.MultimediaItem;
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
    private List<MultimediaItem> multimedia = null;

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

    private List<MultimediaItem> getMultimedia() {
        return multimedia;
    }
}
