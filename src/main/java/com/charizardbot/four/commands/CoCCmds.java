package com.charizardbot.four.commands;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.charizardbot.four.Main;
import com.charizardbot.clashofjava.API;
import com.charizardbot.clashofjava.Clan;
import com.charizardbot.clashofjava.ClanQuery;
import com.charizardbot.clashofjava.Player;
public class CoCCmds extends ListenerAdapter {
	public void onGuildMessageReceived(MessageReceivedEvent event) {
		if (event.isFromGuild()) {

		String cocCmd = "1";
		String API_TOKEN = Main.COC_TOKEN;
		if (Main.config.getProperty("cocCmd" + event.getGuild().getId().toString()) != null) {
    		cocCmd = Main.config.getProperty("cocCmd" + event.getGuild().getId().toString());
    	}
		try {
    	String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
    		prefix = "!";
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "claninfo") && !event.getAuthor().isBot() && cocCmd.equals("1")) {//&& event.getGuild().getId().toString().equals("468440854886088714")) { 
        	if (event.getMessage().getContentRaw().charAt(10) != '#') {
				event.getChannel().sendTyping().queue();
				API.setToken(API_TOKEN);
        		String clanName = event.getMessage().getContentRaw().substring(10, event.getMessage().getContentRaw().length());
				Clan clan1 = ClanQuery.queryName(clanName).get(0);
				Random rand = new Random();
        		EmbedBuilder embed = new EmbedBuilder();
        		embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
      	 		embed.setTitle("Clash of Clans Clan Info");
      	 		embed.addField("Name:", clan1.getName(), true);
        		embed.addField("Description:", clan1.getClanDescription(), true);
        		embed.addField("Country:", clan1.getClanLocationCountry(), true);
        		embed.addField("Members:", clan1.getClanMembers() + "/50", false);
        		embed.addField("Clan Wars:", "Wins: " + clan1.getClanWarWins() + ", Win Streak: " + clan1.getClanWarWinStreak() + ", War Frequency: " + clan1.getClanWarFrequency(), true);
        		embed.addField("Trohies", "Current trophies: " + clan1.getClanTrophies() + "/" + clan1.getClanVersusTrophies() + ", Minimum required: " + clan1.getClanRequiredTrophies(), false);
        		embed.addField("Tag", clan1.getTag(), false);
        		embed.setThumbnail(clan1.getClanIconLarge());
        		embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
        		event.getChannel().sendMessageEmbeds(embed.build()).queue();
        	} else {
				event.getChannel().sendTyping().queue();
				API.setToken(API_TOKEN);
            	String clanTag = event.getMessage().getContentRaw().substring(10, event.getMessage().getContentRaw().length());
				Clan clan1 = new Clan(clanTag);
            	Random rand = new Random();
    			EmbedBuilder embed = new EmbedBuilder();
            	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            	embed.setTitle("Clash of Clans Clan Info");
            	embed.addField("Name:", clan1.getName(), true);
            	embed.addField("Description:", clan1.getClanDescription(), true);
            	embed.addField("Country:", clan1.getClanLocationCountry(), true);
            	embed.addField("Members:", clan1.getClanMembers() + "/50", false);
            	embed.addField("Clan Wars:", "Wins: " + clan1.getClanWarWins() + ", Win Streak: " + clan1.getClanWarWinStreak() + ", War Frequency: " + clan1.getClanWarFrequency(), true);
            	embed.addField("Trohies", "Current trophies: " + clan1.getClanTrophies() + "/" + clan1.getClanVersusTrophies() + ", Minimum required: " + clan1.getClanRequiredTrophies(), false);
            	embed.addField("Tag", clan1.getTag(), false);
            	embed.setThumbnail(clan1.getClanIconLarge());
            	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
            	event.getChannel().sendMessageEmbeds(embed.build()).queue();
            }
        }
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "searchclans") && !event.getAuthor().isBot() && cocCmd.equals("1")) {//&& event.getGuild().getId().toString().equals("468440854886088714")) { 
			event.getChannel().sendTyping().queue();
			API.setToken(API_TOKEN);
        	String clanName = event.getMessage().getContentRaw().substring(13, event.getMessage().getContentRaw().length());
        	ArrayList<Clan> clan = new ArrayList<Clan>(ClanQuery.queryName(clanName));
        	Random rand = new Random();
			EmbedBuilder embed = new EmbedBuilder();
        	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        	for (int i = 0;i < clan.size(); i++) {
        		embed.addField("Clan Name", clan.get(i).getName(), false);
        		embed.addField("Tag:", clan.get(i).getTag(), false);
        		embed.addField("Members:", clan.get(i).getClanMembers() + "/50", false);
        		embed.addField("War frequency:" , clan.get(i).getClanWarFrequency(), false);
        	}
        	embed.setTitle("Clash of Clans Search");
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
        	event.getChannel().sendMessageEmbeds(embed.build()).queue();
        }
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "clanplayers") && !event.getAuthor().isBot() && cocCmd.equals("1")) {// && event.getGuild().getId().toString().equals("468440854886088714")) {
			event.getChannel().sendTyping().queue();
			API.setToken(API_TOKEN);
			String clanTag = event.getMessage().getContentRaw().substring(13, event.getMessage().getContentRaw().length());
        	Clan clan1 = new Clan(clanTag);
        	Random rand = new Random();
			EmbedBuilder embed = new EmbedBuilder();
        	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        	embed.setTitle("Clash of Clans Players");
        	embed.addField("Name:", clan1.getName(), true);
        	embed.addField("Tag", clan1.getTag(), false);
        	String pList = "";
        	for (int i = 0; i < clan1.getClanMemberList().size(); i++) {
        		pList += clan1.getClanMemberList().get(i).getName() + ", ";
        	}
        	embed.addField("Players", pList, false);
        	embed.setThumbnail(clan1.getClanIconLarge());
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
        	event.getChannel().sendMessageEmbeds(embed.build()).queue();
        }
        if (event.getMessage().getContentRaw().toLowerCase().startsWith(prefix + "playerinfo") && !event.getAuthor().isBot() && cocCmd.equals("1")) {// && event.getGuild().getId().toString().equals("468440854886088714")) {
			event.getChannel().sendTyping().queue();
			API.setToken(API_TOKEN);
			String memberTag = event.getMessage().getContentRaw().substring(12, event.getMessage().getContentRaw().length());
        	Player member = new Player(memberTag);
        	Random rand = new Random();
			EmbedBuilder embed = new EmbedBuilder();
        	embed.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        	embed.setTitle("Clash of Clans Player Info");
        	embed.addField("Name:", member.getName(), true);
        	embed.addField("Tag:", member.getTag(), false);
        	embed.addField("Clan info:", "Clan: " + member.getClanName() + ", " + member.getClanTag() + ".\nDonations: " + member.getDonations() + " / " + member.getDonationsReceived(), false);
        	embed.addField("Season info:", "Attack wins: " + member.getAttackWins() + "\nDefense wins: " + member.getDefenseWins(), false);
        	embed.addField("TownHall:", "" + member.getTownHallLevel(), false);
        	embed.setThumbnail(member.getLeagueIconLarge());
        	embed.setFooter("CharizardBot Team", "https://cdn.discordapp.com/attachments/382377954908569600/463038441547104256/angery_cherizord.png");
        	event.getChannel().sendMessageEmbeds(embed.build()).queue();
        }
		//TOGGLE CMDS
        if (event.getMessage().getContentRaw().toLowerCase().contains(prefix + "togglecoc") && !event.getAuthor().isBot() && (event.getAuthor().equals(event.getJDA().getUserById(Main.OWNER_ID)) || event.getMember().hasPermission(Permission.ADMINISTRATOR))) {
			event.getChannel().sendTyping().queue();
			Main.output = new FileOutputStream("server_config.cfg");
        	boolean wasNull = false;
        	boolean wasChanged = false;
        	String toggle = Main.config.getProperty("cocCmd" + event.getGuild().getId().toString());
        	if (toggle == null) {
        		toggle = "1";
        		Main.config.setProperty("cocCmd" + event.getGuild().getId().toString(), toggle);
        		Main.config.store(Main.output, null);
        		wasNull = true;
        		wasChanged = true;
        		event.getChannel().sendMessage("No toggle was set for Clash of Clans Commands. Set to on by default. Please run again to turn off.").queue();
        		}
        	if (!wasNull ) {
        		if (toggle.equals("0") && !wasChanged) {
        			toggle = "1";
        			wasChanged = true;
            		Main.config.setProperty("cocCmd" + event.getGuild().getId().toString(), toggle);
            		Main.config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned on Clash of Clans commands.").queue();
        		}
        		if (toggle.equals("1") && !wasChanged) {
        			toggle = "0";
        			wasChanged = false;
            		Main.config.setProperty("cocCmd" + event.getGuild().getId().toString(), toggle);
            		Main.config.store(Main.output, null);
        			event.getChannel().sendMessage("Turned off Clash of Clans commands.").queue();
        		}  			
        	}  
        	Main.config.setProperty("cocCmd" + event.getGuild().getId().toString(), toggle);
        }
		} catch (Exception e) {Main.logger.info("Warn: Exception in CoC Commands\n" + e);}
	
	}
}
}