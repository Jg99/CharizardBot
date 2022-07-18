package com.charizardbot.main;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.Random;
import java.util.Scanner;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class UserJoinHandler extends ListenerAdapter {
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		String serverID = event.getGuild().getId().toString();
		String logChan = "";
		String svrLogging = "0";
		String banDur = "";
		long banDuration = 3600; //seconds, default
		try {
		String verificationToggle = "0"; //default value if getting property fails
		String nickBanToggle = "0"; // default value if getting property fails
    	try {
			 verificationToggle = Main.config.getProperty("verification" + serverID);
			 if (Main.config.getProperty("verification" + serverID) == null) {
				Main.config.setProperty("verification" + serverID, "0");
				Main.output = new FileOutputStream("server_config.cfg");
				Main.config.store(Main.output, null);
			 }
			} catch (Exception e){e.printStackTrace();}
			try {
			 nickBanToggle = Main.config.getProperty("nickBL" + serverID);
			 if (Main.config.getProperty("nickBL" + serverID) == null) {
				Main.config.setProperty("nickBL" + serverID, "0");
				Main.output = new FileOutputStream("server_config.cfg");
				Main.config.store(Main.output, null);
			 }
		} catch (Exception e){e.printStackTrace();}
		//Autoban blacklisted nicknames. Meant for Wiz servers.
		if (nickBanToggle.equals("1")) {
			String joinNick = event.getUser().getName().toLowerCase();
			Scanner scan = new Scanner(Main.NICK_BL.toLowerCase());
			while (scan.hasNextLine()) {
				String token = scan.nextLine();
				if (joinNick.contains(token)){
				if (!event.getUser().isBot()) {
					event.getGuild().ban(event.getUser(), 0, "Auto-banned for blacklisted username.").queue();
					verificationToggle = "0";
					try {
						logChan = Main.logging_config.getProperty("logchannel" + serverID);	
						svrLogging = Main.logging_config.getProperty("isLoggingEnabled" + serverID);	
					} catch (Exception e){System.out.println("Exception in UserJoinHandler: Nickname blacklist");}
					if (logChan != null){
					try {
						if (svrLogging.equals("1") && event.getJDA().getTextChannelById(logChan).canTalk()) {
							EmbedBuilder embed = new EmbedBuilder();
      			   			embed.setTitle("Blacklisted username detected");
      			   			embed.addField("Auto-ban triggered", ("User " + event.getUser().getName() + ", ID: " + event.getMember().getId() + " 	has a blacklisted username."), true);
      	   					Random rand = new Random();
         					embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
							embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
							event.getGuild().getTextChannelById(logChan).sendMessageEmbeds(embed.build()).queue();
						}
					} catch (Exception e) {e.printStackTrace();}
					}
				}
		}
	}
	scan.close();
		}
		if (verificationToggle.equals("1")) {
			try {
				banDur = Main.config.getProperty("banDuration" + serverID);
				System.out.println("banDur: " + banDur);
				banDuration = Long.parseLong(banDur);
				banDuration = Long.valueOf(banDur);
			} catch (Exception e) {e.printStackTrace();}
			long userJoinTimestamp = event.getMember().getTimeJoined().toEpochSecond(); //seconds
			long userCreationDate = event.getUser().getTimeCreated().toEpochSecond();
			if (((userJoinTimestamp - userCreationDate) < banDuration) && !event.getUser().isBot()) {
				event.getGuild().ban(event.getUser(), 0, "Auto-banned for account age.").queue();
				try {
					logChan = Main.logging_config.getProperty("logchannel" + serverID);	
					svrLogging = Main.logging_config.getProperty("isLoggingEnabled" + serverID);	
				} catch (Exception e){e.printStackTrace();}
				if (logChan != null){
				try {
					if (svrLogging.equals("1") && event.getJDA().getTextChannelById(logChan).canTalk()) {
						EmbedBuilder embed = new EmbedBuilder();
      			   		embed.setTitle("New account detected");
      			   		embed.addField("Auto-ban triggered", ("User " + event.getUser().getName() + ", ID: " + event.getMember().getId() + " is an account younger than the set threshold."), true);
      	   				Random rand = new Random();
         				embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
						embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
						event.getGuild().getTextChannelById(logChan).sendMessageEmbeds(embed.build()).queue();
					}
					if (event.getGuild().getId().equals("468440854886088714")) { //GTP Moderation Log for AutoBan
						event.getGuild().getTextChannelById("468502270171021312").sendMessage("User: <@" + event.getMember().getId() + "> / " + event.getMember().getId() +
						"\nAction Taken: Auto-banned by CharizardBot for being under the minimum account age threshold.").queue();
					}
				} catch (Exception e) {e.printStackTrace();}
			}
    	}
	}
		} catch (Exception e) {Main.logger.info("WARN: Exception in the user join handler:\n" + e);
		e.printStackTrace();
		} 
	}
}