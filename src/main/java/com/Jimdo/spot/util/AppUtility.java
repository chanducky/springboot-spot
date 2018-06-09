package com.Jimdo.spot.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.Jimdo.spot.constants.AppConstants;

/**
 * @author chandrakumar
 *
 */
public class AppUtility {
	public static ConcurrentHashMap<String,String> validateAndBuildParameterForPlcaeApi(String providerName,Map<String,String> reqParams){

		ConcurrentHashMap<String,String> result = new ConcurrentHashMap<>();
		StringBuilder queryBuilder = new StringBuilder();

		if(AppConstants.PLACE_API_PROVIDER_GOOGLE.equalsIgnoreCase(providerName)) {
			if(! reqParams.containsKey("query")) {
				result.put("status", "false");
				result.put("message","query parameter is required");
				return result;
			}
		}else {
			result.put("status", "false");
			result.put("message","Invalid place api provider name.");
			return result;
		}

		reqParams.entrySet().forEach(entry -> {
			if(queryBuilder.length() >0) {
				queryBuilder.append("&"+entry.getKey()+"="+entry.getValue());
			}else {
				queryBuilder.append(entry.getKey()+"="+entry.getValue());
			}
			
		});

		result.put("status", "true");
		result.put("params", queryBuilder.toString());

		return result;

	}
}
