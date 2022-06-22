package com.charizardbot.main.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;
import com.charizardbot.main.Main;
public class ValueGuides extends ListenerAdapter {
    /**
     * This command is what the user runs to get the value of an item [Wizard101].
     */
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild()) {

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
     //           boolean isWhitelisted = Main.VG_WHITELIST.contains(event.getGuild().getId());//check the whitelist for value guides
     //           if (!isWhitelisted) {
     //               event.getChannel().sendMessage("Server is not whitelisted. Please contact the bot's owner for details.").queue();
     //           }
                if (isEnabled.equals("1")) {//&& isWhitelisted) {
                String item = event.getMessage().getContentRaw().substring(7, event.getMessage().getContentRaw().length());
                Scanner lineScan = new Scanner(VALUE_TABLE);
                String final_item = "";
                while (lineScan.hasNextLine()) {
                    String token = lineScan.nextLine();
                    String[] tokenSpl = token.split(",");
                    if (tokenSpl[0].toLowerCase().equals(item.toLowerCase())) {
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
                if (item_split.length == 4) {
                    embed.addField("Summon:", item_split[1], true);
                    embed.addField("Expel: ", item_split[2], true);
                    embed.addField("Housing: ", item_split[3], true);
                }
                embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            	embed.setFooter("CharizardBot Team, Gamma's Trading Post. https://gtp.gg.\nPlease note some values may not be up-to-date with constantly changing supply/demand and may be undergoing an audit. For more info and a list of these items, see the main VG spreadsheet located in #value-guides", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
            }
            }
            //SEARCH command - Searches for multiple values with the same name.
            if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "search")) {
                String isEnabled = "1";
                if (Main.config.getProperty("wizCmds" + event.getGuild().getId().toString()) != null) {
                    isEnabled = Main.config.getProperty("wizCmds" + event.getGuild().getId().toString());
                    }
       //         boolean isWhitelisted = Main.VG_WHITELIST.contains(event.getGuild().getId());//check the whitelist for value guides
       //         if (!isWhitelisted) {
       //             event.getChannel().sendMessage("Server is not whitelisted. Please contact the bot's owner for details.").queue();
       //         }
                if (isEnabled.equals("1")) {// && isWhitelisted) {
                    String item = event.getMessage().getContentRaw().substring(8, event.getMessage().getContentRaw().length());
                    if (item.length() < 2) {
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
                    
                    if (tokenSpl[0].toLowerCase().contains(item.toLowerCase()) && embed.getFields().size() < 23 && !token.contains("Table")) {
                        final_item = token;
                        String[] item_split = final_item.split(",");
                        embed.addField("Item:", item_split[0], false);
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
                        if (item_split.length == 4) {
                            embed.addField("Summon:", item_split[1], true);
                            embed.addField("Expel: ", item_split[2], true);
                            embed.addField("Housing: ", item_split[3], true);
                        }
                        if (embed.getFields().size() >= 7) {
                            embed.addField("For more results", "Please refine your search if your item isn't on this list.", false);
                            break;
                        }
                    }
                }
                lineScan.close();
                embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            	embed.setFooter("CharizardBot Team, Gamma's Trading Post. https://gtp.gg.\nPlease note some values may not be up-to-date with constantly changing supply/demand and may be undergoing an audit. For more info and a list of these items, see the main VG spreadsheet located in #value-guides", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                }
            }
        }
            if (event.getMessage().getContentRaw().contains(prefix + "addvgserver")) {
                String whitelistedUsers = "228736231335264256\n184534810369196032\n115305611842945032\n308060090739720195";
                if (whitelistedUsers.contains(event.getAuthor().getId())) {
                    String[] split = event.getMessage().getContentRaw().split(prefix + "addvgserver ");
                    String serverID = split[1];
                    String servers = Main.VG_WHITELIST;
                    if (servers.contains(serverID)) {
                        event.getChannel().sendMessage("Server is already whitelisted for value guides").queue();
                    } else {
                        servers += serverID + "\n";
                        BufferedWriter writer = new BufferedWriter(new FileWriter("vgwhitelist.txt"));
                        writer.write(servers);
                        writer.close();
                        Main.VG_WHITELIST= servers;
                        event.getChannel().sendMessage("Added the server to the value guides whitelist").queue();
                    }
                }
            }
            if (event.getMessage().getContentRaw().startsWith(prefix + "remvgserver")) {
                String whitelistedUsers = "228736231335264256\n184534810369196032\n115305611842945032\n308060090739720195";
                if (whitelistedUsers.contains(event.getAuthor().getId())) {
                    String[] split = event.getMessage().getContentRaw().split(prefix + "remvgserver ");
                    String serverID = split[1];
                    String servers = Main.VG_WHITELIST;
                    String[] lines = servers.split("\n");
                    for (int i = 0; i < lines.length; i++) {
                        if (lines[i].contains(serverID)) {
                            lines[i] = "";
                        }
                    }
                    StringBuilder svList = new StringBuilder();
                    for (String s : lines) {
                        if (!s.equals("")) {
                            svList.append(s).append("\n");
                        }
                    }
                    servers = svList.toString();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("vgwhitelist.txt"));
                    writer.write(servers);
                    writer.close();
                    Main.VG_WHITELIST = servers;
                    event.getChannel().sendMessage("Removed the server from the value guides whitelist.").queue();
                }
            }
        } catch (Exception e) {
            Main.logger.info("WARN: Exception in the ValueGuides command.");
            e.printStackTrace();
        }
    }
    }
}