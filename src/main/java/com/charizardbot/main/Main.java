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
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import javax.security.auth.login.LoginException;
import com.charizardbot.four.commands.*;

import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
public class Main {
	public static final String VERSION = "4.6.4";
	public static String filterDB = "";
	public static File chatFilter;
	public static File nicknameFile = new File("nick_blacklist.txt");
	public static String filterFile = "chatfilter.txt";
	public static final String OWNER_ID = "184534810369196032";
	public static ChatFilter filter;
	public static String ownerNick = "James, Meme Man 2021#0820";
	public static String table = "";
	public static File VALUE_CSV;
	public static String VALUE_TABLE = "";
	public static String lastUpdated = "";
	public static String PREFIX = "!";
	public static InputStream input = null;
	public static OutputStream output = null;
	public static Properties config = new Properties();
	public static Properties logging_config = new Properties();
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
	public static String XBAN_BANSDB = "";
	public static String VG_WHITELIST = "";
	public static String LOGGING_CFG = "";
	public static String NICK_BL = "";
	public static MessageCache msgCache;
	public static boolean isChatFilterDeleted = false;
	public static boolean isBulkDeleted = false;
	public static int bulkCount = 0;
	public static int curMsgLog = 0;
	public static RedditClient reddit;
	public static String CLEAR_MODS = "";
	public static String CLEAR_AUCTIONS = "";
	public static String CLEAR_EVENTS = "";
	public static String CLEAR_MM = "";
	public static String AUCTION_CHANNELS = "";
	public static String EVENT_CHANNELS = "";
	public static String MM_CHANNELS = "";
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
			 * Copyright 2021 James, Meme Man 2021#0820
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
				logger.info("xbanservers.txt does not exist");
			}
			/**NICKNAME BLACKLIST FILE*/
			if (nicknameFile.exists()) {
				Scanner fileScan = new Scanner(nicknameFile);
				while (fileScan.hasNextLine()) {
					NICK_BL += fileScan.nextLine() + "\n";
				}
				logger.info("Loading blacklisted nicknames");
				fileScan.close();
			} else {
				logger.info("nick_blacklist.txt does not exist");
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
				logger.info("xbanadmins.txt does not exist");
			}
			/**Cross ban list. saving all of the previous bans and reloading */
			File vgwl = new File("vgwhitelist.txt");
			if (vgwl.exists()) {
				Scanner fileScan = new Scanner(vgwl);
				while (fileScan.hasNextLine()) {
					VG_WHITELIST+= fileScan.nextLine() + "\n";
				}
				logger.info("Loading vgwhitelist.txt");
				fileScan.close();
			} else {
				logger.info("vgwhitelist.txt does not exist");
			}
			File pastbans = new File("pastBans.txt");
			if (pastbans.exists()) {
				Scanner fileScan = new Scanner(pastbans);
				while (fileScan.hasNextLine()) {
					XBAN_BANSDB += fileScan.nextLine() + "\n";
				}
				logger.info("Loading banned database for cross ban utility.");
				fileScan.close();
			} else {
				logger.info("pastBans.txt does not exist");
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
			VALUE_CSV = new File("value_guides.csv");
			if (VALUE_CSV.exists()) {
				System.out.println("CSV exists!");
				List<CSVRecord> csvmap = CSVParse.getCSVResults(VALUE_CSV);
				int i = csvmap.size();
				String o = "";
				for (int a = 0; a < i; a++) {
				String[] value = csvmap.get(a).toString().split("values=");
				o += value[1] + "\n";
				}
				o = o.replaceAll("[\\[\\]]","");
				VALUE_TABLE = o;
			} else {
				logger.info("No CSV for Value Guides is provided.");
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

			if (!nicknameFile.exists()) {
				logger.info("Nickname blacklist does not exist, creating nick_blacklist.txt");
				new FileOutputStream("nick_blacklist.txt", false).close();
			}
			File loggingConfig = new File("logConfig.cfg");
			if (!loggingConfig.exists())
			{
				logger.info("Config file does not exist, creating server_config.cfg");
				new FileOutputStream("logConfig.cfg", false).close();
			}
			if (chatFilter.exists()) {
				logger.info("Chat Filter Exists!");
				Scanner fileScan = new Scanner(chatFilter);
				filterDB = "";
				while (fileScan.hasNextLine()) {
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
				//load logging config
				logging_config.load(new FileInputStream("logConfig.cfg"));
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
					} catch (Exception e) {
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
				JDA api = JDABuilder.create(discordtoken, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_MESSAGES)
							.setChunkingFilter(ChunkingFilter.NONE)
							.setDisabledIntents(GatewayIntent.GUILD_MESSAGE_TYPING, GatewayIntent.GUILD_INVITES, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_PRESENCES)
							.setMemberCachePolicy(MemberCachePolicy.OWNER)
							.disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.EMOTE, CacheFlag.CLIENT_STATUS, CacheFlag.ROLE_TAGS, CacheFlag.MEMBER_OVERRIDES, CacheFlag.EMOTE, CacheFlag.ONLINE_STATUS)
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
				api.addEventListener(new enlargeEmote());
				api.addEventListener(new CheckServers());
				api.addEventListener(new ValueGuides());
				// join server listener. Listens for when the bot joins a new server.
				api.addEventListener(new JoinServerStuff());
				/**join listener for that sweet autoban stuff. GTP only (my server).
				 * May add user Join options in the future such as a welcome message.
				 */
				//api.addEventListener(new Clears());
				/**join listener for clear commands, only for use in GTP atm*/
				api.addEventListener(new UserJoinHandler());
				api.addEventListener(new MessageLogger());
			} catch (LoginException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {logger.info("Exception in Main");}
	}
}