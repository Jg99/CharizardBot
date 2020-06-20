package com.charizardbot.four.commands;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import com.charizardbot.four.ChatFilter;
import com.charizardbot.four.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class MainCommands extends ListenerAdapter {
	final String BOT_ID = "428634701771702282";
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
    String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    Random rand = new Random();
    prefix = Main.config.getProperty(event.getGuild().getId().toString()); 
    if (prefix == null)
        prefix = "!";
    try {
        if (event.getMessage().getContentRaw().toLowerCase().startsWith("getserverprefix")) {
            event.getChannel().sendMessage("Your server's prefix is: " + prefix);
            }
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix) && !event.getAuthor().isBot()) {
        

            	if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "leave") && event.getAuthor().getId().equals(Main.OWNER_ID) )
            	{
					ArrayList<String> contentArray = new ArrayList<String>();
        			final String DELIMITERS_LEAVE = "[" + prefix + "leave\\W]+"; // if i wanna leave a server, i will.
                	Scanner messageScanner = new Scanner(event.getMessage().getContentRaw());
        			messageScanner.useDelimiter(DELIMITERS_LEAVE);
        			while (messageScanner.hasNext())
        			{
        				String token = messageScanner.next();
        				contentArray.add(token);
        			}
        			messageScanner.close();
        			if (contentArray.size() > 0) {
					Guild leave = event.getJDA().getGuildById(contentArray.get(0));
					Main.logger.info("Left server " + leave.getName() + ", ID:" + leave.getId());
        			leave.leave();
        			} else {
						Main.logger.info("Left server " + event.getGuild().getName() + ", ID:" + event.getGuild().getId().toString());
						event.getGuild().leave();
        			}
            	}
            	if (event.getMessage().getContentRaw().equals(prefix + "updatestats") && event.getAuthor().getId().equals(Main.OWNER_ID))
            	{
            		// let's make our own json post to discordbotlist.com
            		URL url = new URL("https://discordbotlist.com/api/bots/428634701771702282/stats");
            		String token = "Bot 5ea56c2a32344d8f880c6559575ce4f03cd3cb3411fe857550c6e7469ab6f824";
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
            			    System.out.println(response.toString());
            		}
           		}
            	if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "getchannels") && event.getAuthor().getId().equals(Main.OWNER_ID) )
            	{
					try {
					String serverID = event.getMessage().getContentRaw().replaceAll("[" + prefix + "getchannels\\W]+", "");
					List<TextChannel> channels;
					System.out.println(serverID);
					if (serverID.isEmpty()) {
						channels = event.getGuild().getTextChannels();
					} else {
						channels = event.getJDA().getGuildById(serverID).getTextChannels();
					}
        			String channelArray = "";
					String channelList = "";
        	    	if (channels.size() > 6) // more than one message is needed, send a text file
        	    	{
            	    	for (int i = 0; i < channels.size(); i++)
            	    	{
            	    		channelArray += channels.get(i).getId() + ",";
							channelList += channels.get(i).getName() + ", ID: " + channels.get(i).getId() + "\n";
            	    	}
						File file = new File("channels.txt");
						if (!file.exists())
						{
							new FileOutputStream("channels.txt", false).close();
						}
						BufferedWriter bw = null;
						try {
							bw = new BufferedWriter(new FileWriter("channels.txt", false));
							bw.write(channelList);
							bw.newLine();
							bw.flush();
						} catch (IOException ioe) {
							  ioe.printStackTrace();
						} finally {                       
						if (bw != null) try {
						   bw.close();
						} catch (IOException ioe2) {
						  Main.logger.info("Error, could not write to file channels.txt");
						}
					}
					event.getChannel().sendFile(file, "channels.txt").queue();
        	    	} else {
            	    	for (int i = 0; i < channels.size(); i++)
            	    	{
            	    		channelArray += channels.get(i).toString() + ",";
            	    	}
                    	EmbedBuilder embed = new EmbedBuilder();
                        embed.setTitle("CharizardBot");
                        embed.addField("List of channels in specificed Server ID ", channelArray, true);
                        embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                     	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
                        event.getChannel().sendMessage(embed.build()).queue();
					}
				} catch (Exception e) {event.getChannel().sendMessage("Error in listing channel.");e.printStackTrace();}
            	}
            	if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "setgame") && event.getAuthor().getId().equals(Main.OWNER_ID))
            	{
            		Main.output = new FileOutputStream("server_config.cfg");
	            	String game = event.getMessage().getContentRaw().substring(9, event.getMessage().getContentRaw().length());
	            	Main.config.setProperty("gamestatus", game);
	            	Main.config.store(Main.output, null);
	            	event.getJDA().getPresence().setActivity(Activity.playing(game));
                	EmbedBuilder embed = new EmbedBuilder();
                	embed.setTitle("CharizardBot");
                	embed.addField("Current game set by command: ", game, true);
                	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                	event.getChannel().sendMessage(embed.build()).queue();
            	}
            	if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "restartbot") && event.getAuthor().getId().equals(Main.OWNER_ID)) {
            		String shdir = new File("").getAbsolutePath();
            	 	try {
                     	Process proc = Runtime.getRuntime().exec(shdir + "/restartbot.sh /"); //Bot restart script file.
                     	BufferedReader read = new BufferedReader(new InputStreamReader(
                             proc.getInputStream()));
                     	try {
                         	proc.waitFor();
                     	} catch (InterruptedException e) {
                        	Main.logger.info(e.getMessage());
                    	}
                     	while (read.ready()) {
							Main.logger.info(read.readLine());
                     	}
                 	} catch (IOException e) {
						Main.logger.info(e.getMessage());
                }
             		event.getChannel().sendMessage("attempted to restart the bot.").queue();
            }
            if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "reloadfilter") && event.getAuthor().getId().equals(Main.OWNER_ID))
            {
            	if (Main.chatFilter.exists())
            	{
					Main.logger.info("Chat filter reloaded");
            		Scanner fileScan = null;
        							try {
        								fileScan = new Scanner(Main.chatFilter);
        							} catch (FileNotFoundException e) {
        								e.printStackTrace();
        							}
                                    Main.filterDB = "";
            		while (fileScan.hasNextLine())
            		{
            			Main.filterDB += "\n" + fileScan.nextLine();
            		}
            		Main.filter = new ChatFilter(event.getMessage().getContentRaw().toLowerCase(), Main.filterDB.toLowerCase());
            		}
            }
            if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "filterlist") && (event.getMember().hasPermission(Permission.ADMINISTRATOR)))
            {
            	String mReply = Main.filterDB;
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Chat filter");
                embed.addField("Word list:" , mReply, true);
                embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
             	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
                event.getChannel().sendMessage(embed.build()).queue();
                embed.clear();
            }
            if (event.getMessage().getContentRaw().toLowerCase().startsWith("!reloadsettings") && event.getAuthor().getId().equals(Main.OWNER_ID)|| event.getMember().hasPermission(Permission.ADMINISTRATOR))
            {
        		try {
        			Main.input = new FileInputStream("server_config.cfg");
        			// load a properties file
        			Main.config.load(Main.input);
        		} catch (IOException ex) {
        			ex.printStackTrace();
        		} finally {
        			if (Main.input != null) {
        				try {
        					Main.input.close();
        				} catch (IOException e) {
        					e.printStackTrace();
        				}
        			}
        		}
            }
            if (event.getMessage().getContentRaw().toLowerCase().contains(prefix + "backupsettings") && event.getAuthor().getId().equals(Main.OWNER_ID))
            {
            	Main.output = new FileOutputStream("server_config.cfg.bkp");
            	Main.config.store(Main.output, null);
            	event.getChannel().sendMessage("Successfully backed up server config.").queue();
            }
            if (event.getMessage().getContentRaw().contains(prefix + "restoresettings") && event.getAuthor().getId().equals(Main.OWNER_ID))
            {
            	File file = new File("server_config.cfg.bkp");
            	if (file.exists()) {
                Main.input = new FileInputStream("server_config.cfg.bkp");
            	Main.config.load(Main.input); 
            	event.getChannel().sendMessage("Successfully restored server config.").queue();
            	} else {
            		event.getChannel().sendMessage("Failed to restore server config. Backup file does not exist.").queue();
            	}
            }
            if (event.getMessage().getContentRaw().toLowerCase().contains(prefix + "togglewiz") && (event.getAuthor().getId().equals(Main.OWNER_ID) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
            	Main.output = new FileOutputStream("server_config.cfg");
            	boolean wasNull = false;
            	boolean wasChanged = false;
            	String toggle = Main.config.getProperty("wizCmds" + event.getGuild().getId().toString());
            	if (toggle == null) {
            		toggle = "1";
            		Main.config.setProperty("wizCmds" + event.getGuild().getId().toString(), toggle);
                    Main.config.store(Main.output, null);
            		wasNull = true;
            		wasChanged = true;
            		event.getChannel().sendMessage("No toggle was set for Wiz Commands. Set to on by default. Please run again to turn off.").queue();
            		}
            	if (!wasNull ) {
            		if (toggle.equals("0") && !wasChanged) {
            			toggle = "1";
            			wasChanged = true;
                		Main.config.setProperty("wizCmds" + event.getGuild().getId().toString(), toggle);
                		Main.config.store(Main.output, null);
            			event.getChannel().sendMessage("Turned on Wiz commands such as \"petstats.\"").queue();
            		}
            		if (toggle.equals("1") && !wasChanged) {
            			toggle = "0";
            			wasChanged = false;
                		Main.config.setProperty("wizCmds" + event.getGuild().getId().toString(), toggle);
                		Main.config.store(Main.output, null);
            			event.getChannel().sendMessage("Turned off Wiz commands such as \"petstats.\"").queue();
            		}
            	}
            	Main.config.setProperty("wizCmds" + event.getGuild().getId().toString(), toggle);
            }
            if (event.getMessage().getContentRaw().toLowerCase().contains(prefix + "setprefix") && (event.getAuthor().getId().equals(Main.OWNER_ID) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
            	try {
            		Main.output = new FileOutputStream("server_config.cfg");
            		String ptest = event.getMessage().getContentRaw().substring(11, 12);
            		if (ptest.equals("~") || ptest.equals("!") || ptest.equals("$") || ptest.equals("%") 
            				|| ptest.equals("^") || ptest.equals("&") || ptest.equals("*") || ptest.equals("+")
            				|| ptest.equals("-")) {            			
            		Main.config.remove(event.getGuild().getId().toString());
            		Main.config.setProperty(event.getGuild().getId().toString(), event.getMessage().getContentRaw().substring(11, 12));
            		Main.config.store(Main.output, null);
            		event.getChannel().sendMessage("Set server prefix to " + Main.config.getProperty(event.getGuild().getId().toString())).queue();
            		}
            	} catch (Exception e) {
            		//do nothing as it's probably an invaldi length or something.
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
    } catch (Exception e) {Main.logger.info("Error in the Main message listener: Insufficient permissions?\n" + e);}
}
}