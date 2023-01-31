package com.charizardbot.main.commands;
import java.awt.Color;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Random;

import com.charizardbot.main.Main;
import com.charizardbot.main.MiniDex;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
/**
 * Pokédex commands
 */
public class PokedexCommand extends ListenerAdapter {
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.isFromGuild()) {

    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
    	try {
			if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "pokedex") && !event.getAuthor().isBot()) {
				event.getChannel().sendTyping().queue();
				EmbedBuilder embed = new EmbedBuilder();
            	Random rand = new Random();
            	String inMon = event.getMessage().getContentRaw().toLowerCase();
				String parsedMon = inMon.substring(9, inMon.length());
				String stats = MiniDex.getPokemonInfo(parsedMon);
				String pkName = MiniDex.getPokemonName();
				System.out.println("Input:\n" + parsedMon);
    			if (!pkName.equals(null)) {
					// this checks for entries without a sprite yet, replaces with a pokeball.
					if (!MiniDex.getSprite().equals("null")) {
					embed.setThumbnail(MiniDex.getSprite()); 
					} else {
					embed.setThumbnail("https://charizardbot.com/pokeball.png");
				 	}
					embed.setAuthor(event.getAuthor().getAsTag());
    				embed.setTitle("CharizardBot's Pokédex");
            		embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
    				embed.addField("Pokémon information for " + pkName ,stats, true);
            		embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
    				event.getChannel().sendMessageEmbeds(embed.build()).queue();
    			} else {
    				event.getChannel().sendMessage("Please enter the correct Pokemon's name or national dex number.");
    			}
			}
    		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "randpokemon") && !event.getAuthor().isBot()) {	
            	EmbedBuilder embed = new EmbedBuilder();
            	Random rand = new Random();
            	int randPk = rand.nextInt(807);
				String stats = MiniDex.getPokemonInfo(Integer.toString(randPk));
				String pkName = MiniDex.getPokemonName();
    			if (pkName != null) {
    				embed.setThumbnail(MiniDex.getSprite());
    				embed.setAuthor(event.getAuthor().getAsTag());
    				embed.setTitle("CharizardBot's Pokédex");
            		embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
    				embed.addField("Pokémon information for " + pkName ,stats, false);
            		embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
    				event.getChannel().sendMessageEmbeds(embed.build()).queue();
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			Main.logger.info("WARN: Exception in the Pokedex Command. Insufficient permissions or API server is down?\n" + e);
	}
    		try {
    			if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "pokeapi") && !event.getAuthor().isBot()) {
					InetAddress pokeapi = java.net.InetAddress.getByName("charizardbot.com");
					String address = pokeapi.getHostAddress();
		    	    SocketAddress addy = new InetSocketAddress(address, 8443);
		    		Socket s = new Socket();
		    		s.connect(addy, 1000);
		    	   if (s.isConnected()) {
		    		    EmbedBuilder embed = new EmbedBuilder();
		    			Random rand = new Random();
		    			embed.setTitle("CharizardBot PokeAPI Status");
		    			embed.addField("Status", "UP", false);
		    			embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
		    	   		embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
		    	   		event.getChannel().sendMessageEmbeds(embed.build()).queue();
		       		}
		       		if (s.isClosed()) {
		    		EmbedBuilder embed = new EmbedBuilder();
		    		Random rand = new Random();
		    		embed.setTitle("CharizardBot PokeAPI Status");
		    		embed.addField("Status", "Down", false);
		    		embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
		    		embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
		    		event.getChannel().sendMessageEmbeds(embed.build()).queue();		       
		    	   	} 
		       		s.close(); 
    			} 
    		}catch (Exception e) {
		    	   EmbedBuilder embed = new EmbedBuilder();
		    	   Random rand = new Random();
		    	   embed.setTitle("CharizardBot PokeAPI Status");
		    	   embed.addField("Status", "Down", false);
		    	   embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
		    	   embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
		    	   event.getChannel().sendMessageEmbeds(embed.build()).queue();		
    		}
	}
}
}