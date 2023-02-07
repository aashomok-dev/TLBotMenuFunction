package es.tl.ukr.bot.menu.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record User(int id, boolean is_bot, String first_name, String last_name, String username,
                   String language_code, boolean is_premium, boolean added_to_attachment_menu,
                   boolean can_join_groups, boolean can_read_all_group_messages, boolean supports_inline_queries) {
}
