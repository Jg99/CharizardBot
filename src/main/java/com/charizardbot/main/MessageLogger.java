package com.charizardbot.main;
import java.io.FileOutputStream;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class MessageLogger extends ListenerAdapter {
    /**
     * CharizardBot's Message Logger. Logs deleted messages (if they are in the cache) and edited messages.
     */
    public void onGuildMessageDelete(MessageDeleteEvent event) { 
        if (event.isFromGuild()) {
        String serverID = event.getGuild().getId().toString();
        try {
        String svrLogging = "0"; //disabled by default
        String isChannelIgnored = "0";
        String logChan = "";
        if (Main.logging_config.getProperty("isMsgLoggingEnabled" + serverID) == null) {
            svrLogging = "0";
            Main.logging_config.setProperty("isMsgLoggingEnabled" + serverID, "0");
            Main.output = new FileOutputStream("logConfig.cfg");
            Main.logging_config.store(Main.output, null);
        } else {
            svrLogging = Main.logging_config.getProperty("isMsgLoggingEnabled" + serverID);
        }
        logChan = Main.logging_config.getProperty("logchannel" + serverID.toString());
          if (Main.logging_config.getProperty("isChannelIgnored" + event.getChannel().getId()) == null) {
            isChannelIgnored = "0";
            Main.logging_config.setProperty("isChannelIgnored" + event.getChannel().getId(), "0");
            Main.output = new FileOutputStream("logConfig.cfg");
            Main.logging_config.store(Main.output, null);
          } else {
            isChannelIgnored = Main.logging_config.getProperty("isChannelIgnored" + event.getChannel().getId());
          }
          
        if (logChan != null && !logChan.isEmpty() && svrLogging.equals("1")) {
        String msgId = event.getMessageId();
        Message msg = Main.msgCache.getMessage(msgId);
        if (event.getJDA().getTextChannelById(logChan).canTalk() && msg != null && !msg.getAuthor().isBot() && !Main.isChatFilterDeleted && !Main.isBulkDeleted && isChannelIgnored.equals("0")) {
            EmbedBuilder logEmbed = new EmbedBuilder();
            logEmbed.setTitle("Deleted Message");
            logEmbed.addField("from: " + msg.getAuthor().getAsTag() + " in #" + event.getChannel().getName() + "\nMessage:", msg.getContentRaw(), false);
            String attUrls = "";
            if (!msg.getAttachments().isEmpty()) {
                for (int i = 0; i < msg.getAttachments().size(); i++)
                {
                    attUrls += msg.getAttachments().get(i).getProxyUrl() + "\n";
                }
                logEmbed.addField("Attachments:", attUrls, true);
            }
            logEmbed.setFooter("User ID: " + msg.getAuthor().getId());
            event.getJDA().getTextChannelById(logChan).sendMessageEmbeds(logEmbed.build()).queue();
        }
        if (event.getJDA().getTextChannelById(logChan).canTalk() && msg == null && !Main.isChatFilterDeleted && !Main.isBulkDeleted && isChannelIgnored.equals("0")){
            EmbedBuilder logEmbed = new EmbedBuilder();
            logEmbed.setTitle("Deleted Message");
            logEmbed.addField("A deleted message has been detected in " + event.getChannel().getName(), "Message is not in cache.", false);
            event.getJDA().getTextChannelById(logChan).sendMessageEmbeds(logEmbed.build()).queue();
        }
        if (Main.isChatFilterDeleted)
        {
            Main.isChatFilterDeleted = false;  
        }
        if (Main.bulkCount > Main.curMsgLog) {
            Main.curMsgLog++;
        }
        if (Main.bulkCount == Main.curMsgLog && Main.bulkCount > 0)
        {
            Main.isBulkDeleted = false;
            Main.bulkCount = 0;
            Main.curMsgLog = 0;
        }
    }
    } catch (Exception e) {
          //  Main.logger.info("Exception in MessageLogger (Deleted message event). Insufficient permissions or no cache?" + e);
          //  e.printStackTrace();
        }
    }
    }
    public void onMessageUpdate(MessageUpdateEvent event)
    {
        if (event.isFromGuild()) {
        try {
            String serverID = event.getGuild().getId().toString();
            String svrLogging = "0"; //disabled by default
            String isChannelIgnored = "0";
            String logChan = "";
            if (Main.logging_config.getProperty("isMsgLoggingEnabled" + serverID) == null) {
                svrLogging = "0";
                Main.logging_config.setProperty("isMsgLoggingEnabled" + serverID, "0");
                Main.output = new FileOutputStream("logConfig.cfg");
                Main.logging_config.store(Main.output, null);
            } else {
                svrLogging = Main.logging_config.getProperty("isMsgLoggingEnabled" + serverID);
            }
            logChan = Main.logging_config.getProperty("logchannel" + serverID.toString());
              if (Main.logging_config.getProperty("isChannelIgnored" + event.getChannel().getId()) == null) {
                isChannelIgnored = "0";
                Main.logging_config.setProperty("isChannelIgnored" + event.getChannel().getId(), "0");
                Main.output = new FileOutputStream("logConfig.cfg");
                Main.logging_config.store(Main.output, null);
              } else {
                isChannelIgnored = Main.logging_config.getProperty("isChannelIgnored" + event.getChannel().getId());
              }    
            logChan = Main.logging_config.getProperty("logchannel" + serverID.toString());
            if (event.getJDA().getTextChannelById(logChan).canTalk() && logChan != null && !logChan.isEmpty() && svrLogging.equals("1") && isChannelIgnored.equals("0")) {
                EmbedBuilder logEmbed = new EmbedBuilder();
                logEmbed.setTitle("Edited Message");
                logEmbed.addField("from: " + event.getAuthor().getAsTag() + " in #" + event.getChannel().getName() + "\nNew content:", event.getMessage().getContentRaw(), false);
                logEmbed.setFooter("User ID: " + event.getAuthor().getId());
                event.getJDA().getTextChannelById(logChan).sendMessageEmbeds(logEmbed.build()).queue(); 
            }
        } catch(Exception e) { /*Main.logger.info("Exception in MessageLogger (Edited message event). Insufficient permissions or no cache?" + e);*/}
        }
    }
}