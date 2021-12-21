package com.charizardbot.main.commands;
import java.awt.Color;
import java.util.Random;

import com.charizardbot.main.Main;
import com.charizardbot.main.pokeQuotes;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class PokemonQuoteCommand extends ListenerAdapter{
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.isFromGuild()) {

		try {
    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "pokequote") && !event.getAuthor().isBot()) {
			Random rand = new Random();
			pokeQuotes quote = new pokeQuotes();
			String pkQuote = quote.getQuote();
			EmbedBuilder embed = new EmbedBuilder();
        	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
        	embed.addField("Here's a random Pokémon quote for you!", pkQuote, false);
        	event.getChannel().sendMessageEmbeds(embed.build()).queue();
		}
	} catch (Exception e){Main.logger.info("Warn: Exception in PokemonQuote Command. Insufficient permissions?");}
	}
}
}