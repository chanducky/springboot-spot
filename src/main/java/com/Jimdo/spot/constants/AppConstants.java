package com.Jimdo.spot.constants;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author chandrakumar
 *
 */
public interface AppConstants {
	
	String PLACE_API_PROVIDER_GOOGLE="GOOGLE";
	
	//String GOOGLE_PLACEAPI_NEARBYSEARCH_URI = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	String GOOGLE_PLACEAPI_TEXTSEARCH_URI ="https://maps.googleapis.com/maps/api/place/textsearch/json?";
	
	HashSet<String> requiredGoogleTextSearchParams = new HashSet<>(Arrays.asList("query"));
	HashSet<String> optionalGoogletextSearchParams = new HashSet<>(Arrays.asList("region","location","radius","language","minprice and maxprice","opennow","pagetoken","type"));
	
	String GOOGLE_PLACE_DETAILS_API_URI="https://maps.googleapis.com/maps/api/place/details/json?";
}
