package com.charizardbot.four;
import java.io.FileOutputStream;
import java.io.IOException;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class JoinServerStuff extends ListenerAdapter{
	/**
	 * Performs actions when CharizardBot joins a guild, such as adding default configurations.
	 */
	public void onGuildJoin(GuildJoinEvent event) {
		String serverID = event.getGuild().getId().toString();
		Main.logger.info("Joined server " + event.getGuild().getName() + ", ID: " + serverID);
		//set chat filter to off by default
		if (Main.config.getProperty("filter" + serverID)== null) {
		try {			
		Main.config.setProperty("filter" + event.getGuild().getId().toString(), "0");
			Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		//set prefix to default upon join if there is none
		if (Main.config.getProperty(serverID) == null) {
		try {
		Main.config.setProperty(event.getGuild().getId().toString(), "!");
		Main.output = new FileOutputStream("server_config.cfg");
		Main.config.store(Main.output, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		//set misc cmds to enabled by default
		try {
		if (Main.config.getProperty("miscCmds" + serverID)== null) {
    		Main.config.setProperty("miscCmds" + event.getGuild().getId().toString(), "1");
    		Main.output = new FileOutputStream("server_config.cfg");
    		Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//set wiz commands to be disabled by default
		try {
		if (Main.config.getProperty("wizCmds" + serverID)== null) {
    		Main.config.setProperty("wizCmds" + event.getGuild().getId().toString(), "0");
    		Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//set imgur commands to be disabled by default
		try {
		if (Main.config.getProperty("imgurCmd" + serverID)== null) {
    		Main.config.setProperty("imgurCmd" + event.getGuild().getId().toString(), "0");
    		Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//set tenor to be enabled by default, higher rate limits.
		try {
		if (Main.config.getProperty("tenorCmd" + serverID)== null) {
    		Main.config.setProperty("tenorCmd" + event.getGuild().getId().toString(), "1");
    		Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//set suggestions to be enabled by default, higher rate limits.
		try {
		if (Main.config.getProperty("suggCmd" + serverID)== null) {
    		Main.config.setProperty("suggCmd" + event.getGuild().getId().toString(), "1");
    		Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//set chat filter logging to be disabled by default.
		try {
		if (Main.config.getProperty("isLoggingEnabled" + serverID)== null) {
    		Main.config.setProperty("isLoggingEnabled" + event.getGuild().getId().toString(), "1");
    		Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//set Coc Cmd to be enabled by default
		try {
		if (Main.config.getProperty("cocCmd" + serverID)== null) {
    		Main.config.setProperty("cocCmd" + event.getGuild().getId().toString(), "1");
    		Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (Main.logging_config.getProperty("isMsgLoggingEnabled" + serverID)== null) {
					Main.logging_config.setProperty("isMsgLoggingEnabled" + event.getGuild().getId().toString(), "1");
					Main.output = new FileOutputStream("server_config.cfg");
					Main.config.store(Main.output, null);
				}
					} catch (IOException e) {
						e.printStackTrace();
					}
		//set autoban to disabled by default
		try {
			if (Main.config.getProperty("verification" + serverID) == null) {
				Main.config.setProperty("verification" + event.getGuild().getId().toString(), "0");
				Main.output = new FileOutputStream("server_config.cfg");
				Main.config.store(Main.output, null);
			}
			if (Main.config.getProperty("nickBL" + serverID) == null) {
				Main.config.setProperty("nickBL" + event.getGuild().getId().toString(), "0");
				Main.output = new FileOutputStream("server_config.cfg");
				Main.config.store(Main.output, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}