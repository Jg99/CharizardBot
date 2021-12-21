package com.charizardbot.main.commands;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.charizardbot.main.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BulkDelete extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild()) {

        String prefix = Main.config.getProperty(event.getGuild().getId().toString());
        if (prefix == null)
            prefix = "!";
        try {
        String logChan = "";
        try {
            logChan = Main.logging_config.getProperty("logchannel" + event.getGuild().getId().toString().toString());
        } catch(Exception e) {};
        if (event.getMessage().getContentRaw().startsWith(prefix + "msgclr") && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
            event.getChannel().sendTyping().queue();
            String[] arguments = event.getMessage().getContentRaw().split("\\s+");
            //0 = command, 1 = # of messages, up to 100, 2 = User filter if any
            CompletableFuture<List<Message>> toDelete;
            if (arguments.length == 2) {
                if (Integer.parseInt(arguments[1]) > 100) {
                    arguments[1] = "100";
                }
                toDelete = event.getChannel().getIterableHistory().takeAsync(Integer.parseInt(arguments[1]))
                    .thenApply(list -> list.stream().collect(Collectors.toList()));
                    Collection<Message> td;
                    td = toDelete.get();
                    Main.bulkCount = td.size();
                    Main.isBulkDeleted = true;
                    event.getTextChannel().deleteMessages(td).queue();
                    event.getChannel().sendMessage("Deleted " + arguments[1]  + " messages in bulk.").queue(response -> {
                        response.delete().queueAfter(5, TimeUnit.SECONDS);
                    }); 
                    if (!logChan.equals("") && logChan != null) {
                        EmbedBuilder logEmbed = new EmbedBuilder();
                        logEmbed.setTitle("Bulk Delete Event");
                        logEmbed.addField("A bulk delete message has been detected in " + event.getChannel().getName(), "Admin: " + event.getMessage().getAuthor().getAsMention(), false);
                        event.getJDA().getTextChannelById(logChan).sendMessageEmbeds(logEmbed.build()).queue();
                    }
            } else if (arguments.length == 3) {
                if (Integer.parseInt(arguments[1]) > 100) {
                    arguments[1] = "100";
                }
                String id = arguments[2].replaceAll("[^\\d.]", "");
                toDelete = event.getChannel().getIterableHistory().takeAsync(Integer.parseInt(arguments[1]))
                .thenApply(list -> list.stream()
                .filter(m -> m.getAuthor().getId().equals(id))
                .collect(Collectors.toList()));
                Collection<Message> td;
                td = toDelete.get();
                Main.isBulkDeleted = true;
                Main.bulkCount = td.size();
                event.getTextChannel().deleteMessages(td).queue();
                event.getChannel().sendMessage("Deleted " + arguments[1]  + " messages in bulk.").queue(response -> {
                    response.delete().queueAfter(5, TimeUnit.SECONDS);
                }); 
            } else {
                event.getChannel().sendMessage("Invalid arguments. Please specify a number of messages and a user if possible.").queue();
            }
        }
    } catch (Exception e) {}
    }
}
}