package com.charizardbot.four.commands;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.charizardbot.four.Main;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
public class ChatFilterToggle extends ListenerAdapter {
	public String channelID = "";
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		try {
    	String prefix = Main.config.getProperty(event.getGuild().getId());
    	if (prefix == null)
    		prefix = "!";
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "chatfilter on") && !event.getAuthor().isBot() && ( event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
        	Main.config.setProperty("filter" + event.getGuild().getId(), "1");
        	try {
        		Main.output = new FileOutputStream("server_config.cfg");        			
        		Main.config.setProperty("filter" + event.getGuild().getId(), "1");
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
        	Main.config.setProperty("filter" + event.getGuild().getId(), "1");
        	try {
        		Main.output = new FileOutputStream("server_config.cfg");        			
        		Main.config.setProperty("filter" + event.getGuild().getId(), "0");
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
        	Main.config.setProperty("filter" + event.getGuild().getId(), "1");
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
        	Main.config.setProperty("filter" + event.getGuild().getId(), "1");
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
        	Main.output = new FileOutputStream("server_config.cfg");
        	boolean wasNull = false;
        	boolean wasChanged = false;
        	String toggle = Main.config.getProperty("isLoggingEnabled" + event.getGuild().getId());
        	if (toggle == null) {
        		toggle = "1";
        		Main.config.setProperty("isLoggingEnabled" + event.getGuild().getId(), toggle);
        		Main.config.store(Main.output, null);
        		wasNull = true;
        		wasChanged = true;
        		event.getChannel().sendMessage("No toggle was set for logging. Set to on Please run again to turn off. MAKE SURE TO SET A CHANNEL TO LOG TO.").queue();
        		}
        	if (!wasNull ) {
        		if (toggle.equals("0") && !wasChanged) {
        			toggle = "1";
        			wasChanged = true;
            		Main.config.setProperty("isLoggingEnabled" + event.getGuild().getId(), toggle);
            		Main.config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned on logging.").queue();
        		}
        		if (toggle.equals("1") && !wasChanged) {
        			toggle = "0";
        			wasChanged = false;
            		Main.config.setProperty("isLoggingEnabled" + event.getGuild().getId(), toggle);
            		Main.config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned off logging.").queue();
        		}		
        	}
        	Main.config.setProperty("isLoggingEnabled" + event.getGuild().getId(), toggle);
		}
		//toggle message logging
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "msglogs") && !event.getAuthor().isBot() && (event.getAuthor().getId().equals(Main.OWNER_ID) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
        	Main.output = new FileOutputStream("server_config.cfg");
        	boolean wasNull = false;
        	boolean wasChanged = false;
        	String toggle = Main.config.getProperty("isMsgLoggingEnabled" + event.getGuild().getId());
        	if (toggle == null) {
        		toggle = "1";
        		Main.config.setProperty("isMsgLoggingEnabled" + event.getGuild().getId(), toggle);
        		Main.config.store(Main.output, null);
        		wasNull = true;
        		wasChanged = true;
        		event.getChannel().sendMessage("No toggle was set for logging. Set to on Please run again to turn off. MAKE SURE TO SET A CHANNEL TO LOG TO.").queue();
        		}
        	if (!wasNull ) {
        		if (toggle.equals("0") && !wasChanged) {
        			toggle = "1";
        			wasChanged = true;
            		Main.config.setProperty("isMsgLoggingEnabled" + event.getGuild().getId(), toggle);
            		Main.config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned on logging.").queue();
        		}
        		if (toggle.equals("1") && !wasChanged) {
        			toggle = "0";
        			wasChanged = false;
            		Main.config.setProperty("isMsgLoggingEnabled" + event.getGuild().getId(), toggle);
            		Main.config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned off logging.").queue();
        		}		
        	}
        	Main.config.setProperty("isMsgLoggingEnabled" + event.getGuild().getId(), toggle);
        }
		//set logging channel
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "logchannel") && !event.getAuthor().isBot() && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
			if (event.getMessage().getContentRaw().substring(11, 12).equals("#")) {
		//	List<TextChannel> chList = server.getChannelsByName(event.getMessage().getContentRaw().substring(11, event.getMessage().getContentRaw().length()));
			List<TextChannel> chList = event.getGuild().getTextChannelsByName(event.getMessage().getContentRaw().substring(11, event.getMessage().getContentRaw().length()), true);
			channelID = chList.get(0).getId();
			} else {
			channelID = event.getMessage().getContentRaw().replaceAll("[^\\d]", "");
			}
			if (event.getGuild().getTextChannelById(channelID).canTalk()) {
        	try {
        		Main.output = new FileOutputStream("server_config.cfg");        			
        		Main.config.setProperty("logchannel" + event.getGuild().getId(), channelID);
        		Main.config.store(Main.output, null);
        		event.getChannel().sendMessage("Successfully set the channel for logging.").queue();
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
		}
		} catch (Exception e) { Main.logger.info("WARN: Exception in ChatFilterToggle command: Insufficient permissions?\n" + e); }
	}
}