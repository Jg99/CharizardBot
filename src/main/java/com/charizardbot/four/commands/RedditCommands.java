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
            String prefix = Main.config.getProperty(event.getGuild().getId().toString());
            if (prefix == null)
                prefix = "!";
            String redditCommands = "1";
            if (Main.config.getProperty("redditCmds" + event.getGuild().getId().toString()) != null) {
                redditCommands = Main.config.getProperty("redditCmds" + event.getGuild().getId().toString());
            }
            String redditNsfw = "0";
            if (Main.config.getProperty("redditNsfw" + event.getGuild().getId().toString()) != null) {
                redditNsfw = Main.config.getProperty("redditNsfw" + event.getGuild().getId().toString());
            }
            /**
             * Random post using a specified subreddit. If not, will get from r/all from today.
             */
            if (event.getMessage().getContentRaw().startsWith(prefix + "randpost") && redditCommands.equals("1")) {
            event.getChannel().sendTyping().queue();
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
            //Subreddit sorting, if possible
            if (arguments.length > 2)
            {
                String sort = arguments[2].toLowerCase();
                switch (sort) {
                    case "top":
                        subSort = SubredditSort.TOP;
                        sortMsg = "Random top post from r/";
                        break;
                    case "hot":
                        subSort = SubredditSort.HOT;
                        sortMsg = "Random hot post from r/";
                        break;
                    case "new":
                        subSort = SubredditSort.NEW;
                        sortMsg = "Random new post from r/";
                        break;
                    case "rising":
                        subSort = SubredditSort.RISING;
                        sortMsg = "Random rising post from r/";
                        break;
                    case "controversial":
                        subSort = SubredditSort.CONTROVERSIAL;
                        sortMsg = "Random controversial post from r/";
                        break;
                    case "best":
                        subSort = SubredditSort.BEST;
                        sortMsg = "Random best post from r/";
                        break;
                }
            }
		    DefaultPaginator<Submission> paginator = Main.reddit.subreddit(subreddit)
		    .posts()
	    	.limit(200)
         	.sorting(subSort) // top posts
            .timePeriod(time) // of all time
	    	.build();
	    	Random rand = new Random();
            Listing<Submission> posts = paginator.next();
            int rPost = rand.nextInt(posts.size());
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
            if (event.getMessage().getContentRaw().startsWith(prefix + "randmeme") && redditCommands.equals("1")) {
                event.getChannel().sendTyping().queue();
                String[] memeSubs = {"memes", "dankmemes", "goodanimemes", "memes_of_the_dank"};
                String[] arguments = event.getMessage().getContentRaw().split("\\s+");
                SubredditSort subSort = SubredditSort.TOP;
                String sortMsg = "Random top meme from r/";
                //Subreddit sorting, if possible
            if (arguments.length > 1)
            {
                String sort = arguments[1].toLowerCase();
                switch (sort) {
                    case "top":
                        subSort = SubredditSort.TOP;
                        sortMsg = "Random top meme from r/";
                        break;
                    case "hot":
                        subSort = SubredditSort.HOT;
                        sortMsg = "Random hot meme from r/";
                        break;
                    case "new":
                        subSort = SubredditSort.NEW;
                        sortMsg = "Random new meme from r/";
                        break;
                    case "rising":
                        subSort = SubredditSort.RISING;
                        sortMsg = "Random rising meme from r/";
                        break;
                    case "controversial":
                        subSort = SubredditSort.CONTROVERSIAL;
                        sortMsg = "Random controversial meme from r/";
                        break;
                    case "best":
                        subSort = SubredditSort.BEST;
                        sortMsg = "Random best meme from r/";
                        break;
                }
            }
                DefaultPaginator<Submission> paginator = Main.reddit.subreddits(memeSubs[0], memeSubs[1], memeSubs[2], memeSubs[3], memeSubs[4])
                .posts()
                .limit(200)
                .sorting(subSort) // sorted posts
                .timePeriod(TimePeriod.ALL) // of all time
                .build();
                Random rand = new Random();
                Listing<Submission> posts = paginator.next();
                int rPost = rand.nextInt(posts.size());
                if (event.getChannel().isNSFW() == true && posts.get(rPost).isNsfw() && redditNsfw.equals("1")) {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle(sortMsg + posts.get(rPost).getSubreddit(), posts.get(rPost).getUrl());
                    embed.addField(posts.get(rPost).getTitle(), "By: u/" + posts.get(rPost).getAuthor(), false);
                    embed.addField("Comments: ", posts.get(rPost).getCommentCount() + "", false);
                    embed.addField("Comments Link:", "https://reddit.com" + posts.get(rPost).getPermalink(), false);
                    embed.setImage(posts.get(rPost).getUrl());
                    embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                    event.getChannel().sendMessage(embed.build()).queue();
                } else if ((!event.getChannel().isNSFW() || redditNsfw.equals("0")) && posts.get(rPost).isNsfw()) {
                    event.getChannel().sendMessage("Post is NSFW, channel is not NSFW or nsfw flag is set to disable.").queue();
                } else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle(sortMsg + posts.get(rPost).getSubreddit(), posts.get(rPost).getUrl());
                    embed.addField(posts.get(rPost).getTitle(), "By: u/" + posts.get(rPost).getAuthor(), false);
                    embed.addField("Comments: ", posts.get(rPost).getCommentCount() + "", false);
                    embed.addField("Comments Link:", "https://reddit.com" + posts.get(rPost).getPermalink(), false);
                    embed.setImage(posts.get(rPost).getUrl());
                    embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                    event.getChannel().sendMessage(embed.build()).queue();
                }
            }
            /**
            * Randomly pick some totally not nsfw stuff.
            */
            if (event.getMessage().getContentRaw().startsWith(prefix + "hentai") && redditCommands.equals("1")) {
                event.getChannel().sendTyping().queue();
                String[] nsfwSubs = {"hentai", "HENTAI_GIF", "hentai_paradise", "pokeporn", "waifusgonewild"};
                String[] arguments = event.getMessage().getContentRaw().split("\\s+");
                SubredditSort subSort = SubredditSort.TOP;
                String sortMsg = "Random top hentai post from from r/";
                //Subreddit sorting, if possible
            if (arguments.length > 1)
            {
                String sort = arguments[1].toLowerCase();
                switch (sort) {
                    case "top":
                        subSort = SubredditSort.TOP;
                        sortMsg = "Random top hentai post from r/";
                        break;
                    case "hot":
                        subSort = SubredditSort.HOT;
                        sortMsg = "Random hot meme from r/";
                        break;
                    case "new":
                        subSort = SubredditSort.NEW;
                        sortMsg = "Random new hentai post from r/";
                        break;
                    case "rising":
                        subSort = SubredditSort.RISING;
                        sortMsg = "Random rising hentai post from r/";
                        break;
                    case "controversial":
                        subSort = SubredditSort.CONTROVERSIAL;
                        sortMsg = "Random controversial hentai post from r/";
                        break;
                    case "best":
                        subSort = SubredditSort.BEST;
                        sortMsg = "Random best hentai post from r/";
                        break;
                }
            }
                DefaultPaginator<Submission> paginator = Main.reddit.subreddits(nsfwSubs[0], nsfwSubs[1], nsfwSubs[2], nsfwSubs[3], nsfwSubs[4])
                .posts()
                .limit(200)
                .sorting(subSort) // sorted posts
                .timePeriod(TimePeriod.ALL) // of all time
                .build();
                Random rand = new Random();
                Listing<Submission> posts = paginator.next();
                int rPost = rand.nextInt(posts.size());
                if (event.getChannel().isNSFW() == true && posts.get(rPost).isNsfw() && redditNsfw.equals("1")) {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle(sortMsg + posts.get(rPost).getSubreddit(), posts.get(rPost).getUrl());
                    embed.addField(posts.get(rPost).getTitle(), "By: u/" + posts.get(rPost).getAuthor(), false);
                    embed.addField("Comments: ", posts.get(rPost).getCommentCount() + "", false);
                    embed.addField("Comments Link:", "https://reddit.com" + posts.get(rPost).getPermalink(), false);
                    embed.setImage(posts.get(rPost).getUrl());
                    embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                    event.getChannel().sendMessage(embed.build()).queue();
                } else if ((!event.getChannel().isNSFW() || redditNsfw.equals("0")) && posts.get(rPost).isNsfw()) {
                    event.getChannel().sendMessage("Post is NSFW, channel is not NSFW or nsfw flag is set to disable.").queue();
                } else {
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setTitle(sortMsg + posts.get(rPost).getSubreddit(), posts.get(rPost).getUrl());
                    embed.addField(posts.get(rPost).getTitle(), "By: u/" + posts.get(rPost).getAuthor(), false);
                    embed.addField("Comments: ", posts.get(rPost).getCommentCount() + "", false);
                    embed.addField("Comments Link:", "https://reddit.com" + posts.get(rPost).getPermalink(), false);
                    embed.setImage(posts.get(rPost).getUrl());
                    embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                    event.getChannel().sendMessage(embed.build()).queue();
                }
            }
        	//TOGGLE CMDS
            if (event.getMessage().getContentRaw().toLowerCase().contains(prefix + "togglereddit") && !event.getAuthor().isBot() && (event.getAuthor().equals(event.getJDA().getUserById(Main.OWNER_ID)) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
                Main.output = new FileOutputStream("server_config.cfg");
                boolean wasNull = false;
                boolean wasChanged = false;
                String toggle = Main.config.getProperty("redditCmds" + event.getGuild().getId().toString());
                if (toggle == null) {
                    toggle = "1";
                    Main.config.setProperty("redditCmds" + event.getGuild().getId().toString(), toggle);
                    Main.config.store(Main.output, null);
                    wasNull = true;
                    wasChanged = true;
                    event.getChannel().sendMessage("No toggle was set for Reddit Commands. Set to on by default. Please run again to turn off.").queue();
                    }
                if (!wasNull ) {
                    if (toggle.equals("0") && !wasChanged) {
                        toggle = "1";
                        wasChanged = true;
                        Main.config.setProperty("redditCmds" + event.getGuild().getId().toString(), toggle);
                        Main.config.store(Main.output, null);
                        event.getChannel().sendMessage("Turned on Reddit commands.").queue();
                    }
                    if (toggle.equals("1") && !wasChanged) {
                        toggle = "0";
                        wasChanged = false;
                        Main.config.setProperty("redditCmds" + event.getGuild().getId().toString(), toggle);
                        Main.config.store(Main.output, null);
                        event.getChannel().sendMessage("Turned off Reddit commands.").queue();
                    }  			
                }  
                Main.config.setProperty("redditCmds" + event.getGuild().getId().toString(), toggle);
                }
                if (event.getMessage().getContentRaw().toLowerCase().contains(prefix + "redditnsfw") && !event.getAuthor().isBot() && (event.getAuthor().equals(event.getJDA().getUserById(Main.OWNER_ID)) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
                    Main.output = new FileOutputStream("server_config.cfg");
                    boolean wasNull = false;
                    boolean wasChanged = false;
                    String toggle = Main.config.getProperty("redditNsfw" + event.getGuild().getId().toString());
                    if (toggle == null) {
                        toggle = "1";
                        Main.config.setProperty("redditNsfw" + event.getGuild().getId().toString(), toggle);
                        Main.config.store(Main.output, null);
                        wasNull = true;
                        wasChanged = true;
                        event.getChannel().sendMessage("No toggle was set for Reddit NSFW. Set to on. Only works in NSFW-enabled channels. Please run again to turn off.").queue();
                        }
                    if (!wasNull ) {
                        if (toggle.equals("0") && !wasChanged) {
                            toggle = "1";
                            wasChanged = true;
                            Main.config.setProperty("redditNsfw" + event.getGuild().getId().toString(), toggle);
                            Main.config.store(Main.output, null);
                            event.getChannel().sendMessage("Turned on Reddit NSFW. Only works in NSFW-enabled channels.").queue();
                        }
                        if (toggle.equals("1") && !wasChanged) {
                            toggle = "0";
                            wasChanged = false;
                            Main.config.setProperty("redditNsfw" + event.getGuild().getId().toString(), toggle);
                            Main.config.store(Main.output, null);
                            event.getChannel().sendMessage("Turned off Reddit NSFW.").queue();
                        }  			
                    }  
                    Main.config.setProperty("redditNsfw" + event.getGuild().getId().toString(), toggle);
                    }
        } catch (Exception e) {Main.logger.info("Exception in RedditCommands.java");}
    }
}