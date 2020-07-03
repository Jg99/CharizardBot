package com.charizardbot.four.commands;
import java.awt.Color;
import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.charizardbot.four.Main;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
/**
 * Random Joke commands
 */
public class RandomJoke extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String miscToggle = "1";
    	if (Main.config.getProperty("miscCmds" + event.getGuild().getId().toString()) != null) {
		miscToggle = Main.config.getProperty("miscCmds" + event.getGuild().getId().toString());
    	}
    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
    	try {
    		if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "randomjoke") && !event.getAuthor().isBot() && miscToggle.equals("1")) {	
            	EmbedBuilder embed = new EmbedBuilder();
            	Random rand = new Random();
                JSONObject joke = getJoke();
                String jokeType = joke.getString("type");
                String jokeText = joke.getString("setup") + "\n" + joke.getString("punchline");
    			if (jokeText != null) {
    			    embed.setAuthor(event.getAuthor().getAsTag());
    			    embed.setTitle("Enjoy the random joke!");
                	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
    			    embed.addField("Joke type: " + jokeType,jokeText, false);
            	    embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
    			    event.getChannel().sendMessage(embed.build()).queue();
			}
		}
    	} catch (Exception e) {Main.logger.info("WARN: Exception in the Random Joke Command. Insufficient permissions or API server is down?\n" + e);}
    }
    public static JSONObject getJoke() {
        // Requests a random joke from the URL below
        final String url = String.format("https://official-joke-api.appspot.com/random_joke");
        try {
            return get(url);
        } catch (Exception e) {
        }
        return null;
    }
    private static JSONObject get(String url) throws Exception {
        HttpURLConnection connection = null;
        try {
            // Get request
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.addRequestProperty("User-Agent","CharizardBot / RandomJoke");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Handle failure
            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK && statusCode != HttpURLConnection.HTTP_CREATED) {
                String error = String.format("HTTP Code: '%1$s' from '%2$s'", statusCode, url);
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
    private static JSONObject parser(HttpURLConnection connection) throws Exception {
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
                } catch (Exception e) {
                }
            }
        }
        return new JSONObject("");
    }
}