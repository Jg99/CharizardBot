/********************************************************************
//  API.java       Author: Snubiss
//
//  Date: April 28, 2019
//  Modified: May 8, 2019
//
//  The API class is an abstract class used to perform static
//  ad-hoc queries to the Clash of Clans API.
//
//********************************************************************/

package com.charizardbot.clashofjava;

import Exceptions.ServerMaintenanceException;
import Exceptions.AuthenticationException;
import Exceptions.BadRequestException;
import Exceptions.ClashException;
import Exceptions.NotFoundException;
import Exceptions.RateLimitExceededException;
import Exceptions.UnknownErrorException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import com.charizardbot.four.Main;

import org.json.JSONObject;


public abstract class API {
    
    private static String token = Main.COC_TOKEN;
    private static final String API_BASE = "https://api.clashofclans.com/";
    private static final String API_VERSION = "v1";
    
    private static String inputStreamToString(InputStream in) throws IOException {
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        out.close();
        in.close();
        return new String(out.toByteArray());
    }
    
    public static JSONObject performAPIRequest(String format, String... arguments) throws IOException, ClashException {
    	String arguments2 = "";
        for (int i = 0; i < arguments.length; i++) {
            arguments[i] = URLEncoder.encode(arguments[0], "UTF-8");
            arguments2 += arguments[i];
        }
        String suffix = String.format(format, arguments2);
        suffix = suffix.replace(" ", "%20");
        try {
        	HttpURLConnection connection = (HttpURLConnection) new URL(API_BASE + API_VERSION + "/" + suffix).openConnection();
            //System.out.println("URL: " + API_BASE + API_VERSION + "/" + suffix);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Accept", "application/json");
            connection.addRequestProperty("authorization", "Bearer " + token);
            InputStream input = null;
            int statusCode = connection.getResponseCode();
            if (statusCode >= 200 && statusCode < 400) {
                input = connection.getInputStream();    
            } else {
                input = connection.getErrorStream();
            }

            String response = null;
            try {
                response = inputStreamToString(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject json = null;
            if (response != null && !response.isEmpty()) {
                try {
                    json = new JSONObject(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                response = response.replace("\n", "");
            }

            switch (statusCode) {
                case 400:
                    throw new BadRequestException(response);
                case 403:
                    throw new AuthenticationException(response);
                case 404:
                    throw new NotFoundException(response);
                case 429:
                    throw new RateLimitExceededException(response);
                case 500:
                    throw new UnknownErrorException(response);
                case 503:
                    throw new ServerMaintenanceException(response);
                case 200:
                    return json;
                default:
                    throw new UnknownErrorException(statusCode + ": " + response);
            }
        // Catch Bad URL's
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        // Connection failure or no connection available.
        catch (UnknownHostException ex){
           System.out.println("No connection available");
           return null;
        }
    }
    
    
    public static void setToken(String temp){
        token = temp;
    }
}
