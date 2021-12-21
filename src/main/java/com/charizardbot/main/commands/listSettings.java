package com.charizardbot.main.commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;
import java.util.Random;

import com.charizardbot.main.Main;
public class listSettings extends ListenerAdapter {
public void onMessageReceived (MessageReceivedEvent event) {
	if (event.isFromGuild()) {

		try {
    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "settings") && !event.getAuthor().isBot() && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
        	EmbedBuilder embed = new EmbedBuilder();
        	String chanFilter = "Default";
        	String serverFilter = "Default";
        	String autoBan = "Default";
        	String imgur = "Default";
        	String wizCommands = "Default";
        	String tenor = "Default";
        	String suggestions = "Default";
        	try {
        	 serverFilter = Main.config.getProperty("filter" + event.getGuild().getId().toString());
        	 chanFilter = Main.config.getProperty("chanfilter" + event.getChannel().getId());
        	 autoBan = Main.config.getProperty("verification" + event.getGuild().getId());
        	 imgur = Main.config.getProperty("imgurCmd" + event.getGuild().getId().toString());
        	 wizCommands = Main.config.getProperty("wizCmds" + event.getGuild().getId().toString());
        	 tenor = Main.config.getProperty("tenorCmd" + event.getGuild().getId().toString());
        	 suggestions = Main.config.getProperty("suggCmd" + event.getGuild().getId().toString());
        	} catch (Exception e) {}
          	embed.setTitle("Server settings for this server & channel.");
          	embed.addField("Prefix: ", prefix, true);
        	embed.addField("Server chat filter (1 = on): ", serverFilter, true);
        	embed.addField("This channel filter (1 = on): ", chanFilter, true);
        	embed.addField("Imgur/tenor Search (1 = on): ", imgur + "/" + tenor, false);
        	embed.addField("Wizard101 commands (1 = on): ", wizCommands, false);
			embed.addField("Suggestions commands (1 = on):", suggestions, false);
			embed.addField("Server Autoban: ", autoBan, false);
        	Random rand = new Random();
          	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
          	event.getChannel().sendMessageEmbeds(embed.build()).queue();
        }
		} catch (Exception e) { Main.logger.info("WARN: Exception in Settings command: Insufficient permissions?\n" + e); }
	}
}
}