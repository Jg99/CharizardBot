package com.charizardbot.four.commands;
import com.charizardbot.four.Main;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class CheckServers extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String prefix = Main.config.getProperty(event.getGuild().getId().toString());
        if (prefix == null)
            prefix = "!";
        String admins = Main.XBAN_ADMINS;
        if (event.getMessage().getContentRaw().startsWith(prefix + "svrcheck") && (event.getAuthor().getId().equals(Main.OWNER_ID) || admins.contains(event.getAuthor().getId()))){
            String svrs = Main.XBAN_SERVERS;
            String[] lines = svrs.split("\n");
            for (int i = 0; i < lines.length; i++) {
                if (event.getJDA().getGuildById(lines[i]) == null) {
                    Main.logger.info("Removed: " + lines[i].toString());
                    event.getChannel().sendMessage("Removed: " + lines[i].toString()).queue();
                    lines[i] = "";
                    continue;
                }
            }
            StringBuilder svList = new StringBuilder();
            for (String s : lines) {
                if (!s.equals("")) {
                    svList.append(s).append("\n");
                }
            }
            svrs = svList.toString();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("xbanservers.txt"));
                writer.write(svrs);
                writer.close();
            }

            catch (IOException e) {
                e.printStackTrace();
            }
            Main.XBAN_SERVERS = svrs;

            event.getChannel().sendMessage("Removed all invalid servers from crossban list").queue();
            Main.logger.info("Removed all invalid servers from crossban list");

        }
    }
}

