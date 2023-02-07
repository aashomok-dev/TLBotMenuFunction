package es.tl.ukr.bot.menu.service;

import es.tl.ukr.bot.menu.constant.InlineButtonTypes;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.EXTERNAL_HELP;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.MAIN_MENU;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.USEFUL_LINKS;

public class MainMenuService implements MenuService {

    public SendMessage create(Long chatId) {
        SendMessage message = new SendMessage(); // Create a message object object

        message.setText("""
                Чат записник корисних контактів. 
                Ми намагаємося зробити його максимально доступним та гнучким. 
                Якщо Ви маєте пропозиції, що до покращєння у використанні,
                або додаванні нової інформації. Пищіть на aashomok.dev@gmail.com
                """);

        message.setChatId(chatId);
        message.setText(MAIN_MENU.getName());

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(1);
        List<InlineKeyboardButton> rowInline = new ArrayList<>(2);

        InlineKeyboardButton chatButton = new InlineKeyboardButton();
        chatButton.setText(USEFUL_LINKS.getName());
        chatButton.setCallbackData(USEFUL_LINKS.getValue());

        InlineKeyboardButton externalHelpButton = new InlineKeyboardButton();
        externalHelpButton.setText(EXTERNAL_HELP.getName());
        externalHelpButton.setCallbackData(EXTERNAL_HELP.getValue());

        rowInline.add(externalHelpButton);
        rowInline.add(chatButton);
        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        return message;
    }

    @Override
    public SendMessage getMessage(long chatId, InlineButtonTypes inlineButtonTypes) {
        return create(chatId);
    }

    @Override
    public boolean contains(InlineButtonTypes inlineButtonTypes) {
        return MAIN_MENU.equals(inlineButtonTypes);
    }
}
