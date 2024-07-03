/********************************************************************
//  ClanWar.java       Author: Snubiss
//
//  Date: April 28, 2019
//  Modified: May 8, 2019
//
//  The ClanWar class is used to define all instance data for a 'Clash
//  of Clans' clan war JSON object.
//
//********************************************************************/

package com.charizardbot.clashofjava;

import Exceptions.ClashException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;


public class ClanWar {
    
    JSONObject data;
    
    private Date warPreparationStartTime;
    private Date warStartTime;
    private Date warEndTime;
    private String warState;
    private int warTeamSize;
    
    private String clanName;
    private String clanTag;
    private String clanIconSmall;
    private String clanIconMedium;
    private String clanIconLarge;
    private int clanDestructionPercentage;
    private int clanAttacks;
    private int clanLevel;
    private int clanStars;
    private ArrayList<WarMember> clanMembers = new ArrayList<WarMember>();
    
    private String opponentClanName;
    private String opponentClanTag;
    private String opponentClanIconSmall;
    private String opponentClanIconMedium;
    private String opponentClanIconLarge;
    private int opponentClanDestructionPercentage;
    private int opponentClanAttacks;
    private int opponentClanLevel;
    private int opponentClanStars;
    private ArrayList<WarMember> opponentClanMembers = new ArrayList<WarMember>();
    
    
    
    
    
    ClanWar(String clanTagData) throws IOException, ClashException, URISyntaxException{
        
        data = API.performAPIRequest("clans/%s/currentwar", clanTagData);
        
        warState = data.getString("state");
        clanDestructionPercentage = data.getJSONObject("clan").getInt("destructionPercentage");
        clanAttacks = data.getJSONObject("clan").getInt("attacks");
        clanLevel = data.getJSONObject("clan").getInt("clanLevel");
        clanStars = data.getJSONObject("clan").getInt("stars");
        clanIconSmall = data.getJSONObject("clan").getJSONObject("badgeUrls").getString("small");
        clanIconMedium = data.getJSONObject("clan").getJSONObject("badgeUrls").getString("medium");
        clanIconLarge = data.getJSONObject("clan").getJSONObject("badgeUrls").getString("large");
        opponentClanDestructionPercentage = data.getJSONObject("opponent").getInt("destructionPercentage");
        opponentClanAttacks = data.getJSONObject("opponent").getInt("attacks");
        opponentClanLevel = data.getJSONObject("opponent").getInt("clanLevel");
        opponentClanStars = data.getJSONObject("opponent").getInt("stars");
        opponentClanIconSmall = data.getJSONObject("opponent").getJSONObject("badgeUrls").getString("small");
        opponentClanIconMedium = data.getJSONObject("opponent").getJSONObject("badgeUrls").getString("medium");
        opponentClanIconLarge = data.getJSONObject("opponent").getJSONObject("badgeUrls").getString("large");
        
        
        // Check for active War
        if (!warState.contains("notInWar")){
            // Convert the clash of clans date to a more suitable date format.
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd'T'HHmmss.SSS'Z'");

            try {
                warPreparationStartTime = df.parse(data.getString("preparationStartTime"));
                warStartTime = df.parse(data.getString("startTime"));
                warEndTime = df.parse(data.getString("endTime"));

            } catch (ParseException ex) {
                Logger.getLogger(ClanWar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            warTeamSize = data.getInt("teamSize");
            clanName = data.getJSONObject("clan").getString("name");
            clanTag = data.getJSONObject("clan").getString("tag");
            opponentClanName = data.getJSONObject("opponent").getString("name");
            opponentClanTag = data.getJSONObject("opponent").getString("tag");
            
            
            for (int i = 0; i < warTeamSize; i++){
                clanMembers.add(new WarMember(data.getJSONObject("clan").getJSONArray("members").getJSONObject(i),1));
                opponentClanMembers.add(new WarMember(data.getJSONObject("opponent").getJSONArray("members").getJSONObject(i),0));
            }
        }// End Active War Found
    }
    
    
    public Date getWarPreparationStartTime() {
        return warPreparationStartTime;
    }

    public Date getWarStartTime() {
        return warStartTime;
    }

    public Date getWarEndTime() {
        return warEndTime;
    }

    public String getWarState() {
        return warState;
    }

    public int getWarTeamSize() {
        return warTeamSize;
    }

    public String getClanName() {
        return clanName;
    }

    public String getClanTag() {
        return clanTag;
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

    public int getClanDestructionPercentage() {
        return clanDestructionPercentage;
    }

    public int getClanAttacks() {
        return clanAttacks;
    }

    public int getClanLevel() {
        return clanLevel;
    }

    public int getClanStars() {
        return clanStars;
    }

    public ArrayList<WarMember> getClanMembers() {
        return clanMembers;
    }

    public String getOpponentClanName() {
        return opponentClanName;
    }

    public String getOpponentClanTag() {
        return opponentClanTag;
    }

    public String getOpponentClanIconSmall() {
        return opponentClanIconSmall;
    }

    public String getOpponentClanIconMedium() {
        return opponentClanIconMedium;
    }

    public String getOpponentClanIconLarge() {
        return opponentClanIconLarge;
    }

    public int getOpponentClanDestructionPercentage() {
        return opponentClanDestructionPercentage;
    }

    public int getOpponentClanAttacks() {
        return opponentClanAttacks;
    }

    public int getOpponentClanLevel() {
        return opponentClanLevel;
    }

    public int getOpponentClanStars() {
        return opponentClanStars;
    }

    public ArrayList<WarMember> getOpponentClanMembers() {
        return opponentClanMembers;
    }
    
    @Override
    public String toString(){
        String temp =
        "War Prep Start Time: " + warPreparationStartTime + "\n" +
        "War Start Time: " + warStartTime + "\n" +
        "War End Time: " + warEndTime + "\n" +
        "War State: " + warState + "\n" +
        "War Team Size: " + warTeamSize + "\n" +
        "Clan Name: " + clanName + "\n" +
        "Clan Tag: " + clanTag + "\n" +
        "Clan Icon Small: " + clanIconSmall + "\n" +
        "Clan Icon Medium: " + clanIconMedium + "\n" +
        "Clan Icon Large: " + clanIconLarge + "\n" +
        "Clan Destruction Percentage: " + clanDestructionPercentage + "\n" +
        "Clan Attacks: " + clanAttacks + "\n" +
        "Clan Level: " + clanLevel + "\n" +
        "Clan Stars: " + clanStars + "\n";
        
        return temp;
    }
}
