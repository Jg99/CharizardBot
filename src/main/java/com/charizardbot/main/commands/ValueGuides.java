package com.charizardbot.main.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.Color;
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
            String MONSTRO_VALUE_TABLE = Main.MONSTRO_VALUE_TABLE;
            String prefix = Main.config.getProperty(event.getGuild().getId().toString());
	    	if (prefix == null)
	    		prefix = "!";
            //Look up the first value that matches the search
            if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "value") && !VALUE_TABLE.isEmpty()) {
                String isEnabled = "1";
                if (Main.config.getProperty("wizCmds" + event.getGuild().getId().toString()) != null) {
                    isEnabled = Main.config.getProperty("wizCmds" + event.getGuild().getId().toString());
                    }
                if (isEnabled.equals("1")) {
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
            	embed.setFooter("CharizardBot Team, Gamma's Trading Post, Anima Chambers.\n" + //
            	        " https://gtp.gg or https://www.animachambers.org/.\n" + //
            	        "Please note some values may not be up-to-date with constantly changing supply/demand and may be undergoing an audit. For more info and a list of these items, see the main VG spreadsheet located in #value-guides", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
            }
            }
            // MONSTRO VG command - Monstrology is more detailed, so a separate command and separate CSV will be used
            if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "monstro") && !MONSTRO_VALUE_TABLE.isEmpty()) {
                String isEnabled = "1";
                if (Main.config.getProperty("wizCmds" + event.getGuild().getId().toString()) != null) {
                    isEnabled = Main.config.getProperty("wizCmds" + event.getGuild().getId().toString());
                    }
                if (isEnabled.equals("1")) {
                String item = event.getMessage().getContentRaw().substring(9, event.getMessage().getContentRaw().length());
                Scanner lineScan = new Scanner(MONSTRO_VALUE_TABLE);
                String final_item = "";
                while (lineScan.hasNextLine()) {
                    String token = lineScan.nextLine();
                    String[] tokenSpl = token.split(",");
                    if (tokenSpl[0].toLowerCase().startsWith(item.toLowerCase())) {
                        final_item = token;
                    }
                }
                lineScan.close();
                String[] item_split = final_item.split(",");
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Wizard101 Monstrology Value Guides");
                Random rand = new Random();
                embed.addField("Creature:", item_split[0], false);
                if (item_split.length > 1) { 
                embed.addField("World:", item_split[1], false);
                embed.addField("Location:", item_split[2], true);
                embed.addField("Rank:", item_split[3], true);
                embed.addField("School:", item_split[4], true);
                embed.addField("Summon:", item_split[5], true);
                embed.addField("House Guest: ", item_split[6], true);
                embed.addField("Identical Guest Options: ", item_split[7], true);
                embed.addField("Expel:", item_split[8], true);
                embed.addField("Extract Type:", item_split[9], true);
                embed.addField("Tag:", item_split[10], true);
                
                        if (item_split[10].contains("Seasonal")) {
                             embed.addField("Additional Notes","The left value is the seasonal value, the right value is the normal value.", false);
                        } else if (item_split[10].contains("NoTome")) {
                            embed.addField("Additional Notes", "This creature can have its animus extracted, but no items can be made yet as the tome entry is not properly implemented in the game.", false);
                        }  else if (item_split[10].contains("AEB")) {
                            embed.addField("Additional Notes", "Some or all of this creature's items cannot be made due to a bug. The tome will take your animus and produce no item.", false);
                        }  else if (item_split[10].contains("Upcoming")) {
                            embed.addField("Additional Notes", "This creature is slated to be extractable in an upcoming update that is currently on Test Realm. These items cannot be created in Live Realm at this time and prices are preliminary, and HIGHLY subject to change based on community data.", false);
                        }
                }
                embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            	embed.setFooter("CharizardBot Team, Gamma's Trading Post, Anima Chambers.\n" + //
            	        " https://gtp.gg or https://www.animachambers.org/.\n" + //
            	        "Please note some values may not be up-to-date with constantly changing supply/demand and may be undergoing an audit. For more info and a list of these items, see the main VG spreadsheet located in #value-guides", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
            }
            }
            //SEARCH command - Searches for multiple values with the same name.
            if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "search") && !VALUE_TABLE.isEmpty()) {
                String isEnabled = "1";
                if (Main.config.getProperty("wizCmds" + event.getGuild().getId().toString()) != null) {
                    isEnabled = Main.config.getProperty("wizCmds" + event.getGuild().getId().toString());
                    }
                if (isEnabled.equals("1")) {
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
            	embed.setFooter("CharizardBot Team, Gamma's Trading Post, Anima Chambers.\n https://gtp.gg or https://www.animachambers.org/.\nPlease note some values may not be up-to-date with constantly changing supply/demand and may be undergoing an audit. For more info and a list of these items, see the main VG spreadsheet located in #value-guides", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
                event.getChannel().sendMessageEmbeds(embed.build()).queue();
                }
            }
        }
        } catch (Exception e) {
            Main.logger.info("WARN: Exception in the ValueGuides command.");
            e.printStackTrace();
        }
    }
    }
}