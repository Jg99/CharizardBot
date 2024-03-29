package com.charizardbot.main.commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;
import java.util.Random;

import com.charizardbot.main.Main;
public class JoinDate extends ListenerAdapter {
public void onMessageReceived (MessageReceivedEvent event) {
	if (event.isFromGuild()) {

		try {
    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "joindate") && !event.getAuthor().isBot()) {
        	if (event.getMessage().getMentions().getUsers().size() == 0) {
        //	String joinDate = event.getMessageAuthor().asUser().get().getJoinedAtTimestamp(event.getGuild()).toString();
			String joinDate = event.getMember().getTimeJoined().toString();
			EmbedBuilder embed = new EmbedBuilder();
          	embed.setTitle("User join date");
          	embed.addField("Join date:", joinDate, true);
          	Random rand = new Random();
          	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
          	event.getChannel().sendMessageEmbeds(embed.build()).queue();
        	} else {
				Member member = event.getGuild().getMember(event.getMessage().getMentions().getMembers().get(0).getUser());
            	String joinDate = member.getTimeJoined().toString();
             	EmbedBuilder embed = new EmbedBuilder();
              	embed.setTitle("User join date");
              	embed.addField("Join date:", joinDate, true);
              	Random rand = new Random();
              	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
              	event.getChannel().sendMessageEmbeds(embed.build()).queue();
        	}
        }
		} catch (Exception e) { Main.logger.info("WARN: Exception in JoinDate command: Insufficient permissions?n" + e); }
	}
}
}