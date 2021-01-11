package com.charizardbot.four;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
/**MiniDex aka "Midoriya Dex" - Simple PokeAPI Query for basic information, mainly used for CharizardBot.
*Copyleft 2021 James, Meme Man 2021#0820
*/
public class MiniDex {
private static String pokeurl = "https://charizardbot.com:8443/api/v2/pokemon/";
private static String pkName = "";
private static String sprURL = "";
    /**
     * Returns Pokémon name
     * @return
     */
    public static String getPokemonName()
    {
        return pkName;
    }
    /**
     * Returns the sprite URL. Used for thumbnail in the embeds.
     * @return
     */
    public static String getSprite() 
    {
        return sprURL;
    }
    /**
     * Get formatted Pokémon info, already setup for Embeds.
     * @param pokemon_name
     * @return
     */
    public static String getPokemonInfo(String pokemon_name)
    {
        JSONObject pokeResult = getPokemon(pokemon_name);
        String pkTyp = "N/A";
        String pkTyp2 = "N/A";
        JSONObject typeArr = pokeResult.getJSONArray("types").getJSONObject(0).getJSONObject("type");
        String slot = typeArr.get("name").toString();
        pkTyp = slot.toString();
        String upper = pkTyp.substring(0, 1).toUpperCase();
        pkTyp = upper + pkTyp.substring(1, pkTyp.length());
     // 2nd type if applicable, otherwise display "N/A"
        try {
        typeArr = pokeResult.getJSONArray("types").getJSONObject(1).getJSONObject("type");
        slot = typeArr.get("name").toString();
        pkTyp2 = slot.toString();
        upper = pkTyp2.substring(0, 1).toUpperCase();
        pkTyp2 = upper + pkTyp2.substring(1, pkTyp2.length());
        } catch (Exception e) {} //Do nothing, because 2nd type is null.
        /**Weight, Height, Base XP, Pokémon Number 
         * 
        */
        String weight = pokeResult.get("weight").toString();
        double pkWeight = (double) Integer.parseInt(weight) / 10;
        String height = pokeResult.get("height").toString();
        double pkHeight = (double) Integer.parseInt(height) / 10;
        String baseXP = pokeResult.get("base_experience").toString();
        String id = pokeResult.get("id").toString();
        /**
         * Put it in a nice string to output
         */
        String stats = "Main Type: " + pkTyp + "\nSecondary Type: " + pkTyp2 + "\nBase XP: " + baseXP + "\nHeight: " + pkHeight + " m\nWeight: " + pkWeight + 
                        "kg\nNumber: " + id;
        pkName = pokeResult.get("name").toString();
        upper = pkName.substring(0, 1).toUpperCase();
        pkName = upper + pkName.substring(1, pkName.length());
        JSONObject sprites = pokeResult.getJSONObject("sprites");
        sprURL = sprites.get("front_default").toString();
        return stats;
    }
    public static JSONObject getPokemon(String pokemon) {
        // make search request - using default locale of EN_US
        final String url = String.format(pokeurl + pokemon + "/");
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
            connection.addRequestProperty("User-Agent","CharizardBot / PokeAPI");
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