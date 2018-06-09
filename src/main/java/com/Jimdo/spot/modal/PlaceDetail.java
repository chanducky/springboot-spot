package com.Jimdo.spot.modal;

import java.util.HashMap;

/**
 * fields, results of place search 
 * 
 * @author chandrakumar
 *
 */
public class PlaceDetail {
	private String id;
	private String provider;
	private String name;
	private String description;
	
	private HashMap<String,Double> location;
	private String address;
	private String uri;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public HashMap<String, Double> getLocation() {
		return location;
	}
	public void setLocation(HashMap<String, Double> location) {
		this.location = location;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
}


