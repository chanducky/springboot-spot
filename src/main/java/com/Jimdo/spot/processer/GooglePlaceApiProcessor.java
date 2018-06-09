package com.Jimdo.spot.processer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.Jimdo.spot.constants.AppConstants;
import com.Jimdo.spot.modal.PlaceDetail;

/**
 * call google place api and fetch result based on query and given parameters. 
 * certain fields are read and return in response.
 * use google place textsearch api.
 * All the parameter used by google textsearch api is supported in this implementation. 
 *  
 * @author chandrakumar
 *
 */
public class GooglePlaceApiProcessor implements Callable<ConcurrentHashMap<String, Object>>{

	/**
	 * parameters received from api call. 
	 */
	ConcurrentHashMap<String, String> parameters = null;
	

	/**
	 * Search place based on query using google place textsearch api. 
	 * 
	 * @param parameters
	 */
	public GooglePlaceApiProcessor(ConcurrentHashMap<String, String> parameters) {
		super();
		this.parameters = parameters;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public ConcurrentHashMap<String, Object>  call() throws Exception {

		ConcurrentHashMap<String, Object> finalResponse = new ConcurrentHashMap<>();
		HashMap<String, Object> responseBuilderMap = new HashMap<>();
		List<PlaceDetail> places = new LinkedList<>();

		try {

			// google place textsearch api url
			StringBuffer uriBuilder = new StringBuffer(AppConstants.GOOGLE_PLACEAPI_TEXTSEARCH_URI);
			
			uriBuilder.append(this.parameters.get("params"));
			uriBuilder.append("&key="+parameters.get("key"));

			/*
			 *Call google place textsearch api 
			 */
			RestTemplate rt = new RestTemplate();
			final ResponseEntity<String> responseEntity = rt.getForEntity(uriBuilder.toString(), String.class);

			JSONObject responseJson = null;

			if(responseEntity.getStatusCode().is2xxSuccessful()) {
				String responseString = responseEntity.getBody();
				responseJson = new JSONObject(responseString); 

				if("OK".equalsIgnoreCase(responseJson.getString("status"))) {
					JSONArray results = responseJson.getJSONArray("results");
					places =  parsePlaceDetail(results);
				}

				if(responseJson.has("next_page_token")) {
					responseBuilderMap.put("next_page_token", responseJson.getString("next_page_token"));
				}

				responseBuilderMap.put("status", responseJson.getString("status"));
				responseBuilderMap.put("results", places);
			}else {
				responseBuilderMap.put("status", responseEntity.getStatusCode().name());
			}

		}catch(Exception ex) {
			responseBuilderMap.put("status", "TIMEOUT/UNKNOWN ERROR");
			ex.getStackTrace();
		}

		finalResponse.put(AppConstants.PLACE_API_PROVIDER_GOOGLE, responseBuilderMap);

		return finalResponse;
	}

	
	/**
	 * Parse response JSON from google place api and fetch required field and build list of place
	 * 
	 * @param results
	 * @return List<PlaceDetail>
	 * @throws JSONException
	 */
	private List<PlaceDetail> parsePlaceDetail(JSONArray results) throws JSONException {
		List<PlaceDetail> placeDetailList =new LinkedList<>();

		if( results==null || results.length() ==0) {
			return placeDetailList;
		}

		PlaceDetail placeDetail =null;

		for(int i=0;i< results.length();i++) {
			placeDetail =new PlaceDetail();
			placeDetail.setProvider(AppConstants.PLACE_API_PROVIDER_GOOGLE);

			JSONObject jo = results.getJSONObject(i);

			if(jo.has("id")) {
				placeDetail.setId(jo.getString("id"));
			}
			if(jo.has("name")) {
				placeDetail.setName(jo.getString("name"));
			}
			if(jo.has("formatted_address")) {
				placeDetail.setAddress(jo.getString("formatted_address"));
			}

			if(jo.has("geometry")) {
				JSONObject location = jo.getJSONObject("geometry").getJSONObject("location");
				LinkedHashMap<String, Double> locationMap = new LinkedHashMap<>();
				locationMap.put("lat", location.getDouble("lat"));
				locationMap.put("lng", location.getDouble("lng"));
				placeDetail.setLocation(locationMap);
			}

			placeDetail.setDescription(placeDetail.getName() +", "+ placeDetail.getAddress());


			placeDetailList.add(placeDetail);
		}

		return placeDetailList;
	}



}
