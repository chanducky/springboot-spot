package com.Jimdo.spot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Jimdo.spot.modal.ResponseData;
import com.Jimdo.spot.service.PlcaeLookupService;


@RestController
@RequestMapping("api/place")
public class PlaceLookupRestController {

	PlcaeLookupService plcaeLookupService;

	public PlaceLookupRestController(PlcaeLookupService plcaeLookupService) {
		this.plcaeLookupService = plcaeLookupService;
	}

	@RequestMapping(value="/search", method = RequestMethod.GET)
	public ResponseEntity<Object> serachPlace(@RequestParam HashMap<String,String> allRequestParams) {

		List<ConcurrentHashMap<String, Object>> results =  plcaeLookupService.searchPlace(allRequestParams);
		ResponseData responseData =  new ResponseData(HttpStatus.OK, results);
		
		return new ResponseEntity<Object>(responseData,HttpStatus.OK);

	}

}
