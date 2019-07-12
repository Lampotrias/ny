package com.example.ny.data;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NewsResponse {

	@Expose
	@SerializedName("results")
	private List<NewsItem> results;

	public void setResults(List<NewsItem> results) {
		this.results = results;
	}

	public List<NewsItem> getResults() {
		return results;
	}

	@NonNull
	@Override
	public String toString() {
		return
				"NewsResponse{" +
						"results = '" + results + '\'' +
						"}";
	}
}
