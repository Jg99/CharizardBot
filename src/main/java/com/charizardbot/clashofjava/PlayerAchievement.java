/********************************************************************
//  PlayerAchievement.java       Author: Snubiss
//
//  Date: April 28, 2019
//  Modified: May 8, 2019
//
//  The PlayerAchievement class is used to define instance data for a player's
//  achievements. This class is called directly from the Player class. This
//  class should not be instantiated directly.
//
//********************************************************************/

package com.charizardbot.clashofjava;

import org.json.JSONObject;


public class PlayerAchievement {
    
    private final String name;
    private final int stars;
    private final int value;
    private final int target;
    private final String info;
    private final String completionInfo;
    private final String village;
    
    PlayerAchievement(JSONObject data){
        
        //System.out.println(data);
        name = data.optString("name", "None");
        stars = data.optInt("stars", 0);
        value = data.optInt("value", 0);
        target = data.optInt("target", 0);
        info = data.optString("info", "None");
        completionInfo = data.optString("completionInfo", "None");
        village = data.optString("village", "None");
    }
    
    public String getName() {
        return name;
    }

    public int getStars() {
        return stars;
    }

    public int getValue() {
        return value;
    }

    public int getTarget() {
        return target;
    }

    public String getInfo() {
        return info;
    }

    public String getCompletionInfo() {
        return completionInfo;
    }

    public String getVillage() {
        return village;
    }
    
    public String toString(){
        
        String temp =
        "Name: " + name + "\n" +
        "Village: " + village + "\n" +
        "Stars: " + stars + "\n" +
        "Value: " + value + "\n" +
        "Target: " + target + "\n" +
        "Info: " + info + "\n" +
        "Completion Info: " + completionInfo + "\n";
        
        return temp;
    }
}
