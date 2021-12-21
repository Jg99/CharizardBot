package com.charizardbot.main.commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.charizardbot.main.Main;
public class RngCommand extends ListenerAdapter {
public void onMessageReceived (MessageReceivedEvent event) {
	if (event.isFromGuild()) {

		try {
    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
    	String DELIMITERS_RNG = "[" + prefix + "rng\\W]+";
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "rng") && !event.getAuthor().isBot())
        {
        	ArrayList<String> contentArray = new ArrayList<String>();
    		String content = event.getMessage().getContentRaw().toLowerCase();
        	String randomNumbers = "";
        	Scanner rngScan = new Scanner(content); // scans the message for args on rng
        	rngScan.useDelimiter(DELIMITERS_RNG); //screan for numbers only
        	while (rngScan.hasNext())
        	{
        		String token = rngScan.next(); //loops, adds to arraylist
        		contentArray.add(token);
        	}
        	for (int i = 0; i < contentArray.size(); i++)
        	{
        		int rngIn = Integer.parseInt(contentArray.get(i));
        		Random rand = new Random();
        		randomNumbers += rand.nextInt(rngIn) + "\n";
        	}
            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("Random Number Generator");
            embed.addField("Enjoy!", randomNumbers, true);
            Random rand = new Random();
            embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
        	contentArray.clear();
        	rngScan.close();
        }
		} catch (Exception e) {Main.logger.info("WARN: Exception in RNG command: Insufficient permissions?\n" + e);}
	}
}
}