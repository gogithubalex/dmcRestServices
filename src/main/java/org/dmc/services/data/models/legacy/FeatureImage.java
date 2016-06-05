package org.dmc.services.data.models.legacy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeatureImage {

    private final String logTag = FeatureImage.class.getName();
	          
    private final String thumbnail;    
    private final String large;   
    
	public FeatureImage() {
        this.thumbnail = "thumbnail";
        this.large = "large";
    }
	
    public FeatureImage(@JsonProperty("thumbnail") String thumbnail,
						@JsonProperty("large") String large) {
        this.thumbnail = thumbnail;
        this.large = large;
    }

    public String getThumbnail() {
    	return thumbnail;
    }
    
    public String getLarge() {
    	return large;
    }
}