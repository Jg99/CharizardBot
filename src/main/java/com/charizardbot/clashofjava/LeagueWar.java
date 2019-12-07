/********************************************************************
//  LeagueWar.java       Author: Snubiss
//
//  Date: April 25, 2019
//  Modified: May 8, 2019
//
//  The LeagueWar class is used to define all instance data for a 'Clash
//  of Clans' league game war JSON object. This class is instantiated through
//  the LeagueGame class. This class should not be instantiated directly.
//
//********************************************************************/

package com.charizardbot.clashofjava;

import Exceptions.ClashException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;


public class LeagueWar {
    
    private JSONObject data;
    private String state;
    private int teamSize;
    private Date preparationStartTime;
    private Date startTime;
    private Date endTime;
    private String clan1Tag;
    private String clan1Name;
    private int clan1Level;
    private int clan1Attacks;
    private int clan1Stars;
    private int clan1Destruction;
    private final ArrayList<WarMember> clan1Members = new ArrayList<WarMember>();
    private String clan2Tag;
    private String clan2Name;
    private int clan2Level;
    private int clan2Attacks;
    private int clan2Stars;
    private int clan2Destruction;
    private final ArrayList<WarMember> clan2Members = new ArrayList<WarMember>();
    
    LeagueWar(){
        
    }
    
    LeagueWar(String warTag) throws IOException, ClashException{
        
        data = API.performAPIRequest("clanwarleagues/wars/%s", warTag);
        state = data.getString("state");
        teamSize = data.getInt("teamSize");
        clan1Tag = data.getJSONObject("clan").getString("tag");
        clan1Name = data.getJSONObject("clan").getString("name");
        clan1Level = data.getJSONObject("clan").getInt("clanLevel");
        clan1Attacks = data.getJSONObject("clan").getInt("attacks");
        clan1Stars = data.getJSONObject("clan").getInt("stars");
        clan1Destruction = data.getJSONObject("clan").getInt("destructionPercentage");
        clan2Tag = data.getJSONObject("opponent").getString("tag");
        clan2Name = data.getJSONObject("opponent").getString("name");
        clan2Level = data.getJSONObject("opponent").getInt("clanLevel");
        clan2Attacks = data.getJSONObject("opponent").getInt("attacks");
        clan2Stars = data.getJSONObject("opponent").getInt("stars");
        clan2Destruction = data.getJSONObject("opponent").getInt("destructionPercentage");
        
        // Convert the clash of clans date to a more suitable date format.
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss.SSS'Z'");

        try {
            preparationStartTime = df.parse(data.getString("preparationStartTime"));
            startTime = df.parse(data.getString("startTime"));
            endTime = df.parse(data.getString("endTime"));

        } catch (ParseException ex) {
            Logger.getLogger(ClanWar.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        for (int i = 0; i < data.getJSONObject("clan").getJSONArray("members").length(); i++){
            clan1Members.add(new WarMember(data.getJSONObject("clan").getJSONArray("members").getJSONObject(i),1));
        }
        
        for (int i = 0; i < data.getJSONObject("opponent").getJSONArray("members").length(); i++){
            clan2Members.add(new WarMember(data.getJSONObject("opponent").getJSONArray("members").getJSONObject(i),2));
        }
    }
    
    
    public String getState() {
        return state;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public Date getPreparationStartTime() {
        return preparationStartTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getClan1Tag() {
        return clan1Tag;
    }

    public String getClan1Name() {
        return clan1Name;
    }

    public int getClan1Level() {
        return clan1Level;
    }

    public int getClan1Attacks() {
        return clan1Attacks;
    }

    public int getClan1Stars() {
        return clan1Stars;
    }

    public int getClan1Destruction() {
        return clan1Destruction;
    }

    public ArrayList<WarMember> getClan1Members() {
        return clan1Members;
    }

    public String getClan2Tag() {
        return clan2Tag;
    }

    public String getClan2Name() {
        return clan2Name;
    }

    public int getClan2Level() {
        return clan2Level;
    }

    public int getClan2Attacks() {
        return clan2Attacks;
    }

    public int getClan2Stars() {
        return clan2Stars;
    }

    public int getClan2Destruction() {
        return clan2Destruction;
    }

    public ArrayList<WarMember> getClan2Members() {
        return clan2Members;
    }
    
    @Override
    public String toString(){
        String temp =
        "State: " + state + "\n" +
        "Team Size: " + teamSize + "\n" +
        "Prep Start Time: " + preparationStartTime + "\n" +
        "Start Time: " + startTime + "\n" +
        "End Time: " + endTime + "\n" +
        "Clan 1 Tag: " + clan1Tag + "\n" +
        "Clan 1 Name: " + clan1Name + "\n" +
        "Clan 1 Level: " + clan1Level + "\n" +
        "Clan 1 Attacks: " + clan1Attacks + "\n" +
        "Clan 1 Stars: " + clan1Stars + "\n" +
        "Clan 1 Destruction: " + clan1Destruction + "%" + "\n\n" +
        "Clan 2 Tag: " + clan2Tag + "\n" +
        "Clan 2 Name: " + clan2Name + "\n" +
        "Clan 2 Level: " + clan2Level + "\n" +
        "Clan 2 Attacks: " + clan2Attacks + "\n" +
        "Clan 2 Stars: " + clan2Stars + "\n" +
        "Clan 2 Destruction: " + clan2Destruction + "%" + "\n";
        return temp;
    }
}
