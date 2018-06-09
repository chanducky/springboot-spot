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
import org.springframework.stereotype.Service;

import com.Jimdo.spot.constants.AppConstants;
import com.Jimdo.spot.constants.AppPropperties;
import com.Jimdo.spot.exception.BadRequestException;
import com.Jimdo.spot.processer.GooglePlaceApiProcessor;
import com.Jimdo.spot.util.AppUtility;


@Service
public class PlcaeLookupServiceImpl implements PlcaeLookupService{
	
	@Autowired
	AppPropperties appPropperties;
	
	@Override
	public List<ConcurrentHashMap<String, Object>> searchPlace(HashMap<String,String> reqParams){
		
		List<ConcurrentHashMap<String, Object>> finalResult =new LinkedList<>();
		
		ExecutorService executor = Executors.newWorkStealingPool();
		List<Future<ConcurrentHashMap<String, Object>>> futureList = new LinkedList<>();
		
		for(String  providerName: appPropperties.getPlaceApiProviders()) {
			
			ConcurrentHashMap<String, String> result = AppUtility.validateAndBuildParameterForPlcaeApi(providerName, reqParams);
			
			if("true".equalsIgnoreCase(result.get("status"))) {
				if(AppConstants.PLACE_API_PROVIDER_GOOGLE.equalsIgnoreCase(providerName)) {
					result.put("key", appPropperties.getGoogleApiKey());
					
					GooglePlaceApiProcessor googlePlaceSearchingTask = new GooglePlaceApiProcessor(result);
					Future<ConcurrentHashMap<String, Object>> fo = executor.submit(googlePlaceSearchingTask);
					futureList.add(fo);
				}
				
				// call other api provider like Yelp etc in else if condition
				
			}else {
				throw new BadRequestException(result.get("message"));
			}
		}
		
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
	
	
	
}
