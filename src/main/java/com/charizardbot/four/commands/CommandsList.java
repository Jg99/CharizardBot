package com.charizardbot.four.commands;
import java.awt.Color;
import java.util.Random;
import com.charizardbot.four.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class CommandsList extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
			try {
            Random rand = new Random();
            String prefix = Main.config.getProperty(event.getGuild().getId());
            if (prefix == null)
				prefix = "!";
				String a = event.getMessage().getContentRaw();
				System.out.println(a);
            if ((event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "charizard") || event.getMessage().getContentRaw().startsWith(prefix + "help")) && !event.getAuthor().isBot())
            {
            	EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("CharizardBot Bot commands. Visit https://charizardbot.com for more commands.");
            	String cmdListStrB = "";
            	String cmdListStr = new String(prefix + 
            			"rng <max number>,\n" + prefix + "joindate @user,\n" + prefix + "rps <rock, paper, or scissors>" +
            			",\n gestserverprefix (gets the current server's prefix),\n" + prefix + "pokedex <Pokémon> or " + prefix + "randpokemon - Pokedex Database., ") + 
            			prefix + "pokequotes - Random pokemon quote from the bot.";
        		cmdListStr += "\n" + prefix + "imgursearch - Searches imgur for the top image from your query" +
        	", " + prefix + "randimgur - Randomly pick an image on imgur from a query." +
        				",\n" + prefix + "gif - Searches Tenor for the top image from your query." + 
        				", " + prefix + "randgif - Searches Tenor and returns a random image from the top 50 results." +
        				", " + prefix + "suggest/ - Adds a suggestion for the Discord server if enabled.";
            	String wizCmd = "1";
            	if (Main.config.getProperty("wizCmds" + event.getGuild().getId()) != null) {
            	wizCmd = Main.config.getProperty("wizCmds" + event.getGuild().getId());
            	}
            //	if (event.getAuthor().getId().equals(Main.OWNER_ID))
            //	{
            	//	cmdListStr += "\n\nBot owner commands: " + prefix + "servers, " + prefix + "debug <on> <off>, " + prefix + "mod <add> <remove>, " + prefix +
            	//			"admin <add> <remove>, list user tostring, " + prefix + "setgame <string>";
            //	}
            	if (event.getMember().hasPermission(Permission.ADMINISTRATOR))
            	{
            		 cmdListStrB = prefix + "setprefix prefix: sets the server prefix, such as &, $, !" + 
            				",\n" + prefix + "chatfilter <on> <off> - turns the chat filter on or off." + 
            				",\n" + prefix + "channelfilter <on> <off> - turns channel specific chat filter on or off. MUST HAVE SERVER FILTER ON AS WELL." +
            				",\n" + prefix + "togglemisc, togglewiz, toggleimgur, togglegif, togglesuggestions, togglecoc - Toggles each function on or off on your server.";
                     embed.addField("Admin commands", cmdListStrB, true);
            	}
            	if (wizCmd.equals("1"))
            	{
            		embed.addField("Wizard101 Commands: ",prefix + "petstats s i a w p,\n" + prefix +
            				"schedule (Wizard101 schedule).", false);
            	}
                embed.addField("CharizardBot Commands", cmdListStr, true);
                embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                embed.setFooter("Version: " + Main.VERSION + ", CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
				event.getChannel().sendMessage("Check your DMs! If you don't receive a message, please enable DMs!").queue();
				event.getAuthor().openPrivateChannel().queue(channel ->{
					try {
					channel.sendMessage(embed.build()).queue();
					} catch (Exception e) {/*DM is probably blocked or disabled.*/}
				});
			}
		} catch (Exception e) {Main.logger.info("Exception in CommandsList. Insufficient permissions?\n" + e);}
    }
}