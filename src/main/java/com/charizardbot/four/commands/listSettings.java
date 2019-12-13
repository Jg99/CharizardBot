package com.charizardbot.four.commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.charizardbot.four.Main;
import java.awt.Color;
import java.util.Random;
public class listSettings extends ListenerAdapter {
public void onGuildMessageReceived (GuildMessageReceivedEvent event) {
		try {
    	String prefix = Main.config.getProperty(event.getGuild().getId());
    	if (prefix == null)
    		prefix = "!";
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "settings") && !event.getAuthor().isBot() && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
        	EmbedBuilder embed = new EmbedBuilder();
        	String chanFilter = "Default";
        	String serverFilter = "Default";
        	String gtpVerification = "Default";
        	String empVerification = "Default";
        	String imgur = "Default";
        	String wizCommands = "Default";
        	String tenor = "Default";
        	String suggestions = "Default";
        	try {
        	 serverFilter = Main.config.getProperty("filter" + event.getGuild().getId());
        	 chanFilter = Main.config.getProperty("chanfilter" + event.getChannel().getId());
        	 gtpVerification = Main.config.getProperty("verification" + "468440854886088714");
        	 empVerification = Main.config.getProperty("verification" + "458451155522027521");
        	 imgur = Main.config.getProperty("imgurCmd" + event.getGuild().getId());
        	 wizCommands = Main.config.getProperty("wizCmds" + event.getGuild().getId());
        	 tenor = Main.config.getProperty("tenorCmd" + event.getGuild().getId());
        	 suggestions = Main.config.getProperty("suggCmd" + event.getGuild().getId());
        	} catch (Exception e) {}
          	embed.setTitle("Server settings for this server & channel.");
          	embed.addField("Prefix: ", prefix, true);
        	embed.addField("Server chat filter (1 = on): ", serverFilter, true);
        	embed.addField("This channel filter (1 = on): ", chanFilter, true);
        	embed.addField("Imgur/tenor Search (1 = on): ", imgur + "/" + tenor, false);
        	embed.addField("Wizard101 commands (1 = on): ", wizCommands, false);
        	embed.addField("Suggestions commands (1 = on):", suggestions, false);
        	if (event.getGuild().getId().equals("468440854886088714"))
        	{
        		EmbedBuilder embed2 = new EmbedBuilder();
        		embed2.addField("GTP Verification (CharizardBot): ", gtpVerification, false);
        		event.getChannel().sendMessage(embed2.build()).queue();
        	}
        	if (event.getGuild().getId().equals("458451155522027521"))
        	{
        		EmbedBuilder embed2 = new EmbedBuilder();
        		embed2.addField("Emporium Verification (CharizardBot): ", empVerification, false);
        		event.getChannel().sendMessage(embed2.build()).queue();
        	}
        	Random rand = new Random();
          	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
          	event.getChannel().sendMessage(embed.build()).queue();
        }
		} catch (Exception e) { Main.logger.info("WARN: Exception in Settings command: Insufficient permissions?\n" + e); }
	}
}