/********************************************************************
//  Clan.java       Author: Snubiss
//
//  Date: April 28, 2019
//  Modified: May 8, 2019
//
//  The Clan class is used to define all instance data for all 'Clash
//  of Clans' Clan JSON objects. A list of all clan members and related
//  data is also created upon instantiation of this class.
//
//********************************************************************/

package com.charizardbot.clashofjava;

import Exceptions.ClashException;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;


public class Clan {
    
    private String tag;
    private String name;
    private int clanLocationID;
    private String clanLocationName;
    private boolean clanLocationIsCountry;
    private String clanLocationCountry;
    private String clanIconSmall;
    private String clanIconMedium;
    private String clanIconLarge;
    private int clanLevel;
    private int clanRequiredTrophies;
    private int clanTrophies;
    private int clanVersusTrophies;
    private int clanMembers;
    private String clanWarFrequency;
    private int clanWarWins;
    private int clanWarWinStreak;
    private int clanWarTies;
    private int clanWarLosses;
    private String clanType;
    private boolean isWarLogPublic;
    private String clanDescription;
    private ArrayList<ClanMember> clanMemberList = new ArrayList<ClanMember>();
    
    public Clan(String clanTag) throws IOException, ClashException{
        
        JSONObject data = API.performAPIRequest("clans/%s", clanTag);
        
        tag = data.getString("tag");
        name = data.getString("name");
        
        try{
            clanLocationID = data.getJSONObject("location").getInt("id");
            clanLocationName = data.getJSONObject("location").getString("name");
            clanLocationCountry = data.getJSONObject("location").getString("countryCode");
            clanLocationIsCountry = data.getJSONObject("location").getBoolean("isCountry");
        }
        catch(JSONException ex){}
        
        clanIconSmall = data.getJSONObject("badgeUrls").getString("small");
        clanIconMedium = data.getJSONObject("badgeUrls").getString("medium");
        clanIconLarge = data.getJSONObject("badgeUrls").getString("large");
        clanLevel = data.getInt("clanLevel");
        clanRequiredTrophies = data.getInt("requiredTrophies");
        clanTrophies = data.getInt("clanPoints");
        clanVersusTrophies = data.getInt("clanVersusPoints");
        clanMembers = data.getInt("members");
        clanType = data.getString("type");
        isWarLogPublic = data.getBoolean("isWarLogPublic");
        clanWarFrequency = data.optString("warFrequency", null);
        clanWarWins = data.optInt("warWins", 0);
        clanWarWinStreak = data.optInt("warWinStreak", 0);
        clanWarLosses = data.optInt("warLosses", 0);
        clanWarTies = data.optInt("warTies", 0);
        clanDescription = data.optString("description", null);
        
        // Clans have these stats. Queries do not. This will help detect if our created clan is a query or not.
        try{
            for(int i=0; i< data.getJSONArray("memberList").length(); i++){
                ClanMember tempMember = new ClanMember(data.getJSONArray("memberList").getJSONObject(i));
                clanMemberList.add(i, tempMember);
            }
        }
        catch(JSONException | NullPointerException ex){}
        
    }
    
