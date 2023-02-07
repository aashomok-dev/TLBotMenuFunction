package es.tl.ukr.bot.menu.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Chat(int id, String type, String title, String username, boolean is_forum) {
}
