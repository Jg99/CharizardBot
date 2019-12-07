# CharizardBot4
CharizardBot version 4.0 Discord bot. 

This bot is a java-based Discord bot that has a few features. Yes, it is now open source!
It uses the JDA wrapper. Version 3 and below used Javacord.

Required tokens:
Imgur API (place client id on the first line, and client secret on the 2nd in imgur_token.txt)
Clash of Clans API (place token in coc_token.txt
Discord Token (token.txt) (Note: if you want to use a token specified by arguments, uncomment the code in Main.java).
Tenor Token (tenor_token.txt)

Config files:
server_config.cfg (created automatically
*ServerID*_suggestionsdb.txt -- Suggestions DB
*ServerID*_status_suggestions.txt -- Suggestions DB
log4j2.xml (created automatically) - Logging configuration
chatfilter.txt - blank file created, specify words one per line. Use "," instead of a space.

Log location: log/charbotlogs.log It is a rotating logfile by default (editable in log4j2.xml).

Uses Maven for sources.
Once dependencies are satisfied, you can compile the bot by doing "mvn clean install" and it will spit out a jar.
