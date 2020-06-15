package com.charizardbot.four.commands;
import java.util.List;
import com.charizardbot.four.Main;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class enlargeEmote extends ListenerAdapter {
    public void onGuildMessageReceived (GuildMessageReceivedEvent event) {
        String prefix = Main.config.getProperty(event.getGuild().getId());
    	if (prefix == null)
            prefix = "!";  
        if (!event.getMessage().getEmotes().isEmpty() && event.getMessage().getContentRaw().startsWith(prefix + "enlarge")) {
            List<Emote> emotes = event.getMessage().getEmotes();
            System.out.println("Emote enlarger");
            String urls = "";
            for (Emote a : emotes) {
             urls += a.getImageUrl() + "\n";
            }
            event.getChannel().sendMessage(urls).queue();
        }
    }
    
}