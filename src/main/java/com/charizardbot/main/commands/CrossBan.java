package com.charizardbot.main.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.charizardbot.main.Main;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.Guild.Ban;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CrossBan extends ListenerAdapter {
    public static String userID = "";
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromGuild()) {

        /**
         * This is a command that lets someone ban an account across multiple servers,
         *say if you want to share bans between servers easily.
         * For example: 2 game trading servers want to share bans of scammers.
         One admin can ban a scammer using this command on both servers at once.
         */
        try {
            String prefix = Main.config.getProperty(event.getGuild().getId().toString());
            String admins = Main.XBAN_ADMINS;
            if (prefix == null)
                prefix = "!";
            /**!addcsv - Add your server to the cross ban list
             *!remcsv - Remove your server to the cross ban list
             *!cban - Cross ban a user.
             *!cunban - Unban across servers.
             * !cpastban - ban all previously banned
             **/
            if (event.getMessage().getContentRaw().startsWith(prefix + "addcsv") && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
                String svID = event.getGuild().getId().toString();
                String svrs = Main.XBAN_SERVERS;
                if (svrs.contains(svID)) {
                    event.getChannel().sendMessage("Server is already in the cross-ban system.").queue();
                } else {
                    svrs += svID + "\n";
                    BufferedWriter writer = new BufferedWriter(new FileWriter("xbanservers.txt"));
                    writer.write(svrs);
                    writer.close();
                    Main.XBAN_SERVERS = svrs;
                    event.getChannel().sendMessage("Added your server to the cross-ban system.").queue();
                }
            }
            if (event.getMessage().getContentRaw().startsWith(prefix + "remcsv") && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
                String svrs = Main.XBAN_SERVERS;
                String[] lines = svrs.split("\n");
                for (int i = 0; i < lines.length; i++) {
                    if (lines[i].contains(event.getGuild().getId().toString())) {
                        lines[i] = "";
                    }
                }
                StringBuilder svList = new StringBuilder();
                for (String s : lines) {
                    if (!s.equals("")) {
                        svList.append(s).append("\n");
                    }
                }
                svrs = svList.toString();
                BufferedWriter writer = new BufferedWriter(new FileWriter("xbanservers.txt"));
                writer.write(svrs);
                writer.close();
                Main.XBAN_SERVERS = svrs;
                event.getChannel().sendMessage("Removed your server from the cross-ban system if it was in.").queue();
            }
            if (event.getMessage().getContentRaw().startsWith(prefix + "cban") && (admins.contains(event.getAuthor().getId()) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
                    if (event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
                        event.getChannel().sendTyping().queue();
                     userID = event.getMessage().getContentRaw().substring(6);
                    if (!event.getMessage().getMentions().getUsers().isEmpty()) {
                        
                        userID = event.getMessage().getMentions().getUsers().get(0).getId();
                    }
                    String banned = Main.XBAN_BANSDB;
                    if (banned.contains(userID)) {
                        event.getChannel().sendMessage("<@" + userID + ">  is already in ban list").queue();
                        return;
                    } else {
                        banned += userID + "\n";
                        BufferedWriter writer = new BufferedWriter(new FileWriter("pastBans.txt"));
                        writer.write(banned);
                        Main.XBAN_BANSDB = banned;
                        writer.close();
                        Scanner scan = new Scanner(Main.XBAN_SERVERS);
                        event.getChannel().sendMessage("Banned <@" + userID + "> from servers in the cross-ban system and added to ban list.").queueAfter(5, TimeUnit.SECONDS);
                        while (scan.hasNextLine()) {
                            String svID = scan.nextLine();
                            if (!svID.equals("")) {
                                if(event.getJDA().getGuildById(svID) != null) {
                                    try {
                                        UserSnowflake toBan = UserSnowflake.fromId(userID);
                                        event.getJDA().getGuildById(svID).ban(toBan, 0, "X-ban by CharizardBot.").queue();
                                        Main.logger.info(userID + " banned in " + event.getJDA().getGuildById(svID).getName() + ".");
                                    } catch (Exception e) {
                                        String invalidBan = svID;
                                        System.out.println("Invalid ban. Server: " + event.getJDA().getGuildById(invalidBan).getName());
                                        continue;
                                    }
                                }
                                else
                                    {
                                        Main.logger.info("Invalid Server: " +userID + " could not be banned in " + svID + ".");
                                        
                                        continue;
                                    }
                            }
                        }
                        Main.logger.info(userID + " banned in the x-ban system done.");
                        scan.close();
                }
                }
            }
            if (event.getMessage().getContentRaw().startsWith(prefix + "cunban") && (admins.contains(event.getAuthor().getId()) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
                admins = Main.XBAN_ADMINS;
                if (event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
                    event.getChannel().sendTyping().queue();
                     userID = event.getMessage().getContentRaw().substring(8);
                    Scanner scan = new Scanner(Main.XBAN_SERVERS);
                    while (scan.hasNextLine()) {
                        String svID = scan.nextLine();
                        if (!svID.equals("")) {
                            if(event.getJDA().getGuildById(svID)!= null){
                            try {
                                UserSnowflake toUnBan = UserSnowflake.fromId(userID);
                                 Ban ban = event.getJDA().getGuildById(svID).retrieveBan(toUnBan).complete();
                                 String reason = ban.getReason();
                                if (reason.equals("X-ban by CharizardBot.")) {
                                event.getJDA().getGuildById(svID).unban(toUnBan).queue();
                                Main.logger.info("User " + userID + " was banned by CharizardBot in " + event.getJDA().getGuildById(svID).getName() + ".\n Unbanning.");
                                } else {
                                Main.logger.info("User " + userID + " was banned by another person in " + event.getJDA().getGuildById(svID).getName() + ".\n Skipping unban.");
                                }
                            } catch (Exception e) {
                                continue;
                            }
                            }
                            else
                            {
                                Main.logger.info("Invalid Server: " + userID + " could not be unbanned in " + svID + ".");
                                continue;
                            }
                        }
                    }
                    //Remove from file
                    String banned = Main.XBAN_BANSDB;
                    String[] lines = banned.split("\n");
                    for (int i = 0; i < lines.length; i++) {
                        if (lines[i].contains(userID)) {
                            lines[i] = "";
                        }
                    }
                    StringBuilder banList = new StringBuilder();
                    for (String s : lines) {
                        if (!s.equals("")) {
                            banList.append(s).append("\n");
                        }
                    }
                    banned = banList.toString();
                    BufferedWriter writer = new BufferedWriter(new FileWriter("pastBans.txt"));
                    writer.write(banned);
                    writer.close();
                    Main.XBAN_BANSDB = banned;
                    Main.logger.info(userID + " unbanned in the x-ban system.");
                    scan.close();
                    event.getChannel().sendMessage("Unbanned <@" + userID + "> from servers in the cross-ban system and removed from banned list.").queue();
                }
            }
            //adds/removes admins to cross ban system so anyone can't just ban.
            if (event.getMessage().getContentRaw().startsWith(prefix + "addcadmin") && Main.XBAN_ADMINS.contains(event.getAuthor().getId())) {
                String msg = event.getMessage().getContentRaw();
                admins = Main.XBAN_ADMINS;
                String adminID = msg.substring(11);
                if (!event.getMessage().getMentions().getUsers().isEmpty()) {
                    adminID = event.getMessage().getMentions().getUsers().get(0).getId();
                }
                if (!admins.contains(adminID)) {
                    admins += adminID + "\n";
                    BufferedWriter writer = new BufferedWriter(new FileWriter("xbanadmins.txt"));
                    writer.write(admins);
                    writer.close();
                    Main.XBAN_ADMINS = admins;
                    event.getChannel().sendMessage("Added admin <@" + adminID + "> to the cross-ban system.").queue();
                } else {
                    event.getChannel().sendMessage("Admin <@" + adminID + "> was already in the cross-ban system.").queue();
                }
            }
            if (event.getMessage().getContentRaw().startsWith(prefix + "remcadmin") && Main.XBAN_ADMINS.contains(event.getAuthor().getId())) {
                String msg = event.getMessage().getContentRaw();
                String adminID = msg.substring(11);
                if (!event.getMessage().getMentions().getUsers().isEmpty()) {
                    adminID = event.getMessage().getMentions().getUsers().get(0).getId();
                }
                admins = Main.XBAN_ADMINS;
                // String[] lines = admins.split(System.getProperty("line.separator"));
                String[] lines = admins.split("\n");
                for (int i = 0; i < lines.length; i++) {
                    if (lines[i].contains(adminID)) {
                        lines[i] = "";
                    }
                }
                StringBuilder adList = new StringBuilder();
                for (String s : lines) {
                    if (!s.equals("")) {
                        adList.append(s).append("\n");
                    }
                }
                admins = adList.toString();
                BufferedWriter writer = new BufferedWriter(new FileWriter("xbanadmins.txt"));
                writer.write(admins);
                writer.close();
                Main.XBAN_ADMINS = admins;
                event.getChannel().sendMessage("Removed admin <@" + adminID + "> from the cross-ban system. if they were in it.").queue();
            }
            if (event.getMessage().getContentRaw().equals(prefix + "listcadmins") && event.getAuthor().getId().equals(Main.OWNER_ID)) {
                event.getChannel().sendMessage(Main.XBAN_ADMINS).queue();
            }
            if (event.getMessage().getContentRaw().startsWith(prefix + "cpastban") && (event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
                String svID = event.getGuild().getId().toString();
                event.getChannel().sendTyping().queue();
                String banned = Main.XBAN_BANSDB;
                String[] lines = banned.split("\n");
                for (int i = 0; i < lines.length; i++) {
                     userID = lines[i];
                    System.out.println(userID);
                    try {
                        UserSnowflake toBan = UserSnowflake.fromId(userID);
                        event.getJDA().getGuildById(svID).ban(toBan, 0, "X-ban by CharizardBot.").queue();
                        Main.logger.info(userID + " banned in " + event.getJDA().getGuildById(svID).getName() + ".");
                    } catch (Exception e) {
                        Main.logger.info("Invalid ban. Server: " + event.getJDA().getGuildById(svID).getName());
                    }
                    Main.logger.info(userID + " ban in the x-ban system done.");
                }
                event.getChannel().sendMessage("Banned users in the database retroactively in this server.").queue();
            }


        } catch (Exception e) {
            Main.logger.info("Exception in Cross-ban: Invalid ban?");
        }


    }
}
}