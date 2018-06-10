package com.Jimdo.spot.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Jimdo.spot.constants.AppConstants;
import com.Jimdo.spot.constants.AppPropperties;
import com.Jimdo.spot.exception.BadRequestException;
import com.Jimdo.spot.processer.GooglePlaceApiProcessor;
import com.Jimdo.spot.util.AppUtility;


/**
 * service to search place from different place api.
 * 
 * @author chandrakumar
 *
 */
@Service
public class PlcaeLookupServiceImpl implements PlcaeLookupService{
	
	/**
	 * Property file bean
	 */
	@Autowired
	AppPropperties appPropperties;
	
	/* (non-Javadoc)
	 * @see com.Jimdo.spot.service.PlcaeLookupService#searchPlace(java.util.HashMap)
	 */
	@Override
	public List<ConcurrentHashMap<String, Object>> searchPlace(HashMap<String,String> reqParams){
		
		
		List<ConcurrentHashMap<String, Object>> finalResult =new LinkedList<>();
		
		// create thread pool based no of core of processor used by machine. 
		ExecutorService executor = Executors.newWorkStealingPool();
		
		List<Future<ConcurrentHashMap<String, Object>>> futureList = new LinkedList<>();
		
		// iteration over used place api provider
		for(String  providerName: appPropperties.getPlaceApiProviders()) {
			
			//build parameter base on provider name
			ConcurrentHashMap<String, String> result = AppUtility.validateAndBuildParameterForPlcaeApi(providerName, reqParams);
			
			if("true".equalsIgnoreCase(result.get("status"))) {
				if(AppConstants.PLACE_API_PROVIDER_GOOGLE.equalsIgnoreCase(providerName)) {
					result.put("key", appPropperties.getGoogleApiKey());
					result.put("context_path", ServletUriComponentsBuilder.fromCurrentContextPath().toUriString());
					
					GooglePlaceApiProcessor googlePlaceSearchingTask = new GooglePlaceApiProcessor(result);
					
					Future<ConcurrentHashMap<String, Object>> fo = executor.submit(googlePlaceSearchingTask);
					futureList.add(fo);
				}
			}else {
				throw new BadRequestException(result.get("message"));
			}
		}
		
		// Collect all the search result
		for(Future<ConcurrentHashMap<String, Object>>  fo : futureList) {
			try {
				finalResult.add(fo.get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		executor.shutdown();
		
		return finalResult;
		
	}
	
	/* (non-Javadoc)
	 * @see com.Jimdo.spot.service.PlcaeLookupService#getPlaceDtls(java.lang.String)
	 */
	@Override
	public ResponseEntity<String>  getPlaceDtls(String placeid){
		
		StringBuffer uriBuilder = new StringBuffer(AppConstants.GOOGLE_PLACE_DETAILS_API_URI);
		
		uriBuilder.append("&key="+appPropperties.getGoogleApiKey());
		uriBuilder.append("&placeid="+placeid);

		/*
		 *Call google place details api 
		 */
		RestTemplate rt = new RestTemplate();
		final ResponseEntity<String> responseEntity = rt.getForEntity(uriBuilder.toString(), String.class);
		
		return responseEntity;
	}
	
	
}
