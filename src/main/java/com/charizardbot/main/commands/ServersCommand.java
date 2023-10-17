package com.charizardbot.main.commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import com.charizardbot.main.Main;

import java.util.Collections;
import java.util.Iterator;
public class ServersCommand extends ListenerAdapter {
public void onMessageReceived (MessageReceivedEvent event) {
	if (event.isFromGuild()) {

		try {
    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "servers") && event.getAuthor().getId().equals(Main.OWNER_ID)) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("CharizardBot is in " + event.getJDA().getGuilds().size() + " servers.");
                Random rand = new Random();
                embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
	}
	if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "exportserverlist") && event.getAuthor().getId().equals(Main.OWNER_ID)) {
		Iterator<Guild> serverList = event.getJDA().getGuilds().iterator();
		String list = "";
		while (serverList.hasNext()) {
			String serverID = serverList.next().getId();
			list += String.format("id: %s, name: %s, size: %s\n", serverID, event.getJDA().getGuildById(serverID).getName(), event.getJDA().getGuildById(serverID).getMemberCount());
		}
		File file = new File("servers.txt");
		if (!file.exists())
		{
			new FileOutputStream("servers.txt", false).close();
		}
		BufferedWriter bw = null;
		try {
		    bw = new BufferedWriter(new FileWriter("servers.txt", false));
			bw.write(list);
		    bw.newLine();
		    bw.flush();
		} catch (IOException ioe) {
	  		ioe.printStackTrace();
		} finally {                       
	    if (bw != null) try {
		   bw.close();
	    } catch (IOException ioe2) {
		   Main.logger.info("Error, could not write to file servers.txt");
	    }
		}
					event.getChannel().sendFiles(Collections.singleton(
						FileUpload.fromStreamSupplier("servers.txt", () -> {
							try {
								return new FileInputStream("servers.txt");
							} catch (Exception e) {return null;}
					})
					)).queue();
	 }
	if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "serverinfo") && event.getAuthor().getId().equals(Main.OWNER_ID)) {
//253313100613156864
		String a = event.getMessage().getContentRaw().substring(12, event.getMessage().getContentRaw().length());
		Guild dserver = event.getJDA().getGuildById(a);
		String serverName = dserver.getName();
		int size = dserver.getMemberCount();
		event.getChannel().sendMessage("name: " + serverName + "\nsize: " + size).queue();
	}
		} catch (Exception e) {Main.logger.info("WARN: Exception in Servers command: Insufficient permissions?\n" + e);}
	}
}
}