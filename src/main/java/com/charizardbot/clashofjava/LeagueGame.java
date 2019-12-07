/********************************************************************
//  LeagueGame.java       Author: Snubiss
//
//  Date: April 25, 2019
//  Modified: May 8, 2019
//
//  The LeagueGame class is used to define all instance data for a 'Clash
//  of Clans' league game JSON object.
//
//********************************************************************/

package com.charizardbot.clashofjava;

import Exceptions.ClashException;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONObject;


public class LeagueGame {
    
    private String state;
    private String season;
    private String round1WarTag1;
    private String round1WarTag2;
    private String round1WarTag3;
    private String round1WarTag4;
    private String round2WarTag1;
    private String round2WarTag2;
    private String round2WarTag3;
    private String round2WarTag4;
    private String round3WarTag1;
    private String round3WarTag2;
    private String round3WarTag3;
    private String round3WarTag4;
    private String round4WarTag1;
    private String round4WarTag2;
    private String round4WarTag3;
    private String round4WarTag4;
    private String round5WarTag1;
    private String round5WarTag2;
    private String round5WarTag3;
    private String round5WarTag4;
    private String round6WarTag1;
    private String round6WarTag2;
    private String round6WarTag3;
    private String round6WarTag4;
    private String round7WarTag1;
    private String round7WarTag2;
    private String round7WarTag3;
    private String round7WarTag4;
    private ArrayList<String> round1TagArray = new ArrayList<String>();
    private ArrayList<String> round2TagArray = new ArrayList<String>();
    private ArrayList<String> round3TagArray = new ArrayList<String>();
    private ArrayList<String> round4TagArray = new ArrayList<String>();
    private ArrayList<String> round5TagArray = new ArrayList<String>();
    private ArrayList<String> round6TagArray = new ArrayList<String>();
    private ArrayList<String> round7TagArray = new ArrayList<String>();
    
    
    private LeagueWar round1 = new LeagueWar();
    private LeagueWar round2 = new LeagueWar();
    private LeagueWar round3 = new LeagueWar();
    private LeagueWar round4 = new LeagueWar();
    private LeagueWar round5 = new LeagueWar();
    private LeagueWar round6 = new LeagueWar();
    private LeagueWar round7 = new LeagueWar();
    private ArrayList<LeagueWar> roundList = new ArrayList<LeagueWar>();
    
    
    LeagueGame(String tag) throws IOException, ClashException{
        
        try{
        JSONObject data = API.performAPIRequest("clans/%s/currentwar/leaguegroup", tag);
        
        state = data.getString("state");
        season = data.getString("season");
        round1WarTag1 = data.getJSONArray("rounds").getJSONObject(0).getJSONArray("warTags").get(0).toString();
        round1WarTag2 = data.getJSONArray("rounds").getJSONObject(0).getJSONArray("warTags").get(1).toString();
        round1WarTag3 = data.getJSONArray("rounds").getJSONObject(0).getJSONArray("warTags").get(2).toString();
        round1WarTag4 = data.getJSONArray("rounds").getJSONObject(0).getJSONArray("warTags").get(3).toString();
        round1TagArray.add(round1WarTag1);
        round1TagArray.add(round1WarTag2);
        round1TagArray.add(round1WarTag3);
        round1TagArray.add(round1WarTag4);
        
        round2WarTag1 = data.getJSONArray("rounds").getJSONObject(1).getJSONArray("warTags").get(0).toString();
        round2WarTag2 = data.getJSONArray("rounds").getJSONObject(1).getJSONArray("warTags").get(1).toString();
        round2WarTag3 = data.getJSONArray("rounds").getJSONObject(1).getJSONArray("warTags").get(2).toString();
        round2WarTag4 = data.getJSONArray("rounds").getJSONObject(1).getJSONArray("warTags").get(3).toString();
        round2TagArray.add(round2WarTag1);
        round2TagArray.add(round2WarTag2);
        round2TagArray.add(round2WarTag3);
        round2TagArray.add(round2WarTag4);
        
        round3WarTag1 = data.getJSONArray("rounds").getJSONObject(2).getJSONArray("warTags").get(0).toString();
        round3WarTag2 = data.getJSONArray("rounds").getJSONObject(2).getJSONArray("warTags").get(1).toString();
        round3WarTag3 = data.getJSONArray("rounds").getJSONObject(2).getJSONArray("warTags").get(2).toString();
        round3WarTag4 = data.getJSONArray("rounds").getJSONObject(2).getJSONArray("warTags").get(3).toString();
        round3TagArray.add(round3WarTag1);
        round3TagArray.add(round3WarTag2);
        round3TagArray.add(round3WarTag3);
        round3TagArray.add(round3WarTag4);
        
        round4WarTag1 = data.getJSONArray("rounds").getJSONObject(3).getJSONArray("warTags").get(0).toString();
        round4WarTag2 = data.getJSONArray("rounds").getJSONObject(3).getJSONArray("warTags").get(1).toString();
        round4WarTag3 = data.getJSONArray("rounds").getJSONObject(3).getJSONArray("warTags").get(2).toString();
        round4WarTag4 = data.getJSONArray("rounds").getJSONObject(3).getJSONArray("warTags").get(3).toString();
        round4TagArray.add(round4WarTag1);
        round4TagArray.add(round4WarTag2);
        round4TagArray.add(round4WarTag3);
        round4TagArray.add(round4WarTag4);
        
        round5WarTag1 = data.getJSONArray("rounds").getJSONObject(4).getJSONArray("warTags").get(0).toString();
        round5WarTag2 = data.getJSONArray("rounds").getJSONObject(4).getJSONArray("warTags").get(1).toString();
        round5WarTag3 = data.getJSONArray("rounds").getJSONObject(4).getJSONArray("warTags").get(2).toString();
        round5WarTag4 = data.getJSONArray("rounds").getJSONObject(4).getJSONArray("warTags").get(3).toString();
        round5TagArray.add(round5WarTag1);
        round5TagArray.add(round5WarTag2);
        round5TagArray.add(round5WarTag3);
        round5TagArray.add(round5WarTag4);
        
        round6WarTag1 = data.getJSONArray("rounds").getJSONObject(5).getJSONArray("warTags").get(0).toString();
        round6WarTag2 = data.getJSONArray("rounds").getJSONObject(5).getJSONArray("warTags").get(1).toString();
        round6WarTag3 = data.getJSONArray("rounds").getJSONObject(5).getJSONArray("warTags").get(2).toString();
        round6WarTag4 = data.getJSONArray("rounds").getJSONObject(5).getJSONArray("warTags").get(3).toString();
        round6TagArray.add(round6WarTag1);
        round6TagArray.add(round6WarTag2);
        round6TagArray.add(round6WarTag3);
        round6TagArray.add(round6WarTag4);
        
        round7WarTag1 = data.getJSONArray("rounds").getJSONObject(6).getJSONArray("warTags").get(0).toString();
        round7WarTag2 = data.getJSONArray("rounds").getJSONObject(6).getJSONArray("warTags").get(1).toString();
        round7WarTag3 = data.getJSONArray("rounds").getJSONObject(6).getJSONArray("warTags").get(2).toString();
        round7WarTag4 = data.getJSONArray("rounds").getJSONObject(6).getJSONArray("warTags").get(3).toString();
        round7TagArray.add(round7WarTag1);
        round7TagArray.add(round7WarTag2);
        round7TagArray.add(round7WarTag3);
        round7TagArray.add(round7WarTag4);
        
        if (!round1WarTag1.equalsIgnoreCase("#0")){
            for (int i = 0; i < round1TagArray.size(); i++){
                LeagueWar temp = new LeagueWar(round1TagArray.get(i));
                if ((temp.getClan1Tag().equalsIgnoreCase(tag) || temp.getClan2Tag().equalsIgnoreCase(tag))){
                    round1 = temp;
                    roundList.add(round1);
                }
            }
        }
        if (!round2WarTag1.equalsIgnoreCase("#0")){
            for (int i = 0; i < round2TagArray.size(); i++){
                LeagueWar temp = new LeagueWar(round2TagArray.get(i));
                if ((temp.getClan1Tag().equalsIgnoreCase(tag) || temp.getClan2Tag().equalsIgnoreCase(tag))){
                    round2 = temp;
                    roundList.add(round2);
                }
            }
        }
        if (!round3WarTag1.equalsIgnoreCase("#0")){
            for (int i = 0; i < round3TagArray.size(); i++){
                LeagueWar temp = new LeagueWar(round3TagArray.get(i));
                if ((temp.getClan1Tag().equalsIgnoreCase(tag) || temp.getClan2Tag().equalsIgnoreCase(tag))){
                    round3 = temp;
                    roundList.add(round3);
                }
            }
        }
        if (!round4WarTag1.equalsIgnoreCase("#0")){
            for (int i = 0; i < round4TagArray.size(); i++){
                LeagueWar temp = new LeagueWar(round4TagArray.get(i));
                if ((temp.getClan1Tag().equalsIgnoreCase(tag) || temp.getClan2Tag().equalsIgnoreCase(tag))){
                    round4 = temp;
                    roundList.add(round4);
                }
            }
        }
        if (!round5WarTag1.equalsIgnoreCase("#0")){
            for (int i = 0; i < round5TagArray.size(); i++){
                LeagueWar temp = new LeagueWar(round5TagArray.get(i));
                if ((temp.getClan1Tag().equalsIgnoreCase(tag) || temp.getClan2Tag().equalsIgnoreCase(tag))){
                    round5 = temp;
                    roundList.add(round5);
                }
            }
        }
        if (!round6WarTag1.equalsIgnoreCase("#0")){
            for (int i = 0; i < round6TagArray.size(); i++){
                LeagueWar temp = new LeagueWar(round6TagArray.get(i));
                if ((temp.getClan1Tag().equalsIgnoreCase(tag) || temp.getClan2Tag().equalsIgnoreCase(tag))){
                    round6 = temp;
                    roundList.add(round6);
                }
            }
        }
        if (!round7WarTag1.equalsIgnoreCase("#0")){
            for (int i = 0; i < round7TagArray.size(); i++){
                LeagueWar temp = new LeagueWar(round7TagArray.get(i));
                if ((temp.getClan1Tag().equalsIgnoreCase(tag) || temp.getClan2Tag().equalsIgnoreCase(tag))){
                    round7 = temp;
                    roundList.add(round7);
                }
            }
        }
    }
    catch(Exceptions.NotFoundException ex){
        System.out.println("No League Game Available at this time");
        }
    }
    
    public LeagueWar getRound1(){
        return round1;
    }
    
    public LeagueWar getRound2(){
        return round2;
    }
    
    public LeagueWar getRound3(){
        return round3;
    }
    
    public LeagueWar getRound4(){
        return round4;
    }
    
    public LeagueWar getRound5(){
        return round5;
    }
    
    public LeagueWar getRound6(){
        return round6;
    }
    
    public LeagueWar getRound7(){
        return round7;
    }
    
    public ArrayList<LeagueWar> getRoundList(){
        return roundList;
    }
    
    @Override
    public String toString(){
        String temp =
        "State: " + state + "\n" +
        "Season: " + season + "\n\n" +
        "Round1WarTag1: " + round1WarTag1 + "\n" +
        "Round1WarTag2: " + round1WarTag2 + "\n" +
        "Round1WarTag3: " + round1WarTag3 + "\n" +
        "Round1WarTag4: " + round1WarTag4 + "\n\n" +
        "Round2WarTag1: " + round2WarTag1 + "\n" +
        "Round2WarTag2: " + round2WarTag2 + "\n" +
        "Round2WarTag3: " + round2WarTag3 + "\n" +
        "Round2WarTag4: " + round2WarTag4 + "\n\n" +
        "Round3WarTag1: " + round3WarTag1 + "\n" +
        "Round3WarTag2: " + round3WarTag2 + "\n" +
        "Round3WarTag3: " + round3WarTag3 + "\n" +
        "Round3WarTag4: " + round3WarTag4 + "\n\n" +
        "Round4WarTag1: " + round4WarTag1 + "\n" +
        "Round4WarTag2: " + round4WarTag2 + "\n" +
        "Round4WarTag3: " + round4WarTag3 + "\n" +
        "Round4WarTag4: " + round4WarTag4 + "\n\n" +
        "Round5WarTag1: " + round5WarTag1 + "\n" +
        "Round5WarTag2: " + round5WarTag2 + "\n" +
        "Round5WarTag3: " + round5WarTag3 + "\n" +
        "Round5WarTag4: " + round5WarTag4 + "\n\n" +
        "Round6WarTag1: " + round6WarTag1 + "\n" +
        "Round6WarTag2: " + round6WarTag2 + "\n" +
        "Round6WarTag3: " + round6WarTag3 + "\n" +
        "Round6WarTag4: " + round6WarTag4 + "\n\n" +
        "Round7WarTag1: " + round7WarTag1 + "\n" +
        "Round7WarTag2: " + round7WarTag2 + "\n" +
        "Round7WarTag3: " + round7WarTag3 + "\n" +
        "Round7WarTag4: " + round7WarTag4 + "\n\n";
        return temp;
    }
}
