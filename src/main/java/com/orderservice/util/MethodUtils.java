package com.orderservice.util;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.util.Random;


public class MethodUtils {


	private MethodUtils() {
	}

	public static String generateOrderId(){
		int size=10;
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(size);
		for (int i = 0; i < size; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
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
