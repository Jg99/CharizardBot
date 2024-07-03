/********************************************************************
//  ClanQuery.java       Author: Snubiss
//
//  Date: April 28, 2019
//  Modified: May 8, 2019
//
//  The ClanQuery class is used to define all instance data for a 'Clash
//  of Clans' clan search query JSON objects. This is an abstract class
//  used to perform various queries against the Clash of Clash API. The
//  results of the query will be returned as a 'Clan' class type list.
//
//********************************************************************/

package com.charizardbot.clashofjava;

import Exceptions.BadRequestException;
import Exceptions.ClashException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.json.JSONObject;


public abstract class ClanQuery {
    
    private static int maxResults = 3;
    
    public static ArrayList<Clan> queryName(String clanName) throws ClashException, IOException, URISyntaxException {
        
        JSONObject data = null;
        ArrayList<Clan> resultList = new ArrayList<Clan>();
        
        try {
            String command = String.format("clans?name=%s&limit=%s", clanName, String.valueOf(maxResults));
            data = API.performAPIRequest(command);
            
            for(int i = 0; i < data.getJSONArray("items").length(); i++){
                Clan temp = new Clan(data.getJSONArray("items").getJSONObject(i));
                resultList.add(temp);
                
            }
        }
        catch(BadRequestException ex){
            System.out.println("Invalid Input");
        }
        
    return resultList;
    }
    
    public static ArrayList<Clan> queryWarFrequency(String clanWarFrequency) throws ClashException, IOException, URISyntaxException{
        
        JSONObject data = null;
        ArrayList<Clan> resultList = new ArrayList<Clan>();
        
        try{
            String command = String.format("clans?warFrequency=%s&limit=%s", clanWarFrequency, String.valueOf(maxResults));
            data = API.performAPIRequest(command);
            for(int i = 0; i < data.getJSONArray("items").length(); i++){
                Clan temp = new Clan(data.getJSONArray("items").getJSONObject(i));
                resultList.add(temp);
                
            }
            
        }
        catch(BadRequestException ex){
            System.out.println("Invalid Input");
        }
       
    return resultList;
    }
    
    public static ArrayList<Clan> queryLocationID(String locationID) throws ClashException, IOException, URISyntaxException{
        
        JSONObject data = null;
        ArrayList<Clan> resultList = new ArrayList<Clan>();
        
        try{
            data = API.performAPIRequest("clans?locationID=%s", locationID);
            for(int i = 0; i < data.getJSONArray("items").length(); i++){
                Clan temp = new Clan(data.getJSONArray("items").getJSONObject(i));
                resultList.add(temp);
            }
        }
        catch(BadRequestException ex){
            System.out.println("Invalid Input");
        }
        
    return resultList;
    }
    
    public static ArrayList<Clan> queryMinimumMembers(String minMembers) throws ClashException, IOException, URISyntaxException{
        
        JSONObject data = null;
        ArrayList<Clan> resultList = new ArrayList<Clan>();
        
        try{
            data = API.performAPIRequest("clans?minMembers=%s", minMembers);
            for(int i = 0; i < data.getJSONArray("items").length(); i++){
                Clan temp = new Clan(data.getJSONArray("items").getJSONObject(i));
                resultList.add(temp);
            }
        }
        catch(BadRequestException ex){
            System.out.println("Invalid Input");
        }
        
    return resultList;
    }
    
    public static ArrayList<Clan> queryMaximumMembers(String maxMembers) throws ClashException, IOException, URISyntaxException{
        
        JSONObject data = null;
        ArrayList<Clan> resultList = new ArrayList<Clan>();
        
        try{
            data = API.performAPIRequest("clans?maxMembers=%s", maxMembers);
            for(int i = 0; i < data.getJSONArray("items").length(); i++){
                Clan temp = new Clan(data.getJSONArray("items").getJSONObject(i));
                resultList.add(temp);
            }
        }
        catch(BadRequestException ex){
            System.out.println("Invalid Input");
        }
        
    return resultList;
    }
    
    public static ArrayList<Clan> queryMinimumClanTrophies(String minTrophies) throws ClashException, IOException, URISyntaxException{
        
        JSONObject data = null;
        ArrayList<Clan> resultList = new ArrayList<Clan>();
        
        try{
            data = API.performAPIRequest("clans?minClanPoints=%s", minTrophies);
            for(int i = 0; i < data.getJSONArray("items").length(); i++){
                Clan temp = new Clan(data.getJSONArray("items").getJSONObject(i));
                resultList.add(temp);
            }
        }
        catch(BadRequestException ex){
            System.out.println("Invalid Input");
        }
        
    return resultList;
    }
    
    public static ArrayList<Clan> queryMinimumClanLevel(String minClanLevel) throws ClashException, IOException, URISyntaxException{
        
        JSONObject data = null;
        ArrayList<Clan> resultList = new ArrayList<Clan>();
        
        try{
            data = API.performAPIRequest("clans?minClanLevel=%s", minClanLevel);
            for(int i = 0; i < data.getJSONArray("items").length(); i++){
                Clan temp = new Clan(data.getJSONArray("items").getJSONObject(i));
                resultList.add(temp);
            }
        }
        catch(BadRequestException ex){
            System.out.println("Invalid Input");
        }
        
    return resultList;
    }

    public static void setMaxResults(int intData) {
        maxResults = intData;
    }
    
    public static int getMaxResults() {
        return maxResults;
    }
    
    
}
