package com.charizardbot.four;
//import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
import com.charizardbot.four.Main;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class JoinServerStuff extends ListenerAdapter{
	/**
	 * Performs actions when CharizardBot joins a guild, such as adding default configurations.
	 */
	public void onGuildJoin(GuildJoinEvent event) {
		String serverID = event.getGuild().getId();
		Main.logger.info("Joined server " + event.getGuild().getName() + ", ID: " + serverID);
		//set chat filter to off by default
		if (Main.config.getProperty("filter" + serverID)== null) {
		try {			
		Main.config.setProperty("filter" + event.getGuild().getId(), "0");
			Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		//set prefix to default upon join if there is none
		if (Main.config.getProperty(serverID) == null) {
		try {
		Main.config.setProperty(event.getGuild().getId(), "!");
		Main.output = new FileOutputStream("server_config.cfg");
		Main.config.store(Main.output, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		//set misc cmds to enabled by default
		try {
		if (Main.config.getProperty("miscCmds" + serverID)== null) {
    		Main.config.setProperty("miscCmds" + event.getGuild().getId(), "1");
    		Main.output = new FileOutputStream("server_config.cfg");
    		Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//set wiz commands to be disabled by default
		try {
		if (Main.config.getProperty("wizCmds" + serverID)== null) {
    		Main.config.setProperty("wizCmds" + event.getGuild().getId(), "0");
    		Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//set imgur commands to be disabled by default
		try {
		if (Main.config.getProperty("imgurCmd" + serverID)== null) {
    		Main.config.setProperty("imgurCmd" + event.getGuild().getId(), "0");
    		Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//set tenor to be enabled by default, higher rate limits.
		try {
		if (Main.config.getProperty("tenorCmd" + serverID)== null) {
    		Main.config.setProperty("tenorCmd" + event.getGuild().getId(), "1");
    		Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//set suggestions to be enabled by default, higher rate limits.
		try {
		if (Main.config.getProperty("suggCmd" + serverID)== null) {
    		Main.config.setProperty("suggCmd" + event.getGuild().getId(), "1");
    		Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//set chat filter logging to be disabled by default.
		try {
		if (Main.config.getProperty("isLoggingEnabled" + serverID)== null) {
    		Main.config.setProperty("isLoggingEnabled" + event.getGuild().getId(), "1");
    		Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
		//set Coc Cmd to be enabled by default
		try {
		if (Main.config.getProperty("cocCmd" + serverID)== null) {
    		Main.config.setProperty("cocCmd" + event.getGuild().getId(), "1");
    		Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
		}
			} catch (IOException e) {
				e.printStackTrace();
			}
/*		try {
    	URL url = new URL("https://discordbotlist.com/api/bots/428634701771702282/stats");
    	String token = "";
    	HttpURLConnection con = (HttpURLConnection)url.openConnection();
    	con.setRequestMethod("POST");
    	con.setRequestProperty("Authorization", token);
    	con.setRequestProperty("Content-Type", "application/json; utf-8");
    	con.setRequestProperty("Accept", "application/json");
    	con.setDoOutput(true);
    	con.setDoInput(true);
    	String jsonInputString = "{\"shard_id\":\"0\",\"guilds\":\"" + event.getJDA().getGuilds().size() + "\",\"users\":\"" + event.getJDA().getUsers().size() +"\",\"voice_connections\":\"0\"}";          
    	try(OutputStream os = con.getOutputStream()) {
    	    byte[] input1 = jsonInputString.getBytes("utf-8");
    	    os.write(input1, 0, input1.length); 
    	}
    	try(BufferedReader br = new BufferedReader(
    			  new InputStreamReader(con.getInputStream(), "utf-8"))) {
    			    StringBuilder response = new StringBuilder();
    			    String responseLine = null;
    			    while ((responseLine = br.readLine()) != null) {
    			        response.append(responseLine.trim());
    			    }
    			    Main.logger.info(response.toString());
    			}
		} catch (Exception e){};*/
		//end void
	}
}