package com.Jimdo.spot.service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.ResponseEntity;

/**
 * @author chandrakumar
 *
 */
public interface PlcaeLookupService {

	/**
	 * Search place based on query from given place search api provider.
	 * 
	 * @param reqParams
	 * @return List<ConcurrentHashMap<String, Object>>
	 */
	List<ConcurrentHashMap<String, Object>> searchPlace(HashMap<String, String> reqParams);

	/**
	 * Method return place details by placeid using google place details api
	 * 
	 * @param placeid
	 * @return ResponseEntity<String>
	 */
	ResponseEntity<String> getPlaceDtls(String placeid) ;
	
}
