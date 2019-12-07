/********************************************************************
//  WarMember.java       Author: Snubiss
//
//  Date: April 26, 2019
//  Modified: May 8, 2019
//
//  The WarMember class is used to define instance data for war attacks.
//  This class contains clan member data while in a war.
//
//********************************************************************/

package com.charizardbot.clashofjava;

import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;


public class WarMember {
    
    private String tag;
    private String name;
    private String team =null;
    private int townHallLevel;
    private int mapPosition;
    private ArrayList<Attack> attacks = new ArrayList<Attack>();
    private int opponentAttacks;
    private Attack bestOpponentAttack;
    
    
    // This will be initiated automatically when creating a LeagueWar or LeagueGame
    // This can only be created useing a JSONObject from the 'members' array in the clash api.
    public WarMember(JSONObject data, int side){
        try{
        tag = data.getString("tag");
        }
        catch(JSONException ex){
        }
        
        name = data.getString("name");
        townHallLevel = data.getInt("townhallLevel");
        mapPosition = data.getInt("mapPosition");
        opponentAttacks = data.getInt("opponentAttacks");
        
        for (int i = 0; i < 2; i++){
            try{
                attacks.add(new Attack(data.getJSONArray("attacks").getJSONObject(i)));
            }
            catch(JSONException ex){
                attacks.add(new Attack());
            }
        }
        for (int i = 0; i < 1; i++){
            try{
                bestOpponentAttack = new Attack(data.getJSONObject("bestOpponentAttack"));
            }
            catch(JSONException ex){
                bestOpponentAttack = new Attack();
            }
        }
        
        
        if (side == 1){
            team = "Home";
        }
        else{
            team = "Away";
        }
    }
    
    
    
    @Override
    public String toString(){
        String temp =
        "Tag: " + tag + "\n" +
        "Name: " + name + "\n" +
        "Team: " + team + "\n" +
        "Town Hall Level: " + townHallLevel + "\n" +
        "Map Position: " + mapPosition + "\n\n" +
        "Attacks: " + attacks.size() + "\n\n" +
        "Opponent Attacks: " + opponentAttacks + "\n\n" +
        "Best Opponent Attack: \n" + bestOpponentAttack.toString() + "\n";

        return temp;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public int getTownHallLevel() {
        return townHallLevel;
    }

    public int getMapPosition() {
        return mapPosition;
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public int getOpponentAttacks() {
        return opponentAttacks;
    }

    public Attack getBestOpponentAttacks() {
        return bestOpponentAttack;
    }
}
