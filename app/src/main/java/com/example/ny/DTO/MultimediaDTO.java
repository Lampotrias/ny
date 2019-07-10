
package com.example.myapplication.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MultimediaDTO {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("format")
    @Expose
    private String format;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("copyright")
    @Expose
    private String copyright;

    public String getFormat() {
        return format;
    }

    public String getUrl() {
        return url;
    }
}
