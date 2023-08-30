package com.charizardbot.main;
import java.awt.Color;
import java.io.FileOutputStream;
import net.dv8tion.jda.api.Permission;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class ChatFilterHandler extends ListenerAdapter {
    /**
     * Checks all guilds for any words against the chat filter, if it's enabled in a server.
     */
	public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild()) {

                try {
                    String serverID = event.getGuild().getId().toString();
                    {
                            String normalizedText = Normalizer
                            //parse text
                            .normalize(event.getMessage().getContentRaw().toLowerCase(), Form.NFD)
                            .replaceAll("[^\\p{ASCII}]", "") // so stuff like tést and fóóbår are filtered as well.
                                  .replaceAll(" ", ",");  // so chat filter can work with spaces, otherwise will break
                                Main.filter = new ChatFilter(normalizedText.toLowerCase(), Main.filterDB.toLowerCase());
                                Main.filter.checkFilter();
                            //check config
                             if (Main.config.getProperty("chanfilter" + event.getChannel().getId()) == null)
                             {
                                 Main.output = new FileOutputStream("server_config.cfg");
                                 Main.config.setProperty("chanfilter" + event.getChannel().getId(), "1");
                                 Main.config.store(Main.output, null);
                             }
                             if (Main.config.getProperty("chatfilter" + event.getChannel().getId()) == null)
                             {
                                  Main.output = new FileOutputStream("server_config.cfg");
                                 Main.config.setProperty("chatfilter" + serverID, "1");
                                 Main.config.store(Main.output, null);
                             }
                     if (Main.filter.isFilteredWord() == true && !event.getAuthor().isBot()) {
                      //check for logging enabled/set channel
                      String logChannel = "";
                      //get config defaults
                      String chfilter = "1"; //enabled by default
                      String svrLogging = "0"; //disabled by default
                      String svrfilter = "1"; //enabled by default
                      try {
                            chfilter = Main.config.getProperty("chanfilter" + event.getChannel().getId());
                            svrfilter = Main.config.getProperty("filter" + serverID);
                            logChannel = Main.logging_config.getProperty("logchannel" + serverID.toString());
                            svrLogging = Main.logging_config.getProperty("isLoggingEnabled" + serverID);
                            } catch (Exception e) { 
                                e.printStackTrace();
                        }
                        if (svrfilter.equals("1") && chfilter.equals("1")) {
                        if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                            Main.isChatFilterDeleted = true;
                            event.getMessage().delete().reason("bad language").queue();        
                         EmbedBuilder embed = new EmbedBuilder();
                         embed.setTitle("Inappropriate language detected from: " + event.getAuthor().getName());
                         embed.addField("Watch your language!", "The filter has deleted the " +
                         "message containing the bad word or a malicious URL. Do not try to bypass the filter", true);
                         embed.setFooter("User ID: " +
                         event.getMember().getId(), "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
                         Random rand = new Random();
                         embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                         event.getChannel().sendMessageEmbeds(embed.build()).queue(response -> {
                            response.delete().queueAfter(5, TimeUnit.SECONDS);
                        });                        //Log to channel
                         if (svrLogging.equals("1") && logChannel != ""){
                              if (event.getJDA().getTextChannelById(logChannel).canTalk()) {
                                EmbedBuilder logEmbed = new EmbedBuilder();
                                logEmbed.setTitle("CharizardBot Chat Filter Logging");
                                logEmbed.addField("CharizardBot Filter has removed the following message for bad language from @" + event.getAuthor().getName() + " in #" + event.getChannel().getName() + ":", event.getMessage().getContentRaw(), false);
                                logEmbed.setAuthor(event.getAuthor().getName());
                                logEmbed.setFooter("User ID: " + event.getMember().getId());
                                event.getJDA().getTextChannelById(logChannel).sendMessageEmbeds(logEmbed.build()).queue();
                              }
                         }
                     }
                    }
                    }
                    }
                    } catch (Exception e) {Main.logger.info("WARN: Exception in ChatFilterHandler:\n" + e);}
                }
    }
}