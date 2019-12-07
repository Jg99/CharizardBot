/********************************************************************
//  ClanMember.java       Author: Snubiss
//
//  Date: April 28, 2019
//  Modified: May 8, 2019
//
//  The ClanMember class is used to define all instance data for all 'Clash
//  of Clans' Clan Member JSON objects. This class is instantiated directly
//  from the 'Clan' class. This class should not be called with user tags.
//  Instead, use the 'Player' class for individual searches.
//
//********************************************************************/

package com.charizardbot.clashofjava;

import org.json.JSONObject;


public class ClanMember {
    
    private final String tag;
    private final String name;
    private final int expLevel;
    private final String leagueID;
    private final String leagueName;
    private final String leagueIconSmall;
    private final String leagueIconMedium;
    private final int trophies;
    private final int versusTrophies;
    private final String role;
    private final int clanRank;
    private final int previousClanRank;
    private final int donations;
    private final int donationsReceived;
    
    ClanMember(JSONObject data){
    
        tag = data.getString("tag");
        name = data.getString("name");
        expLevel = data.getInt("expLevel");
        leagueID = data.getJSONObject("league").get("id").toString();
        leagueName = data.getJSONObject("league").get("name").toString();
        leagueIconSmall = data.getJSONObject("league").getJSONObject("iconUrls").get("tiny").toString();
        leagueIconMedium = data.getJSONObject("league").getJSONObject("iconUrls").get("small").toString();
        trophies = data.getInt("trophies");
        versusTrophies = data.getInt("versusTrophies");
        role = data.getString("role");
        clanRank = data.getInt("clanRank");
        previousClanRank = data.getInt("previousClanRank");
        donations = data.getInt("donations");
        donationsReceived = data.getInt("donationsReceived");
    }
    
    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public int getExpLevel() {
        return expLevel;
    }

    public String getLeagueID() {
        return leagueID;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public String getLeagueIconSmall() {
        return leagueIconSmall;
    }

    public String getLeagueIconMedium() {
        return leagueIconMedium;
    }

    public int getTrophies() {
        return trophies;
    }

    public int getVersusTrophies() {
        return versusTrophies;
    }

    public String getRole() {
        return role;
    }

    public int getClanRank() {
        return clanRank;
    }

    public int getPreviousClanRank() {
        return previousClanRank;
    }

    public int getDonations() {
        return donations;
    }

    public int getDonationsReceived() {
        return donationsReceived;
    }
    
    @Override
    public String toString(){
        String temp =
        "Tag: " + tag + "\n" +
        "Name: " + name + "\n" +
        "XP Level: " + expLevel + "\n" +
        "League ID: " + leagueID + "\n" +
        "League Name: " + leagueName + "\n" +
        "League Icon Small: " + leagueIconSmall + "\n" +
        "League Icon Medium: " + leagueIconSmall + "\n" +
        "Trophies: " + trophies + "\n" +
        "Versus Trophies: " + versusTrophies + "\n" +
        "Role: " + role + "\n" +
        "Clan Rank: " + clanRank + "\n" +
        "Prev Clan Rank: " + previousClanRank + "\n" +
        "Donations In: " + donationsReceived + "\n" +
        "Donations Out: " + donations + "\n";
        
        return temp;
    }
}
