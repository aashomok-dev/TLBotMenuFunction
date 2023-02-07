package es.tl.ukr.bot.menu.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Message(int message_id, int message_thread_id, User from, Chat sender_chat, int date,
                      Chat chat, User forward_from, Chat forward_from_chat, int forward_from_message_id,
                      String text, List<MessageEntity> entities) {
}
