package com.charizardbot.four.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.charizardbot.four.Main;
import com.charizardbot.four.sendNudes;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.Random;
public class MiscCommands extends ListenerAdapter {
	final String BOT_ID = "428634701771702282";
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String miscToggle = "1";
    	if (Main.config.getProperty("miscCmds" + event.getGuild().getId()) != null) {
		miscToggle = Main.config.getProperty("miscCmds" + event.getGuild().getId());
    	}
		try {
	    	String prefix = Main.config.getProperty(event.getGuild().getId());
	    	if (prefix == null)
	    		prefix = "!";
		if (miscToggle.equals("1")){
        if (event.getMessage().getContentRaw().toLowerCase().startsWith("no u") && event.getMessage().getMentionedUsers().size() > 0 && event.getAuthor().getId().equals(Main.OWNER_ID))    {
       	 if (event.getMessage().getMentionedMembers().get(0).getId().equals(BOT_ID)) {
       	 	event.getChannel().sendMessage("u can't no u me!").queue(); 
       	 	}
        }
        if (event.getMessage().getContentRaw().toLowerCase().startsWith("send nudes") && !event.getAuthor().isBot())
        {
        	sendNudes nude = new sendNudes();
        	String nudeURL = nude.getNude();
        	EmbedBuilder embed = new EmbedBuilder();
        	embed.setTitle("Here's a nude for you!");
        	//embed.addField("", nudeURL, true);
        	embed.setImage(nudeURL);
        	Random rand = new Random();
        	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
        	event.getChannel().sendMessage(embed.build()).queue();
        }
        if (event.getMessage().getContentRaw().toLowerCase().equals("instructions unclear") && event.getMember().getId().equals(Main.OWNER_ID))
     	{
    	 event.getChannel().sendMessage("Indeed, my dick got stuck in a toaster.").queue();
		}
	}
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "togglemisc") && !event.getAuthor().isBot() && (event.getAuthor().getId().equals(Main.OWNER_ID) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
			Main.output = new FileOutputStream("server_config.cfg");
			boolean wasNull = false;
			boolean wasChanged = false;
			String toggle = Main.config.getProperty("miscCmds" + event.getGuild().getId());
			if (toggle == null) {
				toggle = "1";
				Main.config.setProperty("miscCmds" + event.getGuild().getId(), toggle);
				Main.config.store(Main.output, null);
				wasNull = true;
				wasChanged = true;
				event.getChannel().sendMessage("No toggle was set for Misc Commands. Set to on by default. Please run again to turn off.").queue();
			}
			if (!wasNull ) {
				if (toggle.equals("0") && !wasChanged) {
					toggle = "1";
					wasChanged = true;
					Main.config.setProperty("miscCmds" + event.getGuild().getId(), toggle);
					Main.config.store(Main.output, null);
					event.getChannel().sendMessage("Turned on misc commands such as \"send nudes.\"").queue();
				}
				if (toggle.equals("1") && !wasChanged) {
					toggle = "0";
					wasChanged = false;
					Main.config.setProperty("miscCmds" + event.getGuild().getId(), toggle);
					Main.config.store(Main.output, null);
					event.getChannel().sendMessage("Turned off misc commands such as \"send nudes.\"").queue();
				}		
			}
			Main.config.setProperty("miscCmds" + event.getGuild().getId(), toggle);
		}
		} catch (Exception e) { Main.logger.info("WARN: Exception in Misc Commands: Insufficient permissions?\n" + e); } 
	} 	
}