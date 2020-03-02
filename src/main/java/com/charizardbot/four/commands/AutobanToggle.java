package com.charizardbot.four.commands;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.charizardbot.four.Main;
import java.io.FileOutputStream;
import java.io.IOException;
public class AutobanToggle extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		try {
		String serverID = "468440854886088714"; //GTP
		String pigswickID = "682657889613250570"; //pigswick 
    	String prefix = Main.config.getProperty(event.getGuild().getId());
    	if (prefix == null)
    		prefix = "!";
    	boolean isEnabled = false;
    	if (event.getGuild().getId().equals(serverID) || event.getGuild().getId().equals(pigswickID)) {
    		isEnabled = true;
		}
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "autoban on") && !event.getAuthor().isBot() && isEnabled && ( event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
        	Main.config.setProperty("verification" + event.getGuild().getId(), "1");
        	try {
        		Main.output = new FileOutputStream("server_config.cfg");
        		// set the properties value
        		Main.config.setProperty("verification" + event.getGuild().getId(), "1");
        		Main.config.store(Main.output, null);
        		event.getChannel().sendMessage("Verification for server is on.").queue();
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
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "autoban off") && event.getAuthor().isBot() == false && isEnabled && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
        	Main.config.setProperty("filter" + event.getGuild().getId(), "1");
        	try {
        		Main.output = new FileOutputStream("server_config.cfg");
        		// set the properties value	
        		Main.config.setProperty("verification" + event.getGuild().getId(), "0");
        		Main.config.store(Main.output, null);
        		event.getChannel().sendMessage("Autoban for server is off.").queue();
        		
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
		} catch (Exception e) { Main.logger.info("WARN: Exception in AutoBanToggle command: Insufficient permissions?\n" + e); }
	}
}