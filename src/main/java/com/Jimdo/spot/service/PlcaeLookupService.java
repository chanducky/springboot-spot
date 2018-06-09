package com.Jimdo.spot.service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
	
}