    Clan(JSONObject obData) throws IOException, ClashException{
        
        JSONObject data = API.performAPIRequest("clans/%s", obData.getString("tag"));
        
        tag = data.getString("tag");
        name = data.getString("name");
        
        try{
            clanLocationID = data.getJSONObject("location").getInt("id");
            clanLocationName = data.getJSONObject("location").getString("name");
            clanLocationCountry = data.getJSONObject("location").getString("countryCode");
            clanLocationIsCountry = data.getJSONObject("location").getBoolean("isCountry");
        }
        catch(JSONException ex){}
        
        clanIconSmall = data.getJSONObject("badgeUrls").getString("small");
        clanIconMedium = data.getJSONObject("badgeUrls").getString("medium");
        clanIconLarge = data.getJSONObject("badgeUrls").getString("large");
        clanLevel = data.getInt("clanLevel");
        clanRequiredTrophies = data.getInt("requiredTrophies");
        clanTrophies = data.getInt("clanPoints");
        clanVersusTrophies = data.getInt("clanVersusPoints");
        clanMembers = data.getInt("members");
        clanType = data.getString("type");
        isWarLogPublic = data.getBoolean("isWarLogPublic");
        
        try{
            clanWarFrequency = data.getString("warFrequency");
            clanWarWins = data.getInt("warWins");
            clanWarWinStreak = data.getInt("warWinStreak");
            clanWarLosses = data.getInt("warLosses");
            clanWarTies = data.getInt("warTies");
        }
        catch(JSONException ex){}
        
        // Need to pull data from a specific Clan API request to get member and description data.
        // This creates consistency across both Clan class object creations.
        
        clanDescription = data.optString("description", null);
        
        try{
            for(int i=0; i< data.getJSONArray("memberList").length(); i++){
                ClanMember tempMember = new ClanMember(data.getJSONArray("memberList").getJSONObject(i));
                clanMemberList.add(i, tempMember);
            }
        }
        catch(JSONException | NullPointerException ex){}
    }
    
    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public int getClanLocationID() {
        return clanLocationID;
    }

    public String getClanLocationName() {
        return clanLocationName;
    }

    public boolean isClanLocationIsCountry() {
        return clanLocationIsCountry;
    }

    public String getClanLocationCountry() {
        return clanLocationCountry;
    }

    public String getClanIconSmall() {
        return clanIconSmall;
    }

    public String getClanIconMedium() {
        return clanIconMedium;
    }

    public String getClanIconLarge() {
        return clanIconLarge;
    }

    public int getClanLevel() {
        return clanLevel;
    }

    public int getClanRequiredTrophies() {
        return clanRequiredTrophies;
    }

    public int getClanTrophies() {
        return clanTrophies;
    }

    public int getClanVersusTrophies() {
        return clanVersusTrophies;
    }

    public int getClanMembers() {
        return clanMembers;
    }

    public String getClanWarFrequency() {
        return clanWarFrequency;
    }

    public int getClanWarWins() {
        return clanWarWins;
    }

    public int getClanWarWinStreak() {
        return clanWarWinStreak;
    }

    public int getClanWarTies() {
        return clanWarTies;
    }

    public int getClanWarLosses() {
        return clanWarLosses;
    }

    public String getClanType() {
        return clanType;
    }

    public boolean isIsWarLogPublic() {
        return isWarLogPublic;
    }

    public String getClanDescription() {
        return clanDescription;
    }

    public ArrayList<ClanMember> getClanMemberList() {
        return clanMemberList;
    }
        
    @Override
    public String toString(){
        String temp =
        "Clan Tag: " + tag + "\n" +
        "Clan Name: " + name + "\n" +
        "Clan Description: " + clanDescription + "\n" +
        "Clan Type: " + clanType + "\n" +
        "Location ID: " + clanLocationID + "\n" +
        "Location Name: " + clanLocationName + "\n" +
        "Location Country: " + clanLocationCountry + "\n" +
        "Location is Country?: " + clanLocationIsCountry + "\n" +
        "Clan Icon Small: " + clanIconSmall + "\n" +
        "Clan Icon Medium: " + clanIconMedium + "\n" +
        "Clan Icon Large: " + clanIconLarge + "\n" +
        "Clan Level: " + clanLevel + "\n" +
        "Clan Trophies: " + clanTrophies + "\n" +
        "Clan Required Trophies: " + clanRequiredTrophies + "\n" +
        "Clan Versus Trophies: " + clanVersusTrophies + "\n" +
        "Clan Members: " + clanMembers + "\n" +
        "Clan War Frequency: " + clanWarFrequency + "\n" +
        "Clan War Wins: " + clanWarWins + "\n" +
        "Clan War Win Streak: " + clanWarWinStreak + "\n" +
        "Clan War Ties: " + clanWarTies + "\n" +
        "Clan War Losses: " + clanWarLosses + "\n" +
        "Clan WarLog is Public?: " + isWarLogPublic + "\n";
        
        return temp;
    }
}

