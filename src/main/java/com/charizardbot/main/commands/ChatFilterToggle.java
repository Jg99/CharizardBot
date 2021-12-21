package com.charizardbot.main.commands;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.charizardbot.main.Main;
public class ChatFilterToggle extends ListenerAdapter {
	public String channelID = "";
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.isFromGuild()) {

		try {
    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "chatfilter on") && !event.getAuthor().isBot() && ( event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
        	Main.config.setProperty("filter" + event.getGuild().getId().toString(), "1");
        	try {
        		Main.output = new FileOutputStream("server_config.cfg");        			
        		Main.config.setProperty("filter" + event.getGuild().getId().toString(), "1");
        		Main.config.store(Main.output, null);
        		event.getChannel().sendMessage("Chat filter for your server is on.").queue();
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
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "chatfilter off") && !event.getAuthor().isBot() && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
        	Main.config.setProperty("filter" + event.getGuild().getId().toString(), "1");
        	try {
        		Main.output = new FileOutputStream("server_config.cfg");        			
        		Main.config.setProperty("filter" + event.getGuild().getId().toString(), "0");
        		Main.config.store(Main.output, null);
        		event.getChannel().sendMessage("Chat filter for your server is off.").queue();
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
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "channelfilter on") && !event.getAuthor().isBot() && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
        	Main.config.setProperty("filter" + event.getGuild().getId().toString(), "1");
        	try {
				Main.output = new FileOutputStream("server_config.cfg");  
				      			
        		Main.config.setProperty("chanfilter" + event.getChannel().getId(), "1");
        		Main.config.store(Main.output, null);
        		event.getChannel().sendMessage("Chat filter for your channel is on.").queue();
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
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "channelfilter off") && !event.getAuthor().isBot() && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
        	Main.config.setProperty("filter" + event.getGuild().getId().toString(), "1");
        	try {
        		Main.output = new FileOutputStream("server_config.cfg");        			
        		Main.config.setProperty("chanfilter" + event.getChannel().getId(), "0");
        		Main.config.store(Main.output, null);
        		event.getChannel().sendMessage("Chat filter for your channel is off.").queue();
        		
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
		// toggle logging
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "togglelogs") && !event.getAuthor().isBot() && (event.getAuthor().getId().equals(Main.OWNER_ID) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
        	Main.output = new FileOutputStream("logConfig.cfg");
        	boolean wasNull = false;
        	boolean wasChanged = false;
        	String toggle = Main.logging_config.getProperty("isLoggingEnabled" + event.getGuild().getId().toString());
        	if (toggle == null) {
        		toggle = "1";
        		Main.logging_config.setProperty("isLoggingEnabled" + event.getGuild().getId().toString(), toggle);
        		Main.logging_config.store(Main.output, null);
        		wasNull = true;
        		wasChanged = true;
        		event.getChannel().sendMessage("No toggle was set for logging. Set to on Please run again to turn off. MAKE SURE TO SET A CHANNEL TO LOG TO.").queue();
        		}
        	if (!wasNull ) {
        		if (toggle.equals("0") && !wasChanged) {
        			toggle = "1";
        			wasChanged = true;
            		Main.logging_config.setProperty("isLoggingEnabled" + event.getGuild().getId().toString(), toggle);
            		Main.logging_config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned on logging.").queue();
        		}
        		if (toggle.equals("1") && !wasChanged) {
        			toggle = "0";
        			wasChanged = false;
            		Main.logging_config.setProperty("isLoggingEnabled" + event.getGuild().getId().toString(), toggle);
            		Main.logging_config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned off logging.").queue();
        		}		
        	}
        	Main.logging_config.setProperty("isLoggingEnabled" + event.getGuild().getId().toString(), toggle);
		}
		//toggle message logging
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "msglogs") && !event.getAuthor().isBot() && (event.getAuthor().getId().equals(Main.OWNER_ID) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
        	Main.output = new FileOutputStream("logConfig.cfg");
        	boolean wasNull = false;
        	boolean wasChanged = false;
        	String toggle = Main.logging_config.getProperty("isMsgLoggingEnabled" + event.getGuild().getId().toString());
        	if (toggle == null) {
        		toggle = "1";
        		Main.logging_config.setProperty("isMsgLoggingEnabled" + event.getGuild().getId().toString(), toggle);
        		Main.logging_config.store(Main.output, null);
        		wasNull = true;
        		wasChanged = true;
        		event.getChannel().sendMessage("No toggle was set for logging. Set to on Please run again to turn off. MAKE SURE TO SET A CHANNEL TO LOG TO.").queue();
        		}
        	if (!wasNull ) {
        		if (toggle.equals("0") && !wasChanged) {
        			toggle = "1";
        			wasChanged = true;
            		Main.logging_config.setProperty("isMsgLoggingEnabled" + event.getGuild().getId().toString(), toggle);
            		Main.logging_config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned on logging.").queue();
        		}
        		if (toggle.equals("1") && !wasChanged) {
        			toggle = "0";
        			wasChanged = false;
            		Main.logging_config.setProperty("isMsgLoggingEnabled" + event.getGuild().getId().toString(), toggle);
            		Main.logging_config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned off logging.").queue();
        		}		
        	}
        	Main.logging_config.setProperty("isMsgLoggingEnabled" + event.getGuild().getId().toString(), toggle);
		}
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "ignorechannel") && !event.getAuthor().isBot() && (event.getAuthor().getId().equals(Main.OWNER_ID) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
        	Main.output = new FileOutputStream("logConfig.cfg");
        	boolean wasNull = false;
        	boolean wasChanged = false;
        	String toggle = Main.logging_config.getProperty("isChannelIgnored" + event.getChannel().getId());
        	if (toggle == null) {
        		toggle = "1";
        		Main.logging_config.setProperty("isChannelIgnored" + event.getChannel().getId(), toggle);
        		Main.logging_config.store(Main.output, null);
        		wasNull = true;
        		wasChanged = true;
        		event.getChannel().sendMessage("Ignoring message logging for this channel.").queue();
        		}
        	if (!wasNull ) {
        		if (toggle.equals("0") && !wasChanged) {
        			toggle = "1";
        			wasChanged = true;
            		Main.logging_config.setProperty("isChannelIgnored" + event.getChannel().getId(), toggle);
            		Main.logging_config.store(Main.output, null);
        			event.getChannel().sendMessage("Ignoring message logging for this channel.").queue();
        		}
        		if (toggle.equals("1") && !wasChanged) {
        			toggle = "0";
        			wasChanged = false;
            		Main.logging_config.setProperty("isChannelIgnored" + event.getChannel().getId(), toggle);
            		Main.logging_config.store(Main.output, null);
        			event.getChannel().sendMessage("Message Logging for this channel is on.").queue();
        		}		
        	}
        	Main.logging_config.setProperty("isChannelIgnored" + event.getChannel().getId(), toggle);
        }
		//set logging channel
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "logchannel") && !event.getAuthor().isBot() && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
			if (event.getMessage().getContentRaw().substring(11, 12).equals("#")) {
				List<TextChannel> chList = event.getGuild().getTextChannelsByName(event.getMessage().getContentRaw().substring(11, event.getMessage().getContentRaw().length()), true);
				channelID = chList.get(0).getId();
			} else {
				channelID = event.getMessage().getContentRaw().replaceAll("[^\\d]", "");
			}
			if (event.getGuild().getTextChannelById(channelID).canTalk()) {
        		try {
        			Main.output = new FileOutputStream("logConfig.cfg");        			
        			Main.logging_config.setProperty("logchannel" + event.getGuild().getId().toString().toString(), channelID);
        			Main.logging_config.store(Main.output, null);
        			event.getChannel().sendMessage("Successfully set the channel for logging.").queue();
        		} catch (IOException io) {io.printStackTrace();
        		} finally {
        			if (Main.output != null) {
        				try {
        					Main.output.close();
        				} catch (IOException e) {e.printStackTrace();
        			}
        		}
			}
		}
		}
		} catch (Exception e) { Main.logger.info("WARN: Exception in ChatFilterToggle command: Insufficient permissions?\n" + e); }
	}
}
}