package es.tl.ukr.bot.menu.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MessageEntity(int offset, int length, String type) {
}
