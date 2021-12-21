package com.charizardbot.main.commands;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.Random;

import com.charizardbot.main.Main;
import com.charizardbot.main.Suggestions;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class SuggestionCommand extends ListenerAdapter{
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.isFromGuild()) {

    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
		String serverID = event.getGuild().getId().toString();
    	String suggCmd = "1";
    	if (Main.config.getProperty("suggCmd" + event.getGuild().getId().toString()) != null) {
    		suggCmd = Main.config.getProperty("suggCmd" + event.getGuild().getId().toString());
    	}
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "suggest ") && suggCmd.equals("1") && !event.getAuthor().isBot())
		{
			Suggestions suggestion = new Suggestions(serverID);
			String content = event.getMessage().getContentRaw().substring(9, event.getMessage().getContentRaw().length());
			suggestion.addSuggestion(content, serverID);
			Random rand = new Random();
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("Your suggestion has been added!");
			embed.addField(("Suggestion ID: " + suggestion.getSuggestionID(serverID)), content, true);
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
			embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			event.getChannel().sendMessageEmbeds(embed.build()).queue();
		}
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "listsuggestions") && suggCmd.equals("1") && !event.getAuthor().isBot())
		{
			Suggestions suggestion = new Suggestions(serverID);
			String suggestionList = suggestion.listSuggestions(serverID);
			if (suggestionList.length() < 2000) {
				Random rand = new Random();
				EmbedBuilder embed = new EmbedBuilder();
				embed.setTitle("Here are your suggestions:");
				embed.addField("Suggestion List for this server:", suggestionList, true);
	        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
				embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
				event.getChannel().sendMessageEmbeds(embed.build()).queue();
			} else {
				event.getChannel().sendMessage("List is too big for Discord, sending as a text file.").queue();
				event.getChannel().sendFile(suggestion.getFile(serverID)).queue();;
			}
		}
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "removesuggestion ") && !event.getAuthor().isBot() && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().hasPermission(Permission.MANAGE_SERVER)) && suggCmd.equals("1")) {
			Suggestions suggestion = new Suggestions(serverID);
			String sid = event.getMessage().getContentRaw().substring(18, event.getMessage().getContentRaw().length());
			suggestion.removeSuggestion(sid, serverID);
			Random rand = new Random();
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("Your suggestion has been removed if the ID is successful!");
			embed.addField("Suggestion ID: ", sid, false);
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
			embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			event.getChannel().sendMessageEmbeds(embed.build()).queue();
		}
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "getsuggestion ") && !event.getAuthor().isBot() && suggCmd.equals("1")) {
			Suggestions suggestion = new Suggestions(serverID);
			String sid = event.getMessage().getContentRaw().substring(15, event.getMessage().getContentRaw().length());
			String suggestionText = suggestion.getSuggestion(sid, serverID);
			Random rand = new Random();
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("Results for your suggestion query");
			embed.addField("Suggestion ID: " + sid, suggestionText, false);
			embed.addField("Status:", suggestion.getSuggestionStatus(sid, serverID), false);
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
			embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			event.getChannel().sendMessageEmbeds(embed.build()).queue();
		}
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "approve ") && !event.getAuthor().isBot() && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().hasPermission(Permission.MANAGE_SERVER)) && suggCmd.equals("1")) {
			Suggestions suggestion = new Suggestions(serverID);
			String sid = event.getMessage().getContentRaw().substring(9, event.getMessage().getContentRaw().length());
			suggestion.approveSuggestion(sid, serverID);
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("Suggestion Approved!");
			embed.addField("Suggestion ID: " + sid, suggestion.getSuggestion(sid, serverID), false);
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
			embed.setColor(Color.green);
			event.getChannel().sendMessageEmbeds(embed.build()).queue();
		}
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "deny ") && !event.getAuthor().isBot() && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getMember().hasPermission(Permission.MANAGE_SERVER))&& suggCmd.equals("1")) {
			Suggestions suggestion = new Suggestions(serverID);
			String sid = event.getMessage().getContentRaw().substring(6, event.getMessage().getContentRaw().length());
			suggestion.denySuggestion(sid, serverID);
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle("Suggestion Denied!");
			embed.addField("Suggestion ID: " + sid, suggestion.getSuggestion(sid, serverID), false);
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
			embed.setColor(Color.red);
			event.getChannel().sendMessageEmbeds(embed.build()).queue();
		}
		// SUGGESTIONS TOGGLE
		try {
        if (event.getMessage().getContentRaw().toLowerCase().contains(prefix + "togglesuggestions") && !event.getAuthor().isBot() && (event.getAuthor().getId().equals(Main.OWNER_ID) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
        	Main.output = new FileOutputStream("server_config.cfg");
        	boolean wasNull = false;
        	boolean wasChanged = false;
        	String toggle = Main.config.getProperty("suggCmd" + event.getGuild().getId().toString());
        	if (toggle == null) {
        		toggle = "1";
        		Main.config.setProperty("suggCmd" + event.getGuild().getId().toString(), toggle);
        		Main.config.store(Main.output, null);
        		wasNull = true;
        		wasChanged = true;
        		event.getChannel().sendMessage("No toggle was set for Suggestion Commands. Set to on by default. Please run again to turn off.").queue();
        		}
        	if (!wasNull ) {
        		if (toggle.equals("0") && !wasChanged) {
        			toggle = "1";
        			wasChanged = true;
            		Main.config.setProperty("suggCmd" + event.getGuild().getId().toString(), toggle);
            		Main.config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned on Suggestion commands.").queue();
        		}
        		if (toggle.equals("1") && !wasChanged) {
        			toggle = "0";
        			wasChanged = false;
            		Main.config.setProperty("suggCmd" + event.getGuild().getId().toString(), toggle);
            		Main.config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned off Suggestion commands.").queue();
        		}
        	}
        	}
		} catch (Exception e) {Main.logger.info("WARN: Exception in Suggestions Toggle. Insufficient permissions?\n" + e);};
	}
}
}