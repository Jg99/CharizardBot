package com.charizardbot.four;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
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
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
public class MessageCache implements EventListener{
    private final JDA api;
    //private final Map<String, Message> messageMap;
    private final Cache<String, Message> messageMap;
    /**
     * Message Caching for JDA v4. Adapted from
     * https://gist.github.com/Almighty-Alpaca/32629893e9cd305f1165652c80726b41 Used
     * for deleted message caching :)
     * My version, however, uses Caffeine now, which automatically handles limits and cache expiration.
     * @param api
     */
    public MessageCache(final JDA api) {
        this.api = api;
       this.messageMap = Caffeine.newBuilder()
       .maximumSize(10000)
       .expireAfterWrite(4, TimeUnit.DAYS) //auto expire, thanks to Caffeine :)
       .build();
       this.api.addEventListener(this);
    }
    public void clear() {
        this.messageMap.invalidateAll();
    }
    public Collection<Message> getCachedMessages() {
        return this.messageMap.asMap().values();
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
        return this.messageMap.asMap().get(Id);
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
            this.messageMap.asMap().remove(((MessageDeleteEvent) event).getMessageId());

        if (event instanceof MessageBulkDeleteEvent)
            this.messageMap.asMap().keySet().removeAll(((MessageBulkDeleteEvent) event).getMessageIds());

        if (event instanceof MessageUpdateEvent)
        {
            final Message message = ((MessageUpdateEvent) event).getMessage();
            this.messageMap.put(message.getId(), message);
        }
    }
}