package es.tl.ukr.bot.menu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.tl.ukr.bot.menu.constant.InlineButtonTypes;
import es.tl.ukr.bot.menu.data.UsefulLinksContainer;
import es.tl.ukr.bot.menu.template.MustacheTemplateService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.MAIN_MENU;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.USEFUL_LINKS;

public class UsefulLinksMenuService implements MenuService {

    private final MustacheTemplateService mustacheTemplateService;
    private Optional<UsefulLinksContainer> usefulLinksContainer = Optional.empty();

    public UsefulLinksMenuService() {
        var objectMapper = new ObjectMapper();
        try {
            var eh = objectMapper.readValue(DATA, UsefulLinksContainer.class);
            usefulLinksContainer = Optional.of(eh);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mustacheTemplateService = new MustacheTemplateService();
    }

    @Override
    public SendMessage getMessage(long chatId, InlineButtonTypes inlineButtonTypes) {
        SendMessage message = new SendMessage(); // Create a message object object
        message.setChatId(chatId);

        if (usefulLinksContainer.isPresent()) {
            var links = usefulLinksContainer.get().data();
            message.setText(mustacheTemplateService.compileUsefulLinks(links));
            message.setParseMode("Markdown");
        }

        InlineKeyboardButton inlineBackButton = new InlineKeyboardButton();
        inlineBackButton.setText(MAIN_MENU.getName());
        inlineBackButton.setCallbackData(MAIN_MENU.getValue());

        message.setReplyMarkup(addBackButton());

        return message;
    }

    @Override
    public boolean contains(InlineButtonTypes inlineButtonTypes) {
        return USEFUL_LINKS.equals(inlineButtonTypes);
    }

    private InlineKeyboardMarkup addBackButton() {
        InlineKeyboardButton inlineBackButton = new InlineKeyboardButton();
        inlineBackButton.setText(MAIN_MENU.getName());
        inlineBackButton.setCallbackData(MAIN_MENU.getValue());

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(1);
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>(1);

        rowInline1.add(inlineBackButton);
        rowsInline.add(rowInline1);
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }

    private static final String DATA = """
            {
              "data": [
                {
                  "description": "Сторінка допомоги українцям у фейсбуці",
                  "links": [
                    "https://www.facebook.com/groups/715996029745948",
                    "https://www.facebook.com/isabelle.centelles"
                  ]
                },
                {
                  "description": "Чат Асоціації українців Торревєхи",
                  "links": [
                    "https://t.me/asociaciondetorrevieja"
                  ]
                },
                {
                  "description": "Чат Барахолка Торревєха",
                  "links": [
                    "https://t.me/baraholka_torrevieja"
                  ]
                },
                {
                  "description": "Чат  Молодіжна група Торревєха українці в телеграм",
                  "links": [
                    "https://t.me/+qkDMeNuP1BlhMjQ0"
                  ]
                },
                {
                  "description": "Чат лікарів іспанський",
                  "links": [
                    "https://t.me/asesor_medico"
                  ]
                },
                {
                  "description": "Автомобільний чат іспанський",
                  "links": [
                    "https://t.me/AutoSpain"
                  ]
                },
                {
                  "description": "Родители Торевьеха",
                  "links": [
                    "https://www.facebook.com/groups/118577222156508"
                  ]
                },
                {
                  "description": "Майстри краси/послуги краси, Торревєха.",
                  "links": [
                    "https://t.me/ukrainianintorrevieja"
                  ]
                },
                {
                  "description": "Інфораціний чат підтримки біженці Торревєха/Аліканте",
                  "links": [
                    "https://t.me/infoprovsefromK"
                  ]
                },
                {
                  "description": "Сайт мерії",
                  "links": [
                    "https://torrevieja.es/ru/%20themes%20/%20education"
                  ]
                }
              ]
            }
            """;

}
