package com.charizardbot.four.commands;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.charizardbot.four.Main;
public class ImgurSearch extends ListenerAdapter {
public void onGuildMessageReceived (GuildMessageReceivedEvent event) {
    	String imgurCmd = "1";
    	if (Main.config.getProperty("imgurCmd" + event.getGuild().getId().toString()) != null) {
    	imgurCmd = Main.config.getProperty("imgurCmd" + event.getGuild().getId().toString());
    	}
		try {
    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "imgursearch") && !event.getAuthor().isBot() && imgurCmd.equals("1")) {//&& event.getGuild().getId().toString().equals("468440854886088714")) { 
        		Main.imgur.searchImage(event.getMessage().getContentRaw().substring(13, event.getMessage().getContentRaw().length()));
        		String imgurlink = Main.imgur.returnImageInfo();
        		if (Main.imgur.isError == false) {
        			event.getChannel().sendMessage("Here's the first image from your search term:\n" + imgurlink).queue();
        		}	else {
        			event.getChannel().sendMessage("Error 404: Image not found").queue();
        		}
        	}
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "randimgur") && !event.getAuthor().isBot() && imgurCmd.equals("1")) {// && event.getGuild().getId().toString().equals("468440854886088714")) {
    		Main.imgur.searchRandImage(event.getMessage().getContentRaw().substring(11, event.getMessage().getContentRaw().length()));
    		String imagelink = Main.imgur.returnImageInfo();
    		if (Main.imgur.isError == false) {
    			event.getChannel().sendMessage("Here's a random image from your search term:\n" + imagelink).queue();
    		} else {
    			event.getChannel().sendMessage("Error 404: Image not found.").queue(); }
        	}
		} catch (Exception e) { Main.logger.info("WARN: Exception in the Imgur Search command.\n" + e);}
	}
}