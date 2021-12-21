package com.charizardbot.main.commands;
import java.io.FileOutputStream;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import com.charizardbot.main.Main;
/**
 * Tenor Search Commands
 */
public class TenorSearch extends ListenerAdapter {
    private static final String API_KEY = Main.TENOR_TOKEN; //Tenor API Key
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.isFromGuild()) {

	// TOGGLE COMMANDS
		try {
	    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
	    	if (prefix == null)
	    		prefix = "!";
	        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "togglegif") && !event.getAuthor().isBot() && (event.getAuthor().getId().equals(Main.OWNER_ID) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
	        	Main.output = new FileOutputStream("server_config.cfg");
	        	boolean wasNull = false;
	        	boolean wasChanged = false;
	        	String toggle = Main.config.getProperty("tenorCmd" + event.getGuild().getId().toString());
	        	if (toggle == null) {
	        		toggle = "1";
	        		Main.config.setProperty("tenorCmd" + event.getGuild().getId().toString(), toggle);
	        		Main.config.store(Main.output, null);
	        		wasNull = true;
	        		wasChanged = true;
	        		event.getChannel().sendMessage("No toggle was set for Tenor Commands. Set to on by default. Please run again to turn off.").queue();
	        		}
	        	if (!wasNull ) {
	        		if (toggle.equals("0") && !wasChanged) {
	        			toggle = "1";
	        			wasChanged = true;
	            		Main.config.setProperty("tenorCmd" + event.getGuild().getId().toString(), toggle);
	            		Main.config.store(Main.output, null);
	        			event.getChannel().sendMessage("Turned on Tenor search commands.").queue();
	        		}
	        		if (toggle.equals("1") && !wasChanged) {
	        			toggle = "0";
	        			wasChanged = false;
	            		Main.config.setProperty("tenorCmd" + event.getGuild().getId().toString(), toggle);
	            		Main.config.store(Main.output, null);
	        			event.getChannel().sendMessage("Turned off Tenor search commands.").queue();
	        		}
	        	}
	        	Main.config.setProperty("tenorCmd" + event.getGuild().getId().toString(), toggle);
	        }
		// TENOR SEARCH API
		String tenorCmd = "1";
    	if (Main.config.getProperty("tenorCmd" + event.getGuild().getId().toString()) != null) {
    	tenorCmd = Main.config.getProperty("tenorCmd" + event.getGuild().getId().toString());
    	}
    	if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "gif") && tenorCmd.equals("1") && !event.getAuthor().isBot()) {
			event.getChannel().sendTyping().queue();
			String searchTerm = event.getMessage().getContentRaw().substring(5, event.getMessage().getContentRaw().length()).replace(" ", "%20");
    		JSONObject searchResult = getSearchResults(searchTerm, 1);
    	      String url = "";
    	      ArrayList<String> list = new ArrayList<String>();
    	      JSONArray jsonArray = searchResult.getJSONArray("results");
    	      for(int i = 0 ; i < jsonArray.length(); i++) {
    	         list.add(jsonArray.getJSONObject(i).getString("url"));
    	         url = jsonArray.getJSONObject(i).getString("url");
    	      }
    	      event.getChannel().sendMessage("GIF Search powered by Tenor\n" + url).queue();
    	}
    	if (Main.config.getProperty("tenorCmd" + event.getGuild().getId().toString()) != null) {
    	tenorCmd = Main.config.getProperty("tenorCmd" + event.getGuild().getId().toString());
    	}
    	if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "randgif") && tenorCmd.equals("1") && !event.getAuthor().isBot()) {
			event.getChannel().sendTyping().queue();
			String searchTerm = event.getMessage().getContentRaw().substring(9, event.getMessage().getContentRaw().length()).replace(" ", "%20");
    		JSONObject searchResult = getSearchResults(searchTerm, 50);
    		Random rand = new Random();
    	    String url = "";
    	      ArrayList<String> list = new ArrayList<String>();
    	      JSONArray jsonArray = searchResult.getJSONArray("results");
    	      for(int i = 0 ; i < jsonArray.length(); i++) {
    	         list.add(jsonArray.getJSONObject(i).getString("url"));
    	      }
    	      url = list.get(rand.nextInt(list.size() - 1));
    	      event.getChannel().sendMessage("GIF Search powered by Tenor\n" + url).queue();
    	}
		} catch (Exception e){
			event.getChannel().sendMessage("Error in searching. Either the rate limit was reached or no results were found.").queue();
		}
	}
	}
	/*JSON Functions */
    public static JSONObject getSearchResults(String searchTerm, int limit) {
        // make search request - using default locale of EN_US
        final String url = String.format("https://api.tenor.com/v1/search?q=%1$s&key=%2$s&limit=%3$s",
                searchTerm, API_KEY, limit);
        try {
            return get(url);
        } catch (IOException | JSONException ignored) {
        }
        return null;
    }
    private static JSONObject get(String url) throws IOException, JSONException {
        HttpURLConnection connection = null;
        try {
            // Get request
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            // Handle failure
            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK && statusCode != HttpURLConnection.HTTP_CREATED) {
                String error = String.format("HTTP Code: '%1$s' from '%2$s'", statusCode, url);
                System.out.println(error);
                throw new ConnectException(error);
            }
            // Parse response
            return parser(connection);
        } catch (Exception e) {
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return new JSONObject("");
    }
    private static JSONObject parser(HttpURLConnection connection) throws JSONException {
        char[] buffer = new char[1024 * 4];
        int n;
        InputStream stream = null;
        try {
            stream = new BufferedInputStream(connection.getInputStream());
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            StringWriter writer = new StringWriter();
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
            return new JSONObject(writer.toString());
        } catch (Exception IOException) {
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {
                }
            }
        }
        return new JSONObject("");
    }
}