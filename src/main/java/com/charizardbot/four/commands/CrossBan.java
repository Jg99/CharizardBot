package com.charizardbot.four.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

import com.charizardbot.four.Main;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CrossBan extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
         /**
          * This is a command that lets someone ban an account across multiple servers, 
          *say if you want to share bans between servers easily.
          * For example: 2 game trading servers want to share bans of scammers.
         One admin can ban a scammer using this command on both servers at once.
          */
          try {
            String prefix = Main.config.getProperty(event.getGuild().getId());
            if (prefix == null)
                prefix = "!";
                /**!addcsv - Add your server to the cross ban list
                *!remcsv - Remove your server to the cross ban list
                *!cban - Cross ban a user.
                *!cunban - Unban across servers. */
            if (event.getMessage().getContentRaw().startsWith(prefix + "addcsv") && ( event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
                String svID = event.getGuild().getId();
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
            if (event.getMessage().getContentRaw().startsWith(prefix + "remcsv") && ( event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
                String svrs = Main.XBAN_SERVERS;
                String[] lines = svrs.split("\n");
                for(int i=0;i<lines.length;i++){
                    if(lines[i].contains(event.getGuild().getId())){
                        lines[i]="";
                    }
                }
                StringBuilder svList = new StringBuilder("");
                for(String s:lines){
                    if(!s.equals("")){
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
            if (event.getMessage().getContentRaw().startsWith(prefix + "cban") && ( event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
                String admins = Main.XBAN_ADMINS;
                if (event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS) && admins.contains(event.getAuthor().getId())) {
                    String userID = event.getMessage().getContentRaw().substring(6, event.getMessage().getContentRaw().length());
                    if (!event.getMessage().getMentionedUsers().isEmpty()) {
                        userID = event.getMessage().getMentionedUsers().get(0).getId();
                    }
                    Scanner scan = new Scanner(Main.XBAN_SERVERS);
                    while (scan.hasNextLine()) {
                        String svID = scan.nextLine();
                        if (!svID.equals("")) {
                            try {
                            event.getJDA().getGuildById(svID).ban(userID, 0, "X-ban by CharizardBot.").queue();
                            } catch (Exception e){Main.logger.info("Invalid ban");}
                        }
                    }
                    Main.logger.info(userID + " banned in the x-ban system.");
                    scan.close();
                    event.getChannel().sendMessage("Banned <@" + userID + "> from servers in the cross-ban system.").queue();
                }
            }
            if (event.getMessage().getContentRaw().startsWith(prefix + "cunban") && ( event.getMember().hasPermission(Permission.ADMINISTRATOR) || event.getAuthor().getId().equals(Main.OWNER_ID))) {
                String admins = Main.XBAN_ADMINS;
                if (event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS) && admins.contains(event.getAuthor().getId())) {
                    String userID = event.getMessage().getContentRaw().substring(8, event.getMessage().getContentRaw().length());
                    Scanner scan = new Scanner(Main.XBAN_SERVERS);
                    while (scan.hasNextLine()) {
                        String svID = scan.nextLine();
                        if (!svID.equals("")) {
                            try {
                            event.getJDA().getGuildById(svID).unban(userID).queue();
                        } catch (Exception e){Main.logger.info("Invalid unban");}
                        }
                    }
                    Main.logger.info(userID + " unbanned in the x-ban system.");
                    scan.close();
                    event.getChannel().sendMessage("Unbanned <@" + userID + "> from servers in the cross-ban system.").queue();
                }
            }
            //adds/removes admins to cross ban system so anyone can't just ban.
            if (event.getMessage().getContentRaw().startsWith(prefix + "addcadmin") && event.getAuthor().getId().equals(Main.OWNER_ID)) {
                String msg = event.getMessage().getContentRaw();
                String admins = Main.XBAN_ADMINS;
                String adminID = msg.substring(11, msg.length());
                if (!event.getMessage().getMentionedUsers().isEmpty()) {
                    adminID = event.getMessage().getMentionedUsers().get(0).getId();
                }
                admins += adminID + "\n";
                BufferedWriter writer = new BufferedWriter(new FileWriter("xbanadmins.txt"));
                writer.write(admins);
                writer.close();
                Main.XBAN_ADMINS = admins;
                event.getChannel().sendMessage("Added admin <@" + adminID + "> to the cross-ban system.").queue();
            }
            if (event.getMessage().getContentRaw().startsWith(prefix + "remcadmin") && event.getAuthor().getId().equals(Main.OWNER_ID)) {
                String msg = event.getMessage().getContentRaw();
                String adminID = msg.substring(11, msg.length());
                if (!event.getMessage().getMentionedUsers().isEmpty()) {
                    adminID = event.getMessage().getMentionedUsers().get(0).getId();
                }
                String admins = Main.XBAN_ADMINS;
               // String[] lines = admins.split(System.getProperty("line.separator"));
               String[] lines = admins.split("\n");
                for(int i=0;i<lines.length;i++){
                    if(lines[i].contains(adminID)){
                        lines[i]="";
                    }
                }
                StringBuilder adList = new StringBuilder("");
                for(String s:lines){
                    if(!s.equals("")){
                      adList.append(s).append("\n");
                }
                }
                admins = adList.toString();
                BufferedWriter writer = new BufferedWriter(new FileWriter("xbanadmins.txt"));
                writer.write(admins);
                writer.close();
                Main.XBAN_ADMINS = admins;
                event.getChannel().sendMessage("Removed admin <@" + adminID + "> from the cross-ban system.").queue();
            }
            if (event.getMessage().getContentRaw().equals(prefix + "listcadmins") && event.getAuthor().getId().equals(Main.OWNER_ID)) {
                event.getChannel().sendMessage(Main.XBAN_ADMINS).queue();
            }
          } catch (Exception e) {Main.logger.info("Exception in Cross-ban: Invalid ban?");}
    }
}