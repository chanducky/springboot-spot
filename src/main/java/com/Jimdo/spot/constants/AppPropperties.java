package com.Jimdo.spot.constants;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Read property file and set in bean property which is start with spot.
 * 
 * @author chandrakumar
 *
 */
@Configuration
@ConfigurationProperties("spot")
public class AppPropperties {

	/**
	 * google api key to use google place api
	 */
	@NotBlank
	private String googleApiKey;
	
	/**
	 * List of place api provider which is being used
	 */
	@NotNull
	private List<String> placeApiProviders;
	
	public String getGoogleApiKey() {
		return googleApiKey;
	}
	public void setGoogleApiKey(String googleApiKey) {
		this.googleApiKey = googleApiKey;
	}
	public List<String> getPlaceApiProviders() {
		return placeApiProviders;
	}
	public void setPlaceApiProviders(List<String> placeApiProviders) {
		this.placeApiProviders = placeApiProviders;
	}
	
}
