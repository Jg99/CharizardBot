package com.charizardbot.main.commands;
import com.charizardbot.main.Main;

import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class PingCommand extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild()) {

        String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
        try {
        if (event.getMessage().getContentRaw().toLowerCase().equals(prefix + "ping")) {
            event.getChannel().sendTyping().queue();
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong! Charizardbot version " + Main.VERSION) /* => RestAction<Message> */
                    .queue(response /* => Message */ -> {
                        response.editMessageFormat("Latency: %d ms. CharizardBot version %s", System.currentTimeMillis() - time, Main.VERSION).queue();
                    });
        }
    } catch (Exception e){Main.logger.info("WARN: Exception in Ping command: Insufficient permissions?\n" + e);}
    }
    }
}