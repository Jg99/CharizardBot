package com.charizardbot.four;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class UserJoinHandler extends ListenerAdapter {
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		System.out.println("NEW MEMBER JOIN: " + event.getUser().getId());
		String serverID = event.getGuild().getId().toString();
		String logChan = "";
		String svrLogging = "0";
		String banDur = "";
		long banDuration = 3600; //seconds, default
		try {
    	String verificationToggle = "0"; //default value if getting property fails
    	try {
			 verificationToggle = Main.config.getProperty("verification" + serverID);
			 if (Main.config.getProperty("verification" + serverID) == null) {
				Main.config.setProperty("verification" + serverID, "0");
				Main.output = new FileOutputStream("server_config.cfg");
				Main.config.store(Main.output, null);
			 }
    	} catch (Exception e)
    	{
			e.printStackTrace();
    	}
		if (verificationToggle.equals("1")) {
		try {
		logChan = Main.logging_config.getProperty("logchannel" + serverID.toString());	
		svrLogging = Main.logging_config.getProperty("isLoggingEnabled" + serverID);	
		banDur = Main.config.getProperty("banDuration" + serverID);
		banDuration = Long.parseLong(banDur);
		} catch (Exception e) {e.printStackTrace();}
		long userJoinTimestamp = event.getMember().getTimeJoined().toEpochSecond(); //seconds
		long userCreationDate = event.getUser().getTimeCreated().toEpochSecond();
		System.out.println("Log channel: " + logChan + "\nisLoggingEnabled: " + svrLogging + "\nbanDuration: " + banDur +
		"\njoinTime: " + userJoinTimestamp + "\nCreation Date: " + userCreationDate);
		if (((userJoinTimestamp - userCreationDate) < banDuration) && !event.getUser().isBot()) {
    	//	Main.logger.info("USER " + event.getUser().getName() + ", ID:  " + event.getMember().getId() +  " IS UNDER 1 HOUR OLD, BANNING. AGE: "+ (userJoinTimestamp - userCreationDate));
		if (logChan != null){
		try {
			if (svrLogging.equals("1") && event.getJDA().getTextChannelById(logChan).canTalk()) {
			EmbedBuilder embed = new EmbedBuilder();
         	embed.setTitle("New account detected");
         	embed.addField("Auto-ban triggered", ("User " + event.getUser().getName() + ", ID: " + event.getMember().getId() + " is a new account, created less than an hour ago."), true);
         	Random rand = new Random();
         	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
			event.getGuild().getTextChannelById(logChan).sendMessage(embed.build()).queue();
				}
			} catch (Exception e) {e.printStackTrace();}
		//	event.getMember().ban(0, "Auto-banned for account age.").queue();
			event.getGuild().ban(event.getUser(), 0, "Auto-banned for account age.").queue();
		}
    	} else {
			event.getGuild().ban(event.getUser(), 0, "Auto-banned for account age.").queue(); //ban even if logging channel is null
		}
	
	}
		} catch (Exception e) {Main.logger.info("WARN: Exception in the user join handler:\n" + e);
		e.printStackTrace();
		} 
	}
}