/********************************************************************
//  Attack.java       Author: Snubiss
//
//  Date: April 28, 2019
//  Modified: May 8, 2019
//
//  The Attack class is used to define instance data for war attacks.
//  This class is called directly from the warMember class.
//
//********************************************************************/
//testing commit
package com.charizardbot.clashofjava;

import org.json.JSONObject;

public class Attack {
    private final String attackerTag;
    private final String defenderTag;
    private final int stars;
    private final int destructionPercentage;
    private final int order;
    
    
    Attack(){
        
        attackerTag = null;
        defenderTag = null;
        stars = 0;
        destructionPercentage = 0;
        order = 0;
    }
    
    Attack(JSONObject data){
        
        attackerTag = data.optString("attackerTag");
        defenderTag = data.optString("defenderTag");
        stars = data.optInt("stars");
        destructionPercentage = data.optInt("destructionPercentage");
        order = data.optInt("order");
    }
    
    
    public String getAttackerTag() {
        return attackerTag;
    }

    public String getDefenderTag() {
        return defenderTag;
    }

    public int getStars() {
        return stars;
    }

    public int getDestructionPercentage() {
        return destructionPercentage;
    }

    public int getOrder() {
        return order;
    }
    
    @Override
    public String toString(){
        
        String temp =
        "Attacker Tag: " + attackerTag + "\n" +
        "Defender Tag: " + defenderTag + "\n" +
        "Stars: " + stars + "\n" +
        "Destruction Percentage: " + destructionPercentage + "%" + "\n" +
        "Order: " + order + "\n";
        
        return temp;
    }
}
