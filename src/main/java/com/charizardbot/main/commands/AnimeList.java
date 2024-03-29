package com.charizardbot.main.commands;

import java.awt.Color;
import java.util.Collection;
import java.util.Random;
import com.charizardbot.main.Main;
import net.sandrohc.jikan.*;
import net.sandrohc.jikan.model.anime.Anime;
import net.sandrohc.jikan.model.anime.AnimeType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AnimeList extends ListenerAdapter {
    /**
     * CharizardBot's Anime Search command. Searches MyAnimeList based on the query
     * from the user and returns some information about the anime.
     * Uses Jikan4Java library, modified to use CharizardBot server.
     */
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild()) {

        try {
            String miscToggle = "1";
            if (Main.config.getProperty("miscCmds" + event.getGuild().getId().toString()) != null) {
                miscToggle = Main.config.getProperty("miscCmds" + event.getGuild().getId().toString());
            }
            String prefix = Main.config.getProperty(event.getGuild().getId().toString());
            if (prefix == null)
                prefix = "!";
            if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "anime") && miscToggle.equals("1")) {
                event.getChannel().sendTyping().queue();
                String query = event.getMessage().getContentRaw().substring(7, event.getMessage().getContentRaw().length());
                Jikan search = new Jikan();
                Random rand = new Random();
                EmbedBuilder embed = new EmbedBuilder();
                String genre = "";
                String title = "";
                String desc = "";
                String studiolist = "";
                String imageURL = "";
                String datePremiered = "";
                int episodeCount = 0;
                String url = "";
                //Get list, genre, studio, description
                Collection<Anime> results = search.query().anime().search()
                .query(query)
                .type(AnimeType.TV)             
                .limit(5)
                .safeForWork(true)
                .execute()
                .collectList()
                .block();
                Anime firstResult = results.iterator().next();
                genre = firstResult.getGenres().iterator().next().getName().displayName() != null ? firstResult.getGenres().iterator().next().getName().displayName : "";
                studiolist = firstResult.getStudios().iterator().next().name != null ? firstResult.getStudios().iterator().next().name : "Unknown";
                title = firstResult.getTitle() != null ? firstResult.getTitle() : "Unknown";
                desc = firstResult.getSynopsis() != null ? firstResult.getSynopsis() : "Unknown";
                if (desc.length() > 500) {
                    desc = desc.substring(0, 500) + "...";
                }
                imageURL = firstResult.getImages().getPreferredImageUrl()!= null ? firstResult.getImages().getPreferredImageUrl() : "Unknown";
                episodeCount = firstResult.getEpisodes() != null ? firstResult.getEpisodes() : 0;
                datePremiered = firstResult.aired.toString() != null ? firstResult.aired.toString() : "";
                url = firstResult.getUrl();
                embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                embed.setTitle("Results for your Anime search");
                embed.setThumbnail(imageURL);
                try {embed.addField("Title:", title, false);} catch (Exception e){embed.addField("Title:", title, false);}
                embed.addField("Description:", desc, false);
                embed.addField("Number of episodes:", "" + episodeCount, true);
                try {embed.addField("Premiered:", datePremiered, true);}catch (Exception e){embed.addField("Year:", "Unknown", true);}
                embed.addField("Genres:", genre, false);
                embed.addField("Studios", studiolist, false);
                embed.addField("URL:", url, false);
                embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
                if (event.getGuild().getTextChannelById(event.getChannel().getId()).canTalk()) {
                    String d = genre.toLowerCase();
                if (!d.contains("hentai")) {
                     event.getChannel().sendMessageEmbeds(embed.build()).queue();
                  }  else {
                     event.getChannel().sendMessage("You're a very naughty person aren't you ;)").queue();
                }
            }
        }
    } catch (Exception e) {
        Main.logger.info("WARN: Exception in AnimeList command." + e);
        e.printStackTrace();
    }
    }
}
}