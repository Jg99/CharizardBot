/********************************************************************
//  Player.java       Author: Snubiss
//
//  Date: April 28, 2019
//  Modified: May 8, 2019
//
//  The Player class is used to define all instance data for a 'Clash
//  of Clans' player search query JSON object. This class is used to 
//  perform various player queries against the Clash of Clash API. This
//  class instantiates the playerAchievement, playerHero, playerSpell and
//  playerTroop classes to create lists of the player's data. The
//  results of the query will be returned as a 'Player' object. 
//
//********************************************************************/

package com.charizardbot.clashofjava;

import Exceptions.ClashException;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;


public class Player {
    
    private JSONObject data;
    private String tag;
    private String name;
    private int expLevel;
    private int leagueID;
    private String leagueName;
    private String leagueIconSmall;
    private String leagueIconMedium;
    private String leagueIconLarge;
    private int trophies;
    private int versusTrophies;
    private int attackWins;
    private int defenseWins;
    private String clanTag;
    private String clanName;
    private int clanLevel;
    private String clanIconSmall;
    private String clanIconMedium;
    private String clanIconLarge;
    private int bestTrophies;
    private int donations;
    private int donationsReceived;
    private int warStars;
    private String role;
    private int townHallLevel;
    private int builderHallLevel;
    private int bestVersusTrophies;
    private int versusBattleWins;
    private ArrayList<PlayerAchievement> achievements = new ArrayList<PlayerAchievement>();
    private ArrayList<PlayerTroop> troops = new ArrayList<PlayerTroop>();
    private ArrayList<PlayerHero> heroes = new ArrayList<PlayerHero>();
    private ArrayList<PlayerSpell> spells = new ArrayList<PlayerSpell>();
    
    public Player(String playerTag) throws IOException, ClashException{
    
        try{
            data = API.performAPIRequest("players/%s", playerTag);
            
            tag = data.getString("tag");
            name = data.getString("name");
            expLevel = data.getInt("expLevel");
            
            try{
                leagueID = data.getJSONObject("league").optInt("id", 0);
                leagueName = data.optJSONObject("league").getString("name");
                leagueIconSmall = data.optJSONObject("league").getJSONObject("iconUrls").getString("tiny");
                leagueIconMedium = data.optJSONObject("league").getJSONObject("iconUrls").getString("small");
                leagueIconLarge = data.optJSONObject("league").getJSONObject("iconUrls").getString("medium");
            }
            catch(JSONException ex){
            }
            trophies = data.getInt("trophies");
            versusTrophies = data.getInt("versusTrophies");
            attackWins = data.getInt("attackWins");
            defenseWins = data.getInt("defenseWins");
            
            try{
                clanTag = data.getJSONObject("clan").getString("tag");
                clanName = data.getJSONObject("clan").getString("name");
                clanLevel = data.getJSONObject("clan").getInt("clanLevel");
                clanIconSmall = data.getJSONObject("clan").getJSONObject("badgeUrls").getString("small");
                clanIconMedium = data.getJSONObject("clan").getJSONObject("badgeUrls").getString("medium");
                clanIconLarge = data.getJSONObject("clan").getJSONObject("badgeUrls").getString("large");
                role = data.getString("role");
            }
            catch(JSONException ex){
            }
            
            bestTrophies = data.getInt("bestTrophies");
            donations = data.getInt("donations");
            donationsReceived = data.getInt("donationsReceived");
            warStars = data.getInt("warStars");
            
            townHallLevel = data.getInt("townHallLevel");
            builderHallLevel = data.getInt("builderHallLevel");
            bestVersusTrophies = data.getInt("bestVersusTrophies");
            versusBattleWins = data.getInt("versusBattleWins");
            
            // ACHIEVEMENTS
            for (int i = 0; i < data.getJSONArray("achievements").length(); i++){
                achievements.add(new PlayerAchievement(data.getJSONArray("achievements").getJSONObject(i)));
            }
            
            // PLAYER TROOPS
            for (int i = 0; i < data.getJSONArray("troops").length(); i++){
                troops.add(new PlayerTroop(data.getJSONArray("troops").getJSONObject(i)));
            }
            
            // PLAYER HEROES
            for (int i = 0; i < data.getJSONArray("heroes").length(); i++){
                heroes.add(new PlayerHero(data.getJSONArray("heroes").getJSONObject(i)));
            }
            
            // PLAYER SPELLS
            for (int i = 0; i < data.getJSONArray("spells").length(); i++){
                spells.add(new PlayerSpell(data.getJSONArray("spells").getJSONObject(i)));
            }
            
        }
        catch (Exceptions.NotFoundException ex){
            System.out.println("Player does not exist. Please check the player ID entered.");
        }
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

    public int getLeagueID() {
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

    public String getLeagueIconLarge() {
        return leagueIconLarge;
    }

    public int getTrophies() {
        return trophies;
    }

    public int getVersusTrophies() {
        return versusTrophies;
    }

    public int getAttackWins() {
        return attackWins;
    }

    public int getDefenseWins() {
        return defenseWins;
    }

    public String getClanTag() {
        return clanTag;
    }

    public String getClanName() {
        return clanName;
    }

    public int getClanLevel() {
        return clanLevel;
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

    public int getBestTrophies() {
        return bestTrophies;
    }

    public int getDonations() {
        return donations;
    }

    public int getDonationsReceived() {
        return donationsReceived;
    }

    public int getWarStars() {
        return warStars;
    }

    public String getRole() {
        return role;
    }

    public int getTownHallLevel() {
        return townHallLevel;
    }

    public int getBuilderHallLevel() {
        return builderHallLevel;
    }

    public int getBestVersusTrophies() {
        return bestVersusTrophies;
    }

    public int getVersusBattleWins() {
        return versusBattleWins;
    }

    public ArrayList<PlayerAchievement> getAchievements() {
        return achievements;
    }

    public ArrayList<PlayerTroop> getTroops() {
        return troops;
    }

    public ArrayList<PlayerHero> getHeroes() {
        return heroes;
    }

    public ArrayList<PlayerSpell> getSpells() {
        return spells;
    }
    
    @Override
    public String toString(){
        
        String temp =
        "Tag: " + tag + "\n" +
        "Name: " + name + "\n" +
        "Exp Level: " + expLevel + "\n" +
        "League ID: " + leagueID + "\n" +
        "League Name: " + leagueName + "\n" +
        "League Icon Small: " + leagueIconSmall + "\n" +
        "League Icon Medium: " + leagueIconMedium + "\n" +
        "League Icon Large: " + leagueIconLarge + "\n" +
        "Trophies: " + trophies + "\n" +
        "Versus Trophies: " + versusTrophies + "\n" +
        "Attack Wins: " + attackWins + "\n" +
        "Clan Tag: " + clanTag + "\n" +
        "Clan Name: " + clanName + "\n" +
        "Clan Level: " + clanLevel + "\n" +
        "Clan Icon Small: " + clanIconSmall + "\n" +
        "Clan Icon Medium: " + clanIconMedium + "\n" +
        "Clan Icon Large: " + clanIconLarge + "\n" +
        "Best Trophies: " + bestTrophies + "\n" +
        "Donations: " + donations + "\n" +
        "Donations Received: " + donationsReceived + "\n" +
        "War Stars: " + warStars + "\n" +
        "Role: " + role + "\n" +
        "Town Hall Level: " + townHallLevel + "\n" +
        "Build Hall Level: " + builderHallLevel + "\n" +
        "Best Versus Trophies: " + bestVersusTrophies + "\n" +
        "Versus Battle Wins: " + versusBattleWins + "\n";
        return temp;
    }
}
