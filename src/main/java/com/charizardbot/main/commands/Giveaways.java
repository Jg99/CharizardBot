package com.charizardbot.main.commands;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.charizardbot.main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
/* **** Original code credit: https://github.com/jagrosh/, Apache license 2.0. code adapted for CharizardBot's giveaway system. ****
*/
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.utils.PermissionUtil;

public class Giveaways extends ListenerAdapter {
       
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String prefix = Main.config.getProperty(event.getGuild().getId().toString());
    	if (prefix == null)
            prefix = "!";  
        if(event.getMessage().getContentRaw().split(" ")[0].equals(prefix + "giveawayhelp"))
        {
            event.getChannel().sendMessage("CharizardBot giveaway help:\n"
                    + "`!giveawayhelp` - this message\n"
                    + "`!giveaway <time>,<item>,<giveaway host>,[roleReqID1],[roleID2]` - starts a giveway. Ex: `!gstart 180 s,free game,John,530619324587835393,513100537818775589` for a 3 minute giveaway\n"
                    + "\n Units: s, m, h, d. Please use a space after the time. such as \"180 s\"\n"
                    + "`!rerollgiveaway <messageid>` - rerolls a winner for the giveaway on the provided message\n\n"
                    + "Commands require Manage Server permission to use\n"
                    + "Don't include <> nor []; <> means required, [] means optional").queue();
        }
        else if(event.getMessage().getContentRaw().split(" ")[0].equals(prefix + "giveaway"))
        {
            if(!PermissionUtil.checkPermission(event.getMember(), Permission.MANAGE_SERVER))
            {
                event.getChannel().sendMessage("You must have Manage Server perms to use this!").queue();
                return;
            }
            String str = event.getMessage().getContentRaw().substring(9).trim();
            String[] parts = str.split(",");
            try{
                String gTsplit[] = parts[0].split(" ");
                Integer time = 0;
                if (gTsplit.length > 1) {
                switch (gTsplit[1]) {
                
                    case "h":
                        time = Integer.parseInt(gTsplit[0]) * 60 * 60;
                        gTsplit[0] = time.toString();
                        break;
                    case "m":
                        time = Integer.parseInt(gTsplit[0]) * 60;
                        gTsplit[0] = time.toString();
                        break;
                    case "s": 
                        time = Integer.parseInt(gTsplit[0]);
                        gTsplit[0] = time.toString();
                        break;
                    case "d":
                        time = Integer.parseInt(gTsplit[0]) * 60 * 60 * 24;
                        gTsplit[0] = time.toString();
                        break;
                }
            }
                int sec = Integer.parseInt(gTsplit[0]);
                List<String> roleIds = new ArrayList<String>();
                String rolestoMention = "";
                if (parts.length > 3) {
                    int i = 3;
                    while (i < parts.length) {
                        roleIds.add(parts[i]);
                        rolestoMention += "<@&" + parts[i] + ">, ";
                        i++;
                    }
                }
                if (rolestoMention != "") {
                    event.getChannel().sendMessage("**GIVEAWAY!** \nGIVEAWAY HOST:" + parts[2] + "\nREQUIRED ROLES: " + rolestoMention + "\n" +(parts.length>1 ? "\u25AB*`"+parts[1]+"`*\u25AB\n" : "")+"React with \uD83C\uDF89 to enter!").queue(m -> {
                        m.addReaction("\uD83C\uDF89").queue();
                        new Giveaway(sec,m,parts.length>1 ? parts[1] : null,parts[2], roleIds).start();
                    });
                    event.getMessage().delete().queue();
                } else {
                event.getChannel().sendMessage("**GIVEAWAY!** \nGIVEAWAY HOST:" + parts[2] + "\n"+(parts.length>1 ? "\u25AB*`"+parts[1]+"`*\u25AB\n" : "")+"React with \uD83C\uDF89 to enter!").queue(m -> {
                    m.addReaction("\uD83C\uDF89").queue();
                    new Giveaway(sec,m,parts.length>1 ? parts[1] : null, parts[2]).start();
                });
                event.getMessage().delete().queue();
            }
            } catch(NumberFormatException ex)
            {
                event.getChannel().sendMessage("Could not parse seconds from `"+parts[0]+"`").queue();
            }
        }
        else if(event.getMessage().getContentRaw().startsWith(prefix + "rerollgiveaway"))
        {
            if(!PermissionUtil.checkPermission(event.getMember(), Permission.MANAGE_SERVER))
            {
                event.getChannel().sendMessage("You must have Manage Server perms to use this!").queue();
                return;
            }
            String id = event.getMessage().getContentRaw().substring(16).trim();
            if(!id.matches("\\d{17,22}"))
            {
                event.getChannel().sendMessage("Invalid message id").queue();
                return;
            }
            Message m = event.getChannel().retrieveMessageById(id).complete();
            if(m==null)
            {
                event.getChannel().sendMessage("Message not found!").queue();
                return;
            }
            m.getReactions()
                .stream().filter(mr -> mr.getReactionEmote().getName().equals("\uD83C\uDF89"))
                .findAny().ifPresent(mr -> {
                    List<User> users = new LinkedList<>(mr.retrieveUsers().complete());
                    List<Role> roleList = m.getMentionedRoles();
                    //check roles
                    List<Role> roles = new LinkedList<>(m.getMentionedRoles());
                    List<String> roleIds1 = new ArrayList<String>();
                    for (Role role: roleList) {
                        roleIds1.add(role.getId());
                    }
                    for (int i = 0; i < users.size(); i++) {
                    Member member = m.getGuild().retrieveMember(users.get(i)).complete();
                    if (member.getRoles().isEmpty()) {
                        if (!roles.isEmpty()) {
                            users.remove(i);
                            i--;
                        }
                    } else {
                        List<String>roleIds2 = new ArrayList<String>();
                        List<String>roleIds3 = new ArrayList<String>(roleIds1);
                        for (Role role: member.getRoles()) {
                            roleIds2.add(role.getId());
                        }
                        roleIds3.retainAll(roleIds2);
                    if (roleIds3.isEmpty()) {
                        users.remove(i);
                    } 
                }
                i++;
                    }
                    /*send message*/
                    users.remove(m.getJDA().getSelfUser());
                    if (users.isEmpty()) {
                    event.getChannel().sendMessage("No other qualified winners. :(");
                    } else {
                    String uid = users.get((int)(Math.random()*users.size())).getId();
                    event.getChannel().sendMessage("Congratulations to <@"+uid+">! You won the reroll!").queue();
                    }
                });
        }
    }
    /* end onMessageReceived

    begin Giveaway class */
    public class Giveaway {
        int seconds;
        Message message;
        String item;
        List<String> roles;
        String host;
        public Giveaway(int time, Message message, String item, String host)
        {
            seconds = time;
            this.message = message;
            this.item = item;
            this.host = host;
        }
        public Giveaway(int time, Message message, String item, String host, List<String> roles)
        {
            seconds = time;
            this.message = message;
            this.item = item;
            this.roles = roles;
            this.host = host;
        }
        /*start the giveaways*/
        public void start()
        {
            new Thread(){
                @Override
                public void run() {
                    try {
                    while(seconds>5)
                    {
                        if (!roles.isEmpty()) {
                            String reqRoles = "";
                            int i = 0;
                            while (i < roles.size()) {
                                reqRoles += "<@&" + roles.get(i) + ">, ";
                                i++;
                            }
                        message.editMessage("**GIVEAWAY!**\nGIVEAWAY HOST: " + host + "\nREQUIRED ROLES: " + reqRoles + "\n" +(item!=null ? "\u25AB*`"+item+"`*\u25AB\n" : "")+"React with \uD83C\uDF89 to enter!\nTime remaining: "+secondsToTime(seconds)).queue();
                        seconds-=5;
                        try{Thread.sleep(5000);}catch(Exception e){Thread.currentThread().interrupt();}
                        } else {
                                message.editMessage("**GIVEAWAY!**\nGIVEAWAY HOST: " + host + "\n" +(item!=null ? "\u25AB*`"+item+"`*\u25AB\n" : "")+"React with \uD83C\uDF89 to enter!\nTime remaining: "+secondsToTime(seconds)).queue();
                                seconds-=5;
                                try{Thread.sleep(5000);}catch(Exception e){Thread.currentThread().interrupt();}
                        
                    }
                }
                    while(seconds>0)
                    {
                        if (!roles.isEmpty()) {
                            String reqRoles = "";
                            int i = 0;
                            while (i < roles.size()) {
                                reqRoles += "<@&" + roles.get(i) + ">, ";
                                i++;
                            }
                        message.editMessage("**G I V E A W A Y!**\nLAST CHANCE TO ENTER!!!\nGIVEAWAY HOST: " + host + "\nREQUIRED ROLES: " + reqRoles + "\n" +(item!=null ? "\u25AB*`"+item+"`*\u25AB\n" : "")+"React with \uD83C\uDF89 to enter!\nTime remaining: "+secondsToTime(seconds)).queue();
                        seconds--;
                        try{Thread.sleep(1000);}catch(Exception e){Thread.currentThread().interrupt();}
                    } else {
                        message.editMessage("**G I V E A W A Y!**\nLAST CHANCE TO ENTER!!!\nGIVEAWAY HOST: " + host + "\n" +(item!=null ? "\u25AB*`"+item+"`*\u25AB\n" : "")+"React with \uD83C\uDF89 to enter!\nTime remaining: "+secondsToTime(seconds)).queue();
                        seconds--;
                        try{Thread.sleep(1000);}catch(Exception e){Thread.currentThread().interrupt();}
                    }
                    }
                    message.getChannel().retrieveMessageById(message.getId()).complete().getReactions()
                            .stream().filter(mr -> mr.getReactionEmote().getName().equals("\uD83C\uDF89"))
                            .findAny().ifPresent(mr -> {
                                String reqRoles = "";
                                List<User> users = new LinkedList<>(mr.retrieveUsers().complete());
                                /*check for any role requirements*/
                                List<Role> roleList = message.getMentionedRoles();
                                for (Role role : roleList) {
                                    reqRoles += role.getAsMention()+ ", ";
                                }
                                List<String> roleIds1 = new ArrayList<String>();
                                for (Role role: roleList) {
                                    roleIds1.add(role.getId());
                                }
                                for (int i = 0; i < users.size(); i++) {
                                Member member = message.getGuild().retrieveMember(users.get(i)).complete();
                                if (member.getRoles().isEmpty()) {
                                    if (!roles.isEmpty()) {
                                        users.remove(i);
                                        i--;
                                    }
                                } else {
                                    List<String>roleIds2 = new ArrayList<String>();
                                    List<String>roleIds3 = new ArrayList<String>(roleIds1);
                                    for (Role role: member.getRoles()) {
                                        roleIds2.add(role.getId());
                                    }
                                    roleIds3.retainAll(roleIds2);
                                if (roleIds3.isEmpty()) {
                                    users.remove(i);
                                } 
                            }
                            i++;
                                }
                                /*send message*/
                                users.remove(message.getJDA().getSelfUser());
                                if (!users.isEmpty()) {
                                String id = users.get((int)(Math.random()*users.size())).getId();
                                message.editMessage("**GIVEAWAY ENDED!**\nGIVEAWAY HOST:"+ host + "\nREQUIRED ROLES: " + reqRoles + "\n"+(item!=null ? "\u25AB*`"+item+"`*\u25AB\n" : "")+"\nWinner: <@"+id+"> \uD83C\uDF89").queue();
                                message.getChannel().sendMessage("Congratulations to <@"+id+">! You won"+(item==null ? "" : " the "+item)+"!").queue();
                                } else {
                                    message.editMessage("**GIVEAWAY ENDED!**\nGIVEAWAY HOST: " + host + "\n" +(item!=null ? "\u25AB*`"+item+"`*\u25AB\n" : "")+"\nThere was no winner with a qualifying role.").queue();
                                    message.getChannel().sendMessage("No qualifying users reacted for the giveaway :(").queue();
                                }
                            });
                
                     } catch (Exception e) {Thread.currentThread().interrupt();}
                 }
            }.start();
        }
    }
    /* convert time */
    public static String secondsToTime(long timeseconds)
    {
        StringBuilder builder = new StringBuilder();
        int years = (int)(timeseconds / (60*60*24*365));
        if(years>0)
        {
            builder.append("**").append(years).append("** years, ");
            timeseconds = timeseconds % (60*60*24*365);
        }
        int weeks = (int)(timeseconds / (60*60*24*365));
        if(weeks>0)
        {
            builder.append("**").append(weeks).append("** weeks, ");
            timeseconds = timeseconds % (60*60*24*7);
        }
        int days = (int)(timeseconds / (60*60*24));
        if(days>0)
        {
            builder.append("**").append(days).append("** days, ");
            timeseconds = timeseconds % (60*60*24);
        }
        int hours = (int)(timeseconds / (60*60));
        if(hours>0)
        {
            builder.append("**").append(hours).append("** hours, ");
            timeseconds = timeseconds % (60*60);
        }
        int minutes = (int)(timeseconds / (60));
        if(minutes>0)
        {
            builder.append("**").append(minutes).append("** minutes, ");
            timeseconds = timeseconds % (60);
        }
        if(timeseconds>0)
            builder.append("**").append(timeseconds).append("** seconds");
        String str = builder.toString();
        if(str.endsWith(", "))
            str = str.substring(0,str.length()-2);
        if(str.equals(""))
            str="**No time**";
        return str;
    }

}
