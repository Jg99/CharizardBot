package com.charizardbot.four.commands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.charizardbot.four.Main;
import com.charizardbot.four.Pet;
import com.charizardbot.four.dePet;
import java.awt.Color;
import java.util.Random;
public class PetStats extends ListenerAdapter {
	final String DELIMITERS_PETCALC = "[" + Main.PREFIX + "petstats\\W]+"; //delimiters for petstats - digits only
public void onGuildMessageReceived (GuildMessageReceivedEvent event) {
    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	String wizCmd = "1";
    	if (Main.config.getProperty("wizCmds" + event.getGuild().getId().toString()) != null) {
    	wizCmd = Main.config.getProperty("wizCmds" + event.getGuild().getId().toString());
    	}
    	if (prefix == null)
    		prefix = "!";
		try {
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "petstats") && !event.getAuthor().isBot() && wizCmd.equals("1")) { 
        	if (!event.getGuild().getId().toString().equals("370132368163340289")) { //block german server from english cmd 	because it'll use a translated cmd
    		String[] contentSplit = event.getMessage().getContentRaw().split("\\W+");
    		String[] stats = new String[5];
    		if (contentSplit.length != 7)
    		{
            	event.getChannel().sendMessage("Error in calculating stats.\nThe correct format is " + prefix + "petstats strength " +
            			"intellect agility will power");
    		} else {
    		int n = 0;
    		for (int t = 0; t < contentSplit.length; t++)
    		{
    			if (t > 1 && t <= 7)
    			{
    				stats[n] = contentSplit[t];
    				n++;
    			}
    		}
            double strength = Integer.parseInt(stats[0]);
            double intellect = Integer.parseInt(stats[1]);
            double agility = Integer.parseInt(stats[2]);
            double will = Integer.parseInt(stats[3]);
            double power = Integer.parseInt(stats[4]);
            Pet mainPet = new Pet(strength, intellect, agility, will, power);
            //Embed - PetStats, implemented in 2.1
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor(event.getAuthor().getAsTag());
            embed.setTitle("Wizard101 Pet stats calculator");
            embed.setImage("https://i.imgur.com/phKD1u6.png");
            embed.setDescription("If the value is over x.5 on most stats, it rounds up.\nFor example, 10.5 rounds to 11 in most cases.");
            embed.addField("Input values:", strength + ", " + intellect + ", " + agility + ", " + 
            will + ", " + power, false);
            embed.addField("Main pet stats", mainPet.calculate(), true);
            embed.addField("More pet stats", mainPet.continuedStats(), true);
            Random rand = new Random();
            embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
         	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
            event.getChannel().sendMessage(embed.build()).queue();
            }
        	} else { // german translated petstats
    		String[] contentSplit = event.getMessage().getContentRaw().split("\\W+");
    		String[] stats = new String[5];
    		if (contentSplit.length != 7)
    		{
            	event.getChannel().sendMessage("Fehler bei der Berechnung der Statistiken.\nDas richtige Format ist " + prefix + "petstats Stärke " +
            			"Intelligenz Geschick Wille Power");
    		} else {
    		int n = 0;
    		for (int t = 0; t < contentSplit.length; t++)
    		{
    			if (t > 1 && t <= 7)
    			{
    				stats[n] = contentSplit[t];
    				n++;
    			}
    		}
            double strength = Integer.parseInt(stats[0]);
            double intellect = Integer.parseInt(stats[1]);
            double agility = Integer.parseInt(stats[2]);
            double will = Integer.parseInt(stats[3]);
            double power = Integer.parseInt(stats[4]); 
            dePet mainPet = new dePet(strength, intellect, agility, will, power);
            //Embed - PetStats, implemented in 2.1
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor(event.getAuthor().getAsTag());
            embed.setTitle("Wizard101 Pet Stats Berechnung");
            embed.setImage("https://i.imgur.com/phKD1u6.png");
            embed.setDescription("Wenn der Wert über x,5 ist, wird er meistens aufgerundet.\nZum Beispiel: 10,5 wird als 11 angezeigt");
            embed.addField("Eingabewerte: ", strength + ", " + intellect + ", " + agility + ", " + 
            will + ", " + power, false);
            embed.addField("Die wichtigsten Haustiertalente", mainPet.calculate(), true);
            embed.addField("weitere Haustiertalente", mainPet.continuedStats(), true);
            Random rand = new Random();
            embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
         	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
            event.getChannel().sendMessage(embed.build()).queue();
            }
        	}
        }
		} catch (Exception e) {Main.logger.info("WARN: Exception in Petstats command: Insufficient permissions?\n" + e);}
	}
}