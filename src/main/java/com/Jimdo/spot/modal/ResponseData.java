package com.Jimdo.spot.modal;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;


/**
 * @author chandrakumar
 *
 */
public class ResponseData {
	
	private int code;
	private String error;
	private String message;
	private Date timestamp= new Date();
	private List<?> data;

	public ResponseData(HttpStatus status) {
		super();
		this.code = status.value();
		if(status.value() >=400 && status.value() <=500) {
			this.error = status.name();
		}else {
			this.message=status.name();
		}
	}

	
	public ResponseData(HttpStatus status,String message) {
		super();
		this.code = status.value();
		if(status.value() >=400 && status.value() <=500) {
			this.error = status.name();
		}
		this.message = message;
	}

	public ResponseData(HttpStatus status, List<?> data) {
		super();
		this.code = status.value();
		if(status.value() >=400 && status.value() <=500) {
			this.error = status.name();
		}else {
			this.message=status.name();
		}
		
		this.data = data;
	}
	
	public ResponseData(HttpStatus status, String message, List<?> data) {
		super();
		this.code = status.value();
		if(status.value() >=400 && status.value() <=500) {
			this.error = status.name();
		}
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
	
}
