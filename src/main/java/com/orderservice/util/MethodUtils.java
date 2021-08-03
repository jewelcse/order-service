package com.orderservice.util;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.sql.Date;


public class MethodUtils {


	private MethodUtils() {
	}

		public static String prepareErrorJSON(HttpStatus status, Exception ex) {
	    	JSONObject errorJSON=new JSONObject();
	    	try {
				errorJSON.put("success","False");
				errorJSON.put("message",ex.getMessage());
		    	errorJSON.put("status_code",status.value());
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
	    	
	    	return errorJSON.toString();
	}

		public static Object prepareErrorJSON(HttpStatus status, String localizedMessage) {
			JSONObject errorJSON=new JSONObject();
	    	System.out.println("MethodUtils");
	    	try {
				errorJSON.put("success","False");
				errorJSON.put("message","Invalid input");
		    	errorJSON.put("status_code",status.value());
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
	    	
	    	return errorJSON.toString();
		}

	public static Date getDate() {
		long millis=System.currentTimeMillis();
		Date date=new Date(millis);
		return date;
	}

}
