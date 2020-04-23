package com.charizardbot.four;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import javax.security.auth.login.LoginException;
import com.charizardbot.four.commands.AnimeList;
import com.charizardbot.four.commands.AutobanCommands;
import com.charizardbot.four.commands.BulkDelete;
import com.charizardbot.four.commands.ChatFilterToggle;
import com.charizardbot.four.commands.CoCCmds;
import com.charizardbot.four.commands.CommandsList;
import com.charizardbot.four.commands.CrossBan;
import com.charizardbot.four.commands.ImgurSearch;
import com.charizardbot.four.commands.ImgurToggle;
import com.charizardbot.four.commands.JoinDate;
import com.charizardbot.four.commands.MainCommands;
import com.charizardbot.four.commands.MiscCommands;
import com.charizardbot.four.commands.PetStats;
import com.charizardbot.four.commands.PingCommand;
import com.charizardbot.four.commands.PokedexCommand;
import com.charizardbot.four.commands.PokemonQuoteCommand;
import com.charizardbot.four.commands.RandomJoke;
import com.charizardbot.four.commands.RedditCommands;
import com.charizardbot.four.commands.RngCommand;
import com.charizardbot.four.commands.RpsCommand;
import com.charizardbot.four.commands.ServersCommand;
import com.charizardbot.four.commands.SuggestionCommand;
import com.charizardbot.four.commands.TenorSearch;
import com.charizardbot.four.commands.WizSchedule;
import com.charizardbot.four.commands.listSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
public class Main {
	public static final String VERSION = "4.4.2";
	public static String filterDB = "";
	public static File chatFilter;
    public static String filterFile = "chatfilter.txt";
    public static final String OWNER_ID = "184534810369196032";
	public static ChatFilter filter;
	public static String ownerNick = "James, Meme Man 2020#0820";
	public static String table = "";
	public static String lastUpdated = "";
	public static String PREFIX = "!";
	public static InputStream input = null;
	public static OutputStream output = null;
	public static Properties config = new Properties();
	public static ImgurAPI imgur = new ImgurAPI();
	public static String messageID_deletehandler = "";
	private static String discordtoken = ""; //Tokens are blank so we can read from tokenfile, or specify in args.
	public static Logger logger;
	public static String COC_TOKEN = "";
	public static String IMGUR_ID = "";
	public static String IMGUR_SECRET = "";
	public static String TENOR_TOKEN = "";
	public static String REDDIT_ID = "";
	public static String REDDIT_SECRET = "";
	public static String XBAN_SERVERS = "";
	public static String XBAN_ADMINS = "";
	public static MessageCache msgCache;
	public static boolean isChatFilterDeleted = false;
	public static boolean isBulkDeleted = false;
	public static int bulkCount = 0;
	public static int curMsgLog = 0;
	public static RedditClient reddit;
    public static void main(String[] args) {
        try {
			File logFileConfig = new File("log4j2.xml");
			//creates the logger file for new bot setups automatically.
            if (!logFileConfig.exists()) {
                System.out.println("log4j2.xml does not exist!");
				new FileOutputStream(logFileConfig, false).close();
				BufferedWriter bw = null;
				try {
				   bw = new BufferedWriter(new FileWriter(logFileConfig, true));
				   bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n\r\n<Configuration name=\"CharizardBot Logging\" status=\"INFO\" strict=\"true\">\r\n        <Appenders>\r\n        <Console name=\"LogToConsole\" target=\"SYSTEM_OUT\">\r\n            <PatternLayout pattern=\"%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n\"/>\r\n        </Console>\r\n        <RollingFile name=\"LogToRollingFile\" fileName=\"log/charbotlogs.log\"\r\n                    filePattern=\"log/$${date:yyyy-MM}/charbotlogs-%d{MM-dd-yyyy}-%i.log.gz\">\r\n        <PatternLayout>\r\n            <Pattern>%d %p %c{1.} [%t] %m%n</Pattern>\r\n        </PatternLayout>\r\n        <Policies>\r\n            <TimeBasedTriggeringPolicy />\r\n            <SizeBasedTriggeringPolicy size=\"10 MB\"/>\r\n        </Policies>\r\n    </RollingFile>\r\n    </Appenders>\r\n    <Loggers>\r\n        <Root level=\"INFO\">\r\n           <AppenderRef ref=\"LogToRollingFile\"/>\r\n            <AppenderRef ref=\"LogToConsole\"/>\r\n        </Root>\r\n    </Loggers>\r\n</Configuration>");
				   bw.flush();
				} catch (IOException ioe) {
			   ioe.printStackTrace();
				} finally {                       // always close the file
			   if (bw != null) try {
				  bw.close();
			   } catch (IOException ioe2) {
				   System.out.println("Error, could not write to file log4j2.xml");
			   }
				}
            }
        System.setProperty("log4j2.configurationFile", logFileConfig.toPath().toString());
        logger = LogManager.getLogger(Main.class);
        /**
		 * CharizardBot version 4 
		 * Copyright 2020 James, Meme Man 2020#0820 aka charmander / bakugo
		 * This is a super dank bot that includes Wizard101 specific stuff, Pokemon, Clash of Clans, GIF searching, and more!
		 * license: GNU GPL version 2
		 * Credit: Dewey (website design), Ultra Blue (hosting, email)
		 * Jikan4Java project - MyAnimeList Jikan (https://github.com/Doomsdayrs/Jikan4java)
		 * Snubiss - ClashOfJava project (https://github.com/Snubiss/Snubs-Clash-of-Java)
		 * Various other open source projects used in this bot
		 */
		 //Uncomment this out if you want to use a token based off args. NOTE: TOKEN ARGS GO FIRST!
       // if (args.length > 0) {
		//	discordtoken = args[0]; // set token for future use.
		//	logger.info("Using token from launch arguments.");
     //   } else {
		 	logger.info("CharizardBot, version " + VERSION);
			File tokenFile = new File("token.txt");
			if (tokenFile.exists()) {
			Scanner fileScan = new Scanner(tokenFile);
			while (fileScan.hasNextLine()) {
				discordtoken = fileScan.nextLine();
				logger.info("Using Discord token from token.txt.");
			}
			fileScan.close();
			}
			/**Cross ban server ID file. One server ID per line. */
			File xbanserver = new File("xbanservers.txt");
			if (xbanserver.exists()) {
			Scanner fileScan = new Scanner(xbanserver);
			while (fileScan.hasNextLine()) {
				XBAN_SERVERS += fileScan.nextLine() + "\n";
			}	
			logger.info("Loading Server IDs for cross ban utility.");
			fileScan.close();
			} else {
				logger.info("Please provide a valid Clash of Clans token and place it in coc_token.txt.");
			}
			/**Cross ban admin list. People who can ban/unban members from servers in the system. */
			File xbanadmins = new File("xbanadmins.txt");
			if (xbanadmins.exists()) {
			Scanner fileScan = new Scanner(xbanadmins);
			while (fileScan.hasNextLine()) {
				XBAN_ADMINS += fileScan.nextLine() + "\n";
			}
			logger.info("Loading admins for cross ban utility.");
			fileScan.close();
			} else {
				logger.info("Please provide a valid Clash of Clans token and place it in coc_token.txt.");
			}
			/**Clash of Clans token. You can get this from https://developer.clashofclans.com/
			Note: Tokens are IP address limited.*/
			File cocToken = new File("coc_token.txt");
			if (cocToken.exists()) {
			Scanner fileScan = new Scanner(cocToken);
			while (fileScan.hasNextLine()) {
				COC_TOKEN = fileScan.nextLine();
				logger.info("Using Clash of Clans token from coc_token.txt.");
			}
			fileScan.close();
			} else {
				logger.info("Please provide a valid Clash of Clans token and place it in coc_token.txt.");
			}
			/**
			 * Imgur token. https://api.imgur.com
			 * Free rate limit: 500 per hour
			 * Client ID goes on first line, secret on 2nd line
			 */
			File imgurToken = new File("imgur_token.txt");
			if (imgurToken.exists()) {
			Scanner fileScan = new Scanner(imgurToken);
				IMGUR_ID = fileScan.nextLine();
				IMGUR_SECRET = fileScan.nextLine();
				logger.info("Using Imgur token from imgur_token.txt.");
				imgur.authenticate();
			fileScan.close();
			} else {
				imgur.isError = true; //force an error for imgur since no token provided
				logger.info("Please provide a valid imgur client id and secret (client ID on first line, secret on second) and place it in imgur_token.txt.");
			}
			/**
			 * Reddit API token. https://reddit.com
			 * rate limit: 60 per minute
			 * Client ID goes on first line, secret on 2nd line
			 */
			File redditToken = new File("reddit_token.txt");
			if (redditToken.exists()) {
				Scanner fileScan = new Scanner(redditToken);
				REDDIT_ID = fileScan.nextLine();
				REDDIT_SECRET = fileScan.nextLine();
				logger.info("Using Reddit token from reddit_token.txt.");
				fileScan.close();
				/**
				 * Reddit client setup
				 */
				UserAgent userAgent = new UserAgent("bot", "com.charizardbot.four", VERSION, "jamesgryffindor99");
				NetworkAdapter networkAdapter = new OkHttpNetworkAdapter(userAgent);
				Credentials credentials = Credentials.userless(REDDIT_ID, REDDIT_SECRET, UUID.randomUUID());
				reddit = OAuthHelper.automatic(networkAdapter, credentials);
			} else {
				logger.info("Please provide a valid Reddit client id and secret (client ID on first line, secret on second) and place it in reddit_token.txt.");
			}
			/**
			 * Tenor GIF token
			 * Very high ratelimit, very few limits to API. 
			 * https://tenor.com/developer/
			 */
			File tenorToken = new File("tenor_token.txt");
			if (tenorToken.exists()) {
				Scanner fileScan = new Scanner(tenorToken);
					TENOR_TOKEN = fileScan.nextLine();
					logger.info("Using Tenor token from tenor_token.txt.");
				fileScan.close();
				} else {
					logger.info("Please provide a valid Tenor token and place it in tenor_token.txt.");
				}
		if (discordtoken.equals("")) {
			System.out.println("Please specify a token by placing it in \"token.txt\" in the main directory.");
			System.exit(0);
		}
        KITableCrawler crawler = new KITableCrawler(); //scrapes the Wizard101 PvP tournament website
       	//Chat Filter & Config File
		chatFilter = new File(filterFile);
		File configFile = new File("server_config.cfg");
		// Check files & load contents
		if (!configFile.exists())
		{
			logger.info("Config file does not exist, creating server_config.cfg");
			new FileOutputStream("server_config.cfg", false).close();
		}
		if (chatFilter.exists())
		{
			logger.info("Chat Filter Exists!");
			Scanner fileScan = new Scanner(chatFilter);
			filterDB = "";
			while (fileScan.hasNextLine())
			{
				filterDB += "\n" + fileScan.nextLine();
			}
			fileScan.close();
		} else {
			logger.info("Chat Filter does not exist! Creating chatfilter.txt");
			new FileOutputStream(filterFile, false).close();
        }
        		//PROPERTIES - Load Server configuration 
		try {
			input = new FileInputStream("server_config.cfg");
			// load a properties file
			config.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
        }
        //Crawl KI Wizard101 PvP every 30 minutes.
   	 	Timer crawltimer = new Timer();
   	 	crawltimer.schedule(new TimerTask() {
        public void run() {
            try {
				logger.info("Attempting to crawl Wizard101 Tournament Table");
				crawler.crawlWebsite();
				if (!crawler.returnContents().isEmpty()) {
					logger.info("Successfully crawled Wizard101 Tournament Table");
					table = crawler.returnContents();
					Date date = new Date(System.currentTimeMillis());
					DateFormat formatter = new SimpleDateFormat("HH:mm");
					formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
					lastUpdated = formatter.format(date) + " UTC";
					}
				} catch (IOException e) {
					// just keep trying again every 30 seconds. If it's down, it'll eventually succeed when it's back up.
					run();
				}
            }
		}, 5000, 1800000); //We crawl every 30 minutes because we don't need to update it very frequently.
		//JDA API setup
        try {
			String activity = "";
			/**
			 * Check game if a game activity is set in Config, and if so, load. Otherwise, use the default.
			 * I think "Battling Team Rocket" is a good default.
			 */
            if (config.getProperty("gamestatus") == null || config.getProperty("gamestatus").equals("")) {
                activity = "Battling Team Rocket";
            } else {
            activity = config.getProperty("gamestatus");
            }
			JDA api = JDABuilder.createDefault(discordtoken)
			.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_BANS)
			.setChunkingFilter(ChunkingFilter.NONE)
			.setActivity(Activity.playing(activity))
			.build();
			/**
			 * Message Cache for CharizardBot. Used for logging deleted messages unless
			 * the messages are older than 4 days, or the bot has been restarted since the message was posted.
			 */
			msgCache = new MessageCache(api); 
            //listeners for commands, chat filter, join, etc
            api.addEventListener(new ChatFilterEditHandler());
            api.addEventListener(new ReconnectListener());
            api.addEventListener(new CommandsList());
            api.addEventListener(new PingCommand());
            api.addEventListener(new ChatFilterToggle());
            api.addEventListener(new listSettings());
            api.addEventListener(new PetStats());
            api.addEventListener(new ServersCommand());
            api.addEventListener(new RngCommand());
            api.addEventListener(new RpsCommand());
            api.addEventListener(new JoinDate());
            api.addEventListener(new AutobanCommands());
            api.addEventListener(new PokedexCommand());
            api.addEventListener(new PokemonQuoteCommand());
            api.addEventListener(new ImgurSearch());
            api.addEventListener(new ImgurToggle());
            api.addEventListener(new TenorSearch());
            api.addEventListener(new SuggestionCommand());  
            api.addEventListener(new CoCCmds());
            api.addEventListener(new WizSchedule());
            api.addEventListener(new ChatFilterHandler());
            api.addEventListener(new MainCommands());
			api.addEventListener(new MiscCommands());
			api.addEventListener(new RandomJoke());
			api.addEventListener(new AnimeList());
			api.addEventListener(new BulkDelete());
			api.addEventListener(new RedditCommands());
			api.addEventListener(new CrossBan());
            // join server listener. Listens for when the bot joins a new server.
            api.addEventListener(new JoinServerStuff());
			/**join listener for that sweet autoban stuff. GTP only (my server). 
			* May add user Join options in the future such as a welcome message.
			*/
			api.addEventListener(new UserJoinHandler());
			api.addEventListener(new MessageLogger());
        } catch (LoginException e) {
            e.printStackTrace();
        }
    } catch (Exception e) {logger.info("Exception in Main");}
    }
}