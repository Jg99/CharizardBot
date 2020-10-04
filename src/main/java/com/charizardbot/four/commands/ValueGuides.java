package com.charizardbot.four.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;
import java.util.Random;
import java.util.Scanner;

import com.charizardbot.four.Main;

public class ValueGuides extends ListenerAdapter {
    /**
     * This command is what the user runs to get the value of an item [Wizard101].
     */
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        try {
            String VALUE_TABLE = Main.VALUE_TABLE;
            String prefix = Main.config.getProperty(event.getGuild().getId().toString());
	    	if (prefix == null)
	    		prefix = "!";
            if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "value")) {
                String isEnabled = "1";
                if (Main.config.getProperty("wizCmds" + event.getGuild().getId().toString()) != null) {
                    isEnabled = Main.config.getProperty("wizCmds" + event.getGuild().getId().toString());
                    }
                if (isEnabled.equals("1")) {
                String[] item = event.getMessage().getContentRaw().split(prefix + "value ");
                Scanner lineScan = new Scanner(VALUE_TABLE);
                String final_item = "";
                while (lineScan.hasNextLine()) {
                    String token = lineScan.nextLine();
                    String[] tokenSpl = token.split(",");
                    if (tokenSpl[0].toLowerCase().equals(item[1].toLowerCase())) {
                        final_item = token;
                    }
                }
                lineScan.close();
                String[] item_split = final_item.split(",");
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Wizard101 Value Guides");
                Random rand = new Random();
                embed.addField("Item:", item_split[0], false);
                if (item_split.length == 2) {
                embed.addField("Value:", item_split[1], false);
                }
                if (item_split.length == 3) {
                    if (item_split[1].matches(".*[a-z].*")) {
                        embed.addField("School:", item_split[1], false);
                    } else {
                        embed.addField("Rank:", item_split[1], false);
                    }
                    embed.addField("Value (empowers):", item_split[2], false);
                }
                embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            	embed.setFooter("CharizardBot Team, Gamma's Trading Post. https://gtp.gg.", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
                event.getChannel().sendMessage(embed.build()).queue();
            }
            }
            //SEARCH command - Searches for multiple values with the same name.
            if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "search")) {
                String isEnabled = "1";
                if (Main.config.getProperty("wizCmds" + event.getGuild().getId().toString()) != null) {
                    isEnabled = Main.config.getProperty("wizCmds" + event.getGuild().getId().toString());
                    }
                if (isEnabled.equals("1")) {
                String[] item = event.getMessage().getContentRaw().split(prefix + "search ");
                if (item[1].length() < 2) {
                    event.getChannel().sendMessage("Error, please specify 2 or more letters in your query.").queue();
                 } else {
                Scanner lineScan = new Scanner(VALUE_TABLE);
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Wizard101 Value Guides");
                Random rand = new Random();
                String final_item = "";
                while (lineScan.hasNextLine()) {
                    String token = lineScan.nextLine();
                    String[] tokenSpl = token.split(",");
                    if (tokenSpl[0].toLowerCase().contains(item[1].toLowerCase()) && embed.getFields().size() < 23 && !token.contains("Table")) {
                        final_item = token;
                        String[] item_split = final_item.split(",");
                        embed.addField("Item:", item_split[0], true);
                        if (item_split.length == 2) {
                        embed.addField("Value (empowers):", item_split[1], true);
                        embed.addBlankField(true);
                        }
                        if (item_split.length == 3) {
                            if (item_split[1].matches(".*[a-z].*")) {
                                embed.addField("School:", item_split[1], true);
                            } else {
                                embed.addField("Rank:", item_split[1], true);
                            }
                            embed.addField("Value (empowers):", item_split[2], true);
                        }
                    }
                }
                lineScan.close();
                embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            	embed.setFooter("CharizardBot Team, Gamma's Trading Post. https://gtp.gg.", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
                event.getChannel().sendMessage(embed.build()).queue();
            }
        }
            }
        } catch (Exception e) {
            Main.logger.info("WARN: Exception in the ValueGuides command.");
            e.printStackTrace();
        }
    }
}
