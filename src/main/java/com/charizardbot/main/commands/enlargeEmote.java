package com.charizardbot.main.commands;
import java.util.List;

import com.charizardbot.main.Main;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class enlargeEmote extends ListenerAdapter {
    public void onMessageReceived (MessageReceivedEvent event) {
        if (event.isFromGuild()) {

        String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
            prefix = "!";  
        if (!event.getMessage().getMentions().getEmotes().isEmpty() && event.getMessage().getContentRaw().startsWith(prefix + "enlarge")) {
            List<Emote> emotes = event.getMessage().getMentions().getEmotes();
            String urls = "";
            for (Emote a : emotes) {
                urls += a.getImageUrl() + "\n";
            }
            event.getChannel().sendMessage(urls).queue();
        }
    }
    
}
}