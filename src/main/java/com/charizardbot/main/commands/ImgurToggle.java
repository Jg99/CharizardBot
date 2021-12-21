package com.charizardbot.main.commands;
import java.io.FileOutputStream;

import com.charizardbot.main.Main;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class ImgurToggle extends ListenerAdapter{
	public void onMessageReceived (MessageReceivedEvent event) {
		if (event.isFromGuild()) {

		try {
    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "toggleimgur") && !event.getAuthor().isBot() && (event.getAuthor().getId().equals(Main.OWNER_ID) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
        	Main.output = new FileOutputStream("server_config.cfg");
        	boolean wasNull = false;
        	boolean wasChanged = false;
        	String toggle = Main.config.getProperty("imgurCmd" + event.getGuild().getId().toString());
        	if (toggle == null) {
        		toggle = "1";
        		Main.config.setProperty("imgurCmd" + event.getGuild().getId().toString(), toggle);
        		Main.config.store(Main.output, null);
        		wasNull = true;
        		wasChanged = true;
        		event.getChannel().sendMessage("No toggle was set for Imgur Commands. Set to on by default. Please run again to turn off.").queue();
        	}
        	if (!wasNull ) {
        		if (toggle.equals("0") && !wasChanged) {
        			toggle = "1";
        			wasChanged = true;
            		Main.config.setProperty("imgurCmd" + event.getGuild().getId().toString(), toggle);
            		Main.config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned on Imgur search commands.").queue();
        		}
        		if (toggle.equals("1") && !wasChanged) {
        			toggle = "0";
        			wasChanged = false;
            		Main.config.setProperty("imgurCmd" + event.getGuild().getId().toString(), toggle);
            		Main.config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned off Imgur search commands.").queue();
        		}
        	}
        	Main.config.setProperty("imgurCmd" + event.getGuild().getId().toString(), toggle);
        }
		} catch (Exception e){Main.logger.info("Error in Imgur toggle command. Insufficient permissions?\n" + e);}
	}
}
}