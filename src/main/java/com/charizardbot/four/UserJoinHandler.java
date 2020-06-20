package com.charizardbot.four;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class UserJoinHandler extends ListenerAdapter {
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		String logChan = "";
		String svrLogging = "";
		String banDur = "";
		long banDuration = 3600; //seconds, default
		try {
    	String verificationToggle = "0"; //default value if getting property fails
    	try {
			 verificationToggle = Main.config.getProperty("verification" + event.getGuild().getId());
			 if (Main.config.getProperty("verification" + event.getGuild().getId()) == null) {
				Main.config.setProperty("verification" + event.getGuild().getId(), "0");
				Main.output = new FileOutputStream("server_config.cfg");
				Main.config.store(Main.output, null);
			 }
    	} catch (Exception e)
    	{
    	}
		if (verificationToggle.equals("1")) {
		try {
		logChan = Main.logging_config.getProperty("logchannel" + event.getGuild().getId());	
		svrLogging = Main.logging_config.getProperty("isLoggingEnabled" + event.getGuild().getId());	
		banDur = Main.config.getProperty("banDuration" + event.getGuild().getId());
		banDuration = Long.parseLong(banDur);
		} catch (Exception e) {}
		long userJoinTimestamp = event.getMember().getTimeJoined().toEpochSecond(); //seconds
		long userCreationDate = event.getUser().getTimeCreated().toEpochSecond();
		if (((userJoinTimestamp - userCreationDate) < banDuration) && !event.getUser().isBot()) {
    	//	Main.logger.info("USER " + event.getUser().getName() + ", ID:  " + event.getMember().getId() +  " IS UNDER 1 HOUR OLD, BANNING. AGE: "+ (userJoinTimestamp - userCreationDate));
		
		if (svrLogging.equals("1") && logChan != ""){
		try {
				if (event.getJDA().getTextChannelById(logChan).canTalk()) {
			EmbedBuilder embed = new EmbedBuilder();
         	embed.setTitle("New account detected");
         	embed.addField("Auto-ban triggered", ("User " + event.getUser().getName() + ", ID: " + event.getMember().getId() + " is a new account, created less than an hour ago."), true);
         	Random rand = new Random();
         	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
			event.getMember().ban(0, "Auto-banned for account age.").queue();
			event.getGuild().getTextChannelById(logChan).sendMessage(embed.build()).queue();
				}
			} catch (Exception e) {e.printStackTrace();}
			try {
				event.getMember().ban(0, "Auto-banned for account age.").queue();
			} catch (Exception e) {e.printStackTrace();}
		}
    	} else {
		}
	
	}
		} catch (Exception e) {Main.logger.info("WARN: Exception in the user join handler:\n" + e);
		e.printStackTrace();
		} 
	}
}