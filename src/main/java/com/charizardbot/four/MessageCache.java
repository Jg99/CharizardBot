package com.charizardbot.four;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.internal.requests.EmptyRestAction;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageBulkDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class MessageCache implements EventListener {
    private final JDA api;
    private final Map<String, Message> messageMap;
/**
 * Message Caching for JDA. Adapted from https://gist.github.com/Almighty-Alpaca/32629893e9cd305f1165652c80726b41
 * Used for deleted message caching :)
 * @param api
 * @param weak
 */
    public MessageCache(final JDA api, final boolean weak)
    {
        this.api = api;
        this.messageMap = Collections.synchronizedMap(weak ? new WeakHashMap<String, Message>() : new HashMap<String, Message>());
        this.api.addEventListener(this);
    }
    public void clear()
    {
        this.messageMap.clear();
    }
    public Collection<Message> getCachedMessages()
    {
        return this.messageMap.values();
    }
    public RestAction<Message> getMessage(final MessageChannel channel, final String Id)
    {
        final Message message = this.getMessage(Id);

        if (message == null)
            return channel.retrieveMessageById(Id);
        else 
        return new EmptyRestAction<Message>(api, message);
        }
    public Message getMessage(final String Id)
    {
        return this.messageMap.get(Id);
    }
    public RestAction<Message> getMessage(final String channelId, final String Id)
    {
        final Message message = this.getMessage(Id);

        if (message == null)
        {
            MessageChannel channel = this.api.getTextChannelById(channelId);

            if (channel == null)
                channel = this.api.getPrivateChannelById(channelId);

            if (channel != null)
                return channel.retrieveMessageById(Id);

        }

        return new EmptyRestAction<Message>(api, message);

    }
    @Override
    @SubscribeEvent
    public void onEvent(GenericEvent event)
    {
        if (event instanceof MessageReceivedEvent)
        {
            final Message message = ((MessageReceivedEvent) event).getMessage();
            this.messageMap.put(message.getId(), message);
        }

        if (event instanceof MessageDeleteEvent)
            this.messageMap.remove(((MessageDeleteEvent) event).getMessageId());

        if (event instanceof MessageBulkDeleteEvent)
            this.messageMap.keySet().removeAll(((MessageBulkDeleteEvent) event).getMessageIds());

        if (event instanceof MessageUpdateEvent)
        {
            final Message message = ((MessageUpdateEvent) event).getMessage();
            this.messageMap.put(message.getId(), message);
        }
    }
}