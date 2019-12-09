package com.charizardbot.four.commands;
import java.awt.Color;
import java.util.Random;
import com.charizardbot.four.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class WizSchedule extends ListenerAdapter {
    /**
     * Wizard101 PVP Tournament schedule command
     */
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
            try {
            Random rand = new Random();
            String prefix = Main.config.getProperty(event.getGuild().getId());
            if (prefix == null)
                prefix = "!";
            if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "schedule") && !event.getAuthor().isBot()) {
                    String isEnabled = "1";
                    if (Main.config.getProperty("wizCmds" + event.getGuild().getId()) != null) {
                        isEnabled = Main.config.getProperty("wizCmds" + event.getGuild().getId());
                        }
                    if (isEnabled.equals("1")) {
                    String updatedTable = Main.table; //rename because KI's website is trash and doesn't use the actual names of the tournaments
                    updatedTable = updatedTable.replaceAll("LightningName", "Quick Match Tournament");
                    updatedTable = updatedTable.replaceAll("FireAndIceName", "Fire & Ice Perk Tournament");
                    updatedTable = updatedTable.replaceAll("OldSchoolName", "Classic Tournament");
                    updatedTable = updatedTable.replaceAll("AlternatingTurns_PipsAtOnceName", "Turn-Based Tournament");
                    updatedTable = updatedTable.replaceAll("MythAndStormName", "Myth & Storm Perk Tournament");
                    updatedTable = updatedTable.replaceAll("LifeAndDeathName", "Life & Death Perk Tournament");
                    updatedTable = updatedTable.replaceAll("BalanceName", "Balance Perk Tournament");
                    updatedTable = updatedTable.replaceAll("10 - 130", "-"); // be sure to change when wiz raises the level cap
                    //done changing bad names to good ones and removing level because every tourney is 10-130
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle("Tournament Schedule");
                    embed.setDescription("This schedule is automatically updated every 30 minutes from the Wizard101 tournament website.");
                    embed.setImage("https://i.imgur.com/sne9QoQ.jpg");
                    embed.addField("10 upcoming tourneys showing name and duration.", updatedTable, false);
                    embed.setFooter("Last updated: " + Main.lastUpdated);
                    embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                    event.getChannel().sendMessage(embed.build()).queue();
                    }
                }
            } catch (Exception e){Main.logger.info("Warn: Exception in Wiz Tournament command. Insufficient permissions?\n" + e);}
    }
}