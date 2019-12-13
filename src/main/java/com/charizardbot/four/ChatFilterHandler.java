package com.charizardbot.four;
import java.awt.Color;
import java.io.FileOutputStream;
import net.dv8tion.jda.api.Permission;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class ChatFilterHandler extends ListenerAdapter {
    /**
     * Checks all guilds for any words against the chat filter, if it's enabled in a server.
     */
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
                try {
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
                                 Main.config.setProperty("chatfilter" + event.getGuild().getId(), "1");
                                 Main.config.store(Main.output, null);
                             }
                             //check for logging enabled/set channel
                             String logChannel = "";
                             //get config defaults
                             String chfilter = "1"; //enabled by default
                             String svrLogging = "0"; //disabled by default
                             String svrfilter = "1"; //enabled by default
                             try {
                             chfilter = Main.config.getProperty("chanfilter" + event.getChannel().getId());
                             svrfilter = Main.config.getProperty("filter" + event.getGuild().getId());
                             logChannel = Main.config.getProperty("logchannel" + event.getGuild().getId());
                             svrLogging = Main.config.getProperty("isLoggingEnabled" + event.getGuild().getId());
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                     if (Main.filter.isFilteredWord() == true && !event.getAuthor().isBot() && svrfilter.equals("1") && chfilter.equals("1"))
                     {
                        if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                            event.getMessage().delete().reason("bad language").queue();          
                         EmbedBuilder embed = new EmbedBuilder();
                         embed.setTitle("Inappropriate language detected from: " + event.getAuthor().getAsTag());
                         embed.addField("Watch your language!", "The filter has deleted the " +
                         "message containing the bad word. Do not try to bypass the Main.filter", true);
                         embed.setFooter("User ID: " +
                         event.getMember().getId(), "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
                         Random rand = new Random();
                         embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                         event.getChannel().sendMessage(embed.build()).queue(response -> {
                            response.delete().queueAfter(5, TimeUnit.SECONDS);
                        });                        //Log to channel
                         if (svrLogging.equals("1") && logChannel != ""){
                              if (event.getJDA().getTextChannelById(logChannel).canTalk()) {
                              EmbedBuilder logEmbed = new EmbedBuilder();
                              logEmbed.setTitle("CharizardBot Chat Filter Logging");
                              logEmbed.addField("CharizardBot Filter has removed the following message for bad language from @" + event.getAuthor().getAsTag() + " in #" + event.getChannel().getName() + ":", event.getMessage().getContentRaw(), false);
                              logEmbed.setAuthor(event.getAuthor().getAsTag());
                              logEmbed.setFooter("User ID: " + event.getMember().getId());
                              event.getJDA().getTextChannelById(logChannel).sendMessage(logEmbed.build()).queue();
                              }
                         }
                     }
                    }
                    }
                    } catch (Exception e) {Main.logger.info("WARN: Exception in ChatFilterHandler:\n" + e);}
    }
}