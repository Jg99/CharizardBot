package com.charizardbot.four;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReconnectedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
public class ReconnectListener extends ListenerAdapter {
    /**
     * resets the game status when CharizardBot reconnects.
     */
public void onReconnect (ReconnectedEvent event) {
    String activity = "";
    if (Main.config.getProperty("gamestatus") == null || Main.config.getProperty("gamestatus").equals("")) {
        activity = "Battling Team Rocket";
    } else {
    activity = Main.config.getProperty("gamestatus");
    event.getJDA().getPresence().setActivity(Activity.playing(activity));
    }
}
}