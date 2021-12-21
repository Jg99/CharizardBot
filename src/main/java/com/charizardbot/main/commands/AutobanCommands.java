package com.charizardbot.main.commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

import com.charizardbot.main.Main;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
public class AutobanCommands extends ListenerAdapter {
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.isFromGuild()) {

		try {
    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "autoban on") && !event.getAuthor().isBot() && ( event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
        	Main.config.setProperty("verification" + event.getGuild().getId().toString(), "1");
        	try {
        		Main.output = new FileOutputStream("server_config.cfg");
        		// set the properties value
        		Main.config.setProperty("verification" + event.getGuild().getId().toString(), "1");
        		Main.config.store(Main.output, null);
        		event.getChannel().sendMessage("Auto-ban for server is on.").queue();
        	} catch (IOException io) {
        		io.printStackTrace();
        	} finally {
        		if (Main.output != null) {
        			try {
        				Main.output.close();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        		}
        	}
        }
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "autoban off") && !event.getAuthor().isBot() && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
        	Main.config.setProperty("filter" + event.getGuild().getId().toString(), "1");
        	try {
        		Main.output = new FileOutputStream("server_config.cfg");
        		// set the properties value	
				Main.config.setProperty("verification" + event.getGuild().getId().toString(), "0");
				Main.output = new FileOutputStream("server_config.cfg");
        		Main.config.store(Main.output, null);
        		event.getChannel().sendMessage("Auto-ban for server is off.").queue();
        		
        	} catch (IOException io) {
        		io.printStackTrace();
        	} finally {
        		if (Main.output != null) {
        			try { 
        				Main.output.close();
        			} catch (IOException e) {
        				e.printStackTrace();
        			}
        		}
        	}
		}
		//set a custom time for autoban
		if (event.getMessage().getContentRaw().startsWith(prefix + "autobantime") && !event.getAuthor().isBot() && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
			String time = event.getMessage().getContentRaw().substring(13, event.getMessage().getContentRaw().length());
			int timevalue = Integer.parseInt(time.replaceAll("[^\\d.]", ""));
			String unit = time.replaceAll("[0-9]", "").toLowerCase();
			String unitSeconds = "";
			String unitString = "minute(s)";
			switch(unit) {
				case "":
				unitSeconds = Integer.toString(timevalue * 60);
				unitString = "minute(s)";
				Main.config.setProperty("banDuration" + event.getGuild().getId().toString(), unitSeconds);
				break;
				case "h":
				unitSeconds = Integer.toString(timevalue * 60 * 60);
				unitString = "hour(s)";
				Main.config.setProperty("banDuration" + event.getGuild().getId().toString(), unitSeconds);
				break;
				case "m":
				unitSeconds = Integer.toString(timevalue * 60);
				unitString = "minute(s)";
				Main.config.setProperty("banDuration" + event.getGuild().getId().toString(), unitSeconds);
				break;
				case "d":
				unitSeconds = Integer.toString(timevalue * 60 * 60 * 24);
				unitString = "day(s)";
				Main.config.setProperty("banDuration" + event.getGuild().getId().toString(), unitSeconds);
				break;
			}
			EmbedBuilder embed = new EmbedBuilder();
			Random rand = new Random();
			embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			embed.setTitle("Set auto-ban duration on your server");
			embed.addField("The bot will ban any account younger than:",timevalue + " " + unitString + ".", false);
			embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
			event.getChannel().sendMessageEmbeds(embed.build()).queue();
			Main.config.store(Main.output, null);
		}
		if (event.getMessage().getContentRaw().startsWith(prefix + "getautobantime") && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
			try {
			EmbedBuilder embed = new EmbedBuilder();
			Random rand = new Random();
			embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			embed.setTitle("Auto-ban duration on your server");
			if (Main.config.getProperty("banDuration" + event.getGuild().getId().toString()) == null) {
				Main.config.setProperty("banDuration" + event.getGuild().getId().toString(), "3600");
			}
			embed.addField("The bot will ban any account younger than:", Main.config.getProperty("banDuration" + event.getGuild().getId().toString()) + " seconds.", false);
			embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
			event.getChannel().sendMessageEmbeds(embed.build()).queue();
			} catch (Exception e) {
				event.getChannel().sendMessage("No auto-ban time is set; default is 3600s (1 hour).").queue();
			}
		}
		//AUTOBAN NICKNAME BLIST CHECK TOGGLE
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "nickautoban") && !event.getAuthor().isBot() && (event.getAuthor().getId().equals(Main.OWNER_ID) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
        	Main.output = new FileOutputStream("server_config.cfg");
        	boolean wasNull = false;
        	boolean wasChanged = false;
        	String toggle = Main.config.getProperty("nickBL" + event.getGuild().getId().toString());
        	if (toggle == null) {
        		toggle = "1";
        		Main.config.setProperty("nickBL" + event.getGuild().getId().toString(), toggle);
        		Main.config.store(Main.output, null);
        		wasNull = true;
        		wasChanged = true;
        		event.getChannel().sendMessage("No toggle was set for Nickname blacklist autoban. Set to on by default. Please run again to turn off.").queue();
        	}
        	if (!wasNull ) {
        		if (toggle.equals("0") && !wasChanged) {
        			toggle = "1";
        			wasChanged = true;
            		Main.config.setProperty("nickBL" + event.getGuild().getId().toString(), toggle);
            		Main.config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned on blacklisted nickname autoban.").queue();
        		}
        		if (toggle.equals("1") && !wasChanged) {
        			toggle = "0";
        			wasChanged = false;
            		Main.config.setProperty("nickBL" + event.getGuild().getId().toString(), toggle);
            		Main.config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned off blacklisted nickname autoban.").queue();
        		}
        	}
        	Main.config.setProperty("nickBL" + event.getGuild().getId().toString(), toggle);
        }
		} catch (Exception e) { Main.logger.info("WARN: Exception in AutoBanToggle command.\n" + e);e.printStackTrace();}
	}
}
}