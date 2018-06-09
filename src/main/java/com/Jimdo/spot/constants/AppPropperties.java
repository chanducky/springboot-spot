package com.Jimdo.spot.constants;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spot")
public class AppPropperties {

	@NotBlank
	private String googleApiKey;
	
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
