# CharizardBot
CharizardBot Discord bot. 

Stable version: 4.6.4, NO LONGER IN DEVELOPMENT, JDA 4 WILL BE UNUSABLE!
5.0 beta - semi stable, may eventually migrate to slash commands, besides moderation and the help command will probably be under both.
Changes are reflected in the CHANGELOG file.


If you enjoy my work and wish to donate, my Discord's patreon is https://patreon.com/gammastradingpost
This bot is a java-based Discord bot that has a few features. Yes, it is now open source! Do whatever the heck you want with the code, but please contribute and keep any changes open source.
It uses the JDA Discord API. Version 3 and below used Javacord, and is not open source.
```
Required tokens for this bot:
Imgur API (place client id on the first line, and client secret on the 2nd in imgur_token.txt)
Clash of Clans API (place token in coc_token.txt
Discord Token (token.txt) (Note: if you want to use a token specified by arguments, uncomment the code in Main.java).
Tenor Token (tenor_token.txt)
Reddit Token (reddit_token.txt). Client id on first line, client secret on 2nd. Type of account: script.
Config files:
server_config.cfg (created automatically
*ServerID*_suggestionsdb.txt -- Suggestions DB
*ServerID*_status_suggestions.txt -- Suggestions DB
log4j2.xml (created automatically) - Logging configuration
chatfilter.txt - blank file created, specify words one per line. Use "," instead of a space.
```

Log location: log/charbotlogs.log It is a rotating logfile by default (editable in log4j2.xml).

Uses Maven for sources.
Note: This project requires Lombok to compile. There will be errors if you don't have it installed.
Once dependencies are satisfied, you can compile the bot by doing "mvn clean install" and it will spit out a jar in target/.

If you have any questions, feel free to join the CharizardBot discord found on the CharizardBot website!

**NOTE:**
Only the bot source code is provided under the GNU GPL Licence version 2.0.
Any images, avatars, etc are protected by copyright law and may not be used to impersonate the main bot.

**PRIVACY**

This bot does not store user data on its internal logging system. Only basic info is logged for finding issues. However, server moderators may enable moderation action logging via commands and they log basic information such as the User ID, action, and reason, as a message sent to the specified channel. 

**PRIVILEGED INTENT REQUIREMENTS**
This bot REQUIRES use of several privileged intents in order to function, including MESSAGE_CONTENT (Moderation tools and responding to commands until migration to slash), GUILD_MEMBERS(get info of a member), GUILD_MESSAGES(messages), GUILD_BANS(ban and unban users), and GUILD_MESSAGES (messages in a Server).
