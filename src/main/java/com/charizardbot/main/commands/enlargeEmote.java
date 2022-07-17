package com.charizardbot.main.commands;
import java.util.List;

import com.charizardbot.main.Main;

import net.dv8tion.jda.api.entities.emoji.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class enlargeEmote extends ListenerAdapter {
    public void onMessageReceived (MessageReceivedEvent event) {
        if (event.isFromGuild()) {

        String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
            prefix = "!";  
        if (!event.getMessage().getMentions().getCustomEmojis().isEmpty() && event.getMessage().getContentRaw().startsWith(prefix + "enlarge")) {
            List<CustomEmoji> emojis = event.getMessage().getMentions().getCustomEmojis();
            String urls = "";
            for (CustomEmoji a : emojis) {
                urls += a.getImageUrl() + "\n";
            }
            event.getChannel().sendMessage(urls).queue();
        }
    }
    
}
}