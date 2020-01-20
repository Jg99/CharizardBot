package com.charizardbot.four.commands;

import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.TimePeriod;
import net.dean.jraw.pagination.DefaultPaginator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.Random;
import com.charizardbot.four.Main;
public class RedditCommands extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        try {
            String prefix = Main.config.getProperty(event.getGuild().getId());
            if (prefix == null)
                prefix = "!";
            String redditCommands = "1";
            if (Main.config.getProperty("redditCmds" + event.getGuild().getId()) != null) {
                redditCommands = Main.config.getProperty("redditCmds" + event.getGuild().getId());
            }
            String redditNsfw = "0";
            if (Main.config.getProperty("redditNsfw" + event.getGuild().getId()) != null) {
                redditNsfw = Main.config.getProperty("redditNsfw" + event.getGuild().getId());
            }
            /**
             * Random post using a specified subreddit. If not, will get from r/all from today.
             */
            if (event.getMessage().getContentRaw().startsWith(prefix + "randpost") && redditCommands.equals("1")) {
            String[] arguments = event.getMessage().getContentRaw().split("\\s+");
            String subreddit = "all"; // r/all by default
            SubredditSort subSort = SubredditSort.TOP;
            TimePeriod time = TimePeriod.ALL;
            String sortMsg = "Random top post from r/";
            if (arguments.length > 1)
            {
                subreddit = arguments[1];
            } else {
                subSort = SubredditSort.HOT;
                time = TimePeriod.DAY;
                sortMsg = "Random hot post from r/";
            }
		    DefaultPaginator<Submission> paginator = Main.reddit.subreddit(subreddit)
		    .posts()
	    	.limit(100)
         	.sorting(subSort) // top posts
            .timePeriod(time) // of all time
	    	.build();
	    	Random rand = new Random();
	    	int rPost = rand.nextInt(100);
            Listing<Submission> posts = paginator.next();
            if (event.getChannel().isNSFW() == true && posts.get(rPost).isNsfw() && redditNsfw.equals("1")) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle(sortMsg + subreddit, posts.get(rPost).getUrl());
                embed.addField(posts.get(rPost).getTitle(), "By: u/" + posts.get(rPost).getAuthor(), false);
                embed.addField("Comments: ", posts.get(rPost).getCommentCount() + "", false);
                embed.addField("Comments Link:", "https://reddit.com" + posts.get(rPost).getPermalink(), false);
                if (posts.get(rPost).isSelfPost()) {
                    String body = posts.get(rPost).getSelfText();
                    if (body.length() > 500 && body != null) {
                        body = body.substring(0, 500) + "...";
                    }
                    embed.addField("Preview of self post content:",body,false);
                } else {                    
                    if (posts.get(rPost).getUrl().matches(".*(png|jpg|i.redd.it|i.imgur|bmp).*")) {
                        embed.setImage(posts.get(rPost).getUrl());
                 } else {
                        if (posts.get(rPost).hasThumbnail()) {
                            embed.setThumbnail(posts.get(rPost).getThumbnail());
                         }
                    }
                }
                embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                event.getChannel().sendMessage(embed.build()).queue();            
            }
            else if ((!event.getChannel().isNSFW() || redditNsfw.equals("0")) && posts.get(rPost).isNsfw()) {
                event.getChannel().sendMessage("Post is NSFW, channel is not NSFW or nsfw flag is set to disable.").queue();
            }
            else {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle(sortMsg + subreddit, posts.get(rPost).getUrl());
                embed.addField(posts.get(rPost).getTitle(), "By: u/" + posts.get(rPost).getAuthor(), false);
                embed.addField("Comments: ", posts.get(rPost).getCommentCount() + "", false);
                embed.addField("Comments Link:", "https://reddit.com" + posts.get(rPost).getPermalink(), false);
                if (posts.get(rPost).isSelfPost()) {
                    String body = posts.get(rPost).getSelfText();
                    if (body.length() > 500 && body != null) {
                        body = body.substring(0, 500) + "...";
                    }
                    embed.addField("Preview of self post content:",body,false);
                } else {                    
                    if (posts.get(rPost).getUrl().matches(".*(png|jpg|i.redd.it|i.imgur|bmp).*")) {
                        embed.setImage(posts.get(rPost).getUrl());
                 } else {
                        if (posts.get(rPost).hasThumbnail()) {
                            embed.setThumbnail(posts.get(rPost).getThumbnail());
                        }
                    }
                }
                embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                event.getChannel().sendMessage(embed.build()).queue();            
            }
        }
             /**
            * Randomly pick a meme from Reddit - to do
            */
//            if (event.getMessage().getContentRaw().startsWith(prefix + "randmeme") && redditCommands.equals("1")) {
//            }
        	//TOGGLE CMDS
            if (event.getMessage().getContentRaw().toLowerCase().contains(prefix + "togglereddit") && !event.getAuthor().isBot() && (event.getAuthor().equals(event.getJDA().getUserById(Main.OWNER_ID)) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
                Main.output = new FileOutputStream("server_config.cfg");
                boolean wasNull = false;
                boolean wasChanged = false;
                String toggle = Main.config.getProperty("redditCmds" + event.getGuild().getId());
                if (toggle == null) {
                    toggle = "1";
                    Main.config.setProperty("redditCmds" + event.getGuild().getId(), toggle);
                    Main.config.store(Main.output, null);
                    wasNull = true;
                    wasChanged = true;
                    event.getChannel().sendMessage("No toggle was set for Reddit Commands. Set to on by default. Please run again to turn off.").queue();
                    }
                if (!wasNull ) {
                    if (toggle.equals("0") && !wasChanged) {
                        toggle = "1";
                        wasChanged = true;
                        Main.config.setProperty("redditCmds" + event.getGuild().getId(), toggle);
                        Main.config.store(Main.output, null);
                        event.getChannel().sendMessage("Turned on Reddit commands.").queue();
                    }
                    if (toggle.equals("1") && !wasChanged) {
                        toggle = "0";
                        wasChanged = false;
                        Main.config.setProperty("redditCmds" + event.getGuild().getId(), toggle);
                        Main.config.store(Main.output, null);
                        event.getChannel().sendMessage("Turned off Reddit commands.").queue();
                    }  			
                }  
                Main.config.setProperty("redditCmds" + event.getGuild().getId(), toggle);
                }
                if (event.getMessage().getContentRaw().toLowerCase().contains(prefix + "redditnsfw") && !event.getAuthor().isBot() && (event.getAuthor().equals(event.getJDA().getUserById(Main.OWNER_ID)) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
                    Main.output = new FileOutputStream("server_config.cfg");
                    boolean wasNull = false;
                    boolean wasChanged = false;
                    String toggle = Main.config.getProperty("redditNsfw" + event.getGuild().getId());
                    if (toggle == null) {
                        toggle = "1";
                        Main.config.setProperty("redditNsfw" + event.getGuild().getId(), toggle);
                        Main.config.store(Main.output, null);
                        wasNull = true;
                        wasChanged = true;
                        event.getChannel().sendMessage("No toggle was set for Reddit NSFW. Set to on by default. Only works in NSFW-enabled channels. Please run again to turn off.").queue();
                        }
                    if (!wasNull ) {
                        if (toggle.equals("0") && !wasChanged) {
                            toggle = "1";
                            wasChanged = true;
                            Main.config.setProperty("redditNsfw" + event.getGuild().getId(), toggle);
                            Main.config.store(Main.output, null);
                            event.getChannel().sendMessage("Turned on Reddit NSFW. Only works in NSFW-enabled channels.").queue();
                        }
                        if (toggle.equals("1") && !wasChanged) {
                            toggle = "0";
                            wasChanged = false;
                            Main.config.setProperty("redditNsfw" + event.getGuild().getId(), toggle);
                            Main.config.store(Main.output, null);
                            event.getChannel().sendMessage("Turned off Reddit NSFW.").queue();
                        }  			
                    }  
                    Main.config.setProperty("redditNsfw" + event.getGuild().getId(), toggle);
                    }
        } catch (Exception e) {Main.logger.info("Exception in RedditCommands.java"); e.printStackTrace();}
    }
}