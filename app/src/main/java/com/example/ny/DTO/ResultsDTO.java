
package com.example.ny.DTO;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultsDTO {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("copyright")
    @Expose
    public String copyright;
    @SerializedName("section")
    @Expose
    public String section;
    @SerializedName("last_updated")
    @Expose
    public String lastUpdated;
    @SerializedName("num_results")
    @Expose
    public Integer numResults;

    @SerializedName("results")
    @Expose
    public List<NewsDTO> results = null;

    public List<NewsDTO> getResults() {
        return results;
    }


}
