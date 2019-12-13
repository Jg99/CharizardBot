package com.charizardbot.four.commands;
import com.charizardbot.four.Main;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class PingCommand extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        try {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("!ping")) {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                    .queue(response /* => Message */ -> {
                        response.editMessageFormat("Latency: %d ms. CharizardBot version %s", System.currentTimeMillis() - time, Main.VERSION).queue();
                    });
        }
    } catch (Exception e){Main.logger.info("WARN: Exception in Ping command: Insufficient permissions?\n" + e);}
    }
    }