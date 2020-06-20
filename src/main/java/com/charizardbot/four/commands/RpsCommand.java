package com.charizardbot.four.commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.util.Random;
import com.charizardbot.four.Main;
public class RpsCommand extends ListenerAdapter {
public void onGuildMessageReceived (GuildMessageReceivedEvent event) {
		try {
    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "rps") && !event.getAuthor().isBot()) {
        	String[] choices = {"Rock", "Paper", "Scissors"};
        	Random rand = new Random();
        	String response = "";
        	
        	if (event.getMessage().getContentRaw().toLowerCase().contains("rock")) {
        		int result = rand.nextInt(2);
        		
        		if (result == 0)
        			response = "It's a tie!";
        		else if (result == 1)
        			response = "Paper covers rock, CharizardBot wins!";
        		else
        			response = "You win";
        		EmbedBuilder embed = new EmbedBuilder();
        		embed.setTitle("Rock Paper Scissors Game");
        		embed.addField("Your choice:", "Rock", true);
        		embed.addField("Computer's choice:", choices[result], true);
        		embed.addField("Results:", response, true);
        		embed.setImage("https://i.kym-cdn.com/photos/images/newsfeed/000/521/406/f08.png");
        		event.getChannel().sendMessage(embed.build()).queue();
        	}
        	if (event.getMessage().getContentRaw().toLowerCase().contains("paper")) {
        		int result = rand.nextInt(2);
        		if (result == 1)
        			response = "It's a tie!";            		
        		else if (result == 2)
        			response = "Scissors cuts Paper, CharizardBot wins!";
        		else
        			response = "You win!";
        		EmbedBuilder embed = new EmbedBuilder();
        		embed.setTitle("Rock Paper Scissors Game");
        		embed.addField("Your choice:", "Paper", true);
        		embed.addField("Computer's choice:", choices[result], true);
        		embed.addField("Results:", response, true);
        		embed.setImage("https://i.kym-cdn.com/photos/images/newsfeed/000/521/406/f08.png");
        		event.getChannel().sendMessage(embed.build()).queue();
        	}
        	if (event.getMessage().getContentRaw().toLowerCase().contains("scissors")) {
        		int result = rand.nextInt(2);
        		if (result == 2)
        			response = "It's a tie!";
        		else if (result == 0)
        			response = "Rock crushes Scissors, CharizardBot wins!";
        		else
        			response = "You win!";
        		EmbedBuilder embed = new EmbedBuilder();
        		embed.setTitle("Rock Paper Scissors Game");
        		embed.addField("Your choice:", "Scissors", true);
        		embed.addField("Computer's choice:", choices[result], true);
        		embed.addField("Results:", response, true);
        		embed.setImage("https://i.kym-cdn.com/photos/images/newsfeed/000/521/406/f08.png");
        		event.getChannel().sendMessage(embed.build()).queue();
        	}
        }
		} catch (Exception e) {Main.logger.info("WARN: Exception in RPS command: Insufficient permissions?\n" + e);}
	}
}