package com.charizardbot.four.commands;

import com.charizardbot.four.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.lang.reflect.Array;
import java.util.List;

import static com.charizardbot.four.Main.CLEAR_MODS;

public class Clears extends ListenerAdapter {
    private Object User;

    //TODO: Write Clear commands for MM, AS, ES, and Mods+, Reading from a file where the roles can be stored
    // Include required channels for each of the roles except Mods+,
    // only Admins can add to these lists?
    // useable by checking the role. Roles will determine if can be used
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {

        try {
            String prefix = Main.config.getProperty(event.getGuild().getId().toString());
            if (prefix == null)
                prefix = "!";
            String args[] = event.getMessage().getContentRaw().split(" ");
            if(args[0].equalsIgnoreCase(prefix + "addMod") && (event.getMember().hasPermission(Permission.ADMINISTRATOR)))
            {
                String[] roles = new String[10];
                String[] roles2 = new String[10];

                for(int i = 1; args[i] != null; i++)
                {
                    String role = args[i];
                    //String role = event.getGuild().getRoleById(args[i]).toString();
                    roles[i-1] = role;
                    CLEAR_MODS += args[i] + ",";
                    event.getChannel().sendMessage(roles[i-1]).queue();
                }
            }



            if(args[0].equalsIgnoreCase(prefix + "clear") && (event.getMember().hasPermission(Permission.MESSAGE_MANAGE) || event.getMember().hasPermission(Permission.ADMINISTRATOR)))
            {
                TextChannel channel = event.getChannel();
                MessageHistory history = new MessageHistory(channel);
                List<Message> msg;
                int num = Integer.parseInt(args[1]);
                msg = history.retrievePast(num).complete();
                channel.deleteMessages(msg).queue();
                event.getChannel().sendMessage("Cleared " + num + " messages").queue();
            }
        if(args[0].equalsIgnoreCase(prefix + "asclear") && (event.getMember().hasPermission(Permission.MESSAGE_MANAGE) || event.getMember().hasPermission(Permission.ADMINISTRATOR)))
        {
            String userID = event.getAuthor().getId();
            String[] roles = CLEAR_MODS.split(",");
            Member member = event.getGuild().getMemberById(userID);
            List<Role> memberRoles = member.getRoles();


            for(int i = 0; i < roles.length; i++)
            {
                Role role1 = (Role) roles[i];
                List<Member> roleMembers = event.getGuild().getMembersWithRoles(role1);
                if(memberRoles.contains(roles[i]))
                    event.getChannel().sendMessage("Found Role " + roles[i]);
            }


            TextChannel channel = event.getChannel();
            MessageHistory history = new MessageHistory(channel);
            List<Message> msg;
            int num = Integer.parseInt(args[1]);
            msg = history.retrievePast(num).complete();
            channel.deleteMessages(msg).queue();
            event.getChannel().sendMessage("Cleared " + num + " messages").queue();
            //event.getChannel().sendMessage("Member " + member.toString()).queue();
        }
    }
        catch (Exception e) {
            Main.logger.info("Exception in Clear: Invalid clear?");
        }
    }
}
    