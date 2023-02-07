package es.tl.ukr.bot.menu.service;

import es.tl.ukr.bot.menu.constant.InlineButtonTypes;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MenuService {
    SendMessage getMessage(long chatId, InlineButtonTypes inlineButtonTypes);
    boolean contains(InlineButtonTypes inlineButtonTypes);
}
