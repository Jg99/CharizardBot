package com.charizardbot.four;
import java.awt.Color;
import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class UserJoinHandler extends ListenerAdapter {
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		String logChan;
		try {
		String serverID = "468440854886088714"; //GTP
		String emporiumID = "458451155522027521"; //Wizard101 Emporium
    	String verificationToggle = "1"; //default value if getting property fails
    	try {
        	 verificationToggle = Main.config.getProperty("verification" + event.getGuild().getId());
    	} catch (Exception e)
    	{
    	}
    	boolean isEnabled;
    	if (event.getGuild().getId().equals(serverID) || event.getGuild().getId().equals(emporiumID))
    		isEnabled = true;
    	else
    		isEnabled = false;
		if (isEnabled && verificationToggle.equals("1")) {
		//	System.out.println("New user autobanning is ON");
			if (event.getGuild().getId().equals(emporiumID)) 
				logChan = "561397906570084364";
			else
				logChan = "468447207448772620"; //GTP Bot logs		
    	long userJoinTimestamp = event.getMember().getTimeJoined().toEpochSecond(); //seconds
    	long banDuration = 3600; //seconds
    	long userCreationDate = event.getUser().getTimeCreated().toEpochSecond();
    	if (((userJoinTimestamp - userCreationDate) < banDuration) && isEnabled && !event.getUser().isBot()) {
    		System.out.println("USER " + event.getUser().getName() + ", ID:  " + event.getMember().getId() +  " IS UNDER 1 HOUR OLD, BANNING. AGE: "+ (userJoinTimestamp - userCreationDate));
    		EmbedBuilder embed = new EmbedBuilder();
         	embed.setTitle("New account detected");
         	embed.addField("Auto-ban triggered", ("User " + event.getUser().getName() + ", ID: " + event.getMember().getId() + " is a new account, created less than an hour ago."), true);
         	Random rand = new Random();
         	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
         	event.getGuild().getTextChannelById(logChan).sendMessage(embed.build()).queue();
      //   	event.getUser().sendMessage("You have been automatically banned due to your account being under 1 hour old.");
			 event.getGuild().ban(event.getUser(), 0, "Auto-banned for account age.");
    	} else {
    	}
    	if (isEnabled)
    	{
    	}
		} else {
		}
		} catch (Exception e) {Main.logger.info("WARN: Exception in the user join handler:\n" + e);
		e.printStackTrace();;} 
	}
}