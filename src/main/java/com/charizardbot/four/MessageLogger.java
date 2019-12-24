package com.charizardbot.four;
import java.io.FileOutputStream;
import com.charizardbot.four.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageLogger extends ListenerAdapter {
    /**
     * CharizardBot's Message Logger. Logs deleted messages (if they are in the cache) and edited messages.
     */
    public void onGuildMessageDelete(GuildMessageDeleteEvent event) {
        try {
        String svrLogging = "0"; //disabled by default
        String isChannelIgnored = "0";
        String logChan = "";
        svrLogging = Main.config.getProperty("isMsgLoggingEnabled" + event.getGuild().getId()); 
        logChan = Main.config.getProperty("logchannel" + event.getGuild().getId());
        try {
        isChannelIgnored = Main.config.getProperty("isChannelIgnored" + event.getChannel().getId());
        } catch (Exception e){
            isChannelIgnored = "0";
            Main.config.setProperty("isChannelIgnored" + event.getChannel().getId(), "0");
            Main.output = new FileOutputStream("server_config.cfg");
			Main.config.store(Main.output, null);
        }
        if (logChan != null && !logChan.isEmpty() && svrLogging.equals("1")) {
        String msgId = event.getMessageId();
        Message msg = Main.msgCache.getMessage(msgId);
        if (event.getJDA().getTextChannelById(logChan).canTalk() && msg != null && !msg.getAuthor().isBot() && !Main.isChatFilterDeleted && isChannelIgnored.equals("0")) {
            EmbedBuilder logEmbed = new EmbedBuilder();
            logEmbed.setTitle("Deleted Message");
            logEmbed.addField("from: " + msg.getAuthor().getAsTag() + " in #" + event.getChannel().getName() + "\nMessage:", msg.getContentRaw(), false);
            logEmbed.setFooter("User ID: " + msg.getAuthor().getId());
            event.getJDA().getTextChannelById(logChan).sendMessage(logEmbed.build()).queue();
        }
        if (event.getJDA().getTextChannelById(logChan).canTalk() && msg == null && !Main.isChatFilterDeleted && isChannelIgnored.equals("0")){
            EmbedBuilder logEmbed = new EmbedBuilder();
            logEmbed.setTitle("Deleted Message");
            logEmbed.addField("A deleted message has been detected in " + event.getChannel().getName(), "Message is not in cache.", false);
            event.getJDA().getTextChannelById(logChan).sendMessage(logEmbed.build()).queue();
        
        }
        if (Main.isChatFilterDeleted)
        {
            Main.isChatFilterDeleted = false;  
        }
    }
    } catch (Exception e) {
            Main.logger.info("Exception in MessageLogger (Deleted message event). Insufficient permissions or no cache?" + e);
        }
    }
    public void onGuildMessageUpdate(GuildMessageUpdateEvent event)
    {
        try {
            String isChannelIgnored = "0";
            String svrLogging = "0"; //disabled by default
            String logChan = "";
            try {
                isChannelIgnored = Main.config.getProperty("isChannelIgnored" + event.getChannel().getId());
                } catch (Exception e){
                    isChannelIgnored = "0";
                    Main.config.setProperty("isChannelIgnored" + event.getChannel().getId(), "0");
                    Main.output = new FileOutputStream("server_config.cfg");
                    Main.config.store(Main.output, null);
            }
            svrLogging = Main.config.getProperty("isLoggingEnabled" + event.getGuild().getId());
            logChan = Main.config.getProperty("logchannel" + event.getGuild().getId());
            if (logChan != null && !logChan.isEmpty() && svrLogging.equals("1") && isChannelIgnored.equals("0")) {
                EmbedBuilder logEmbed = new EmbedBuilder();
                logEmbed.setTitle("Edited Message");
                logEmbed.addField("from: " + event.getAuthor().getAsTag() + " in #" + event.getChannel().getName() + "\nNew content:", event.getMessage().getContentRaw(), false);
                logEmbed.setFooter("User ID: " + event.getAuthor().getId());
                event.getJDA().getTextChannelById(logChan).sendMessage(logEmbed.build()).queue(); 
            }
        } catch(Exception e) { Main.logger.info("Exception in MessageLogger (Edited message event). Insufficient permissions or no cache?" + e);}
}
}