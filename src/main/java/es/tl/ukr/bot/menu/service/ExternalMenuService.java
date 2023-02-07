package es.tl.ukr.bot.menu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.tl.ukr.bot.menu.constant.InlineButtonTypes;
import es.tl.ukr.bot.menu.data.ExternalHelp;
import es.tl.ukr.bot.menu.data.ExternalHelpContainer;
import es.tl.ukr.bot.menu.template.MustacheTemplateService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.DOOR_HELPER;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.ELECTRICITY_HELPER;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.ESTATE_HELPER;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.EXTERNAL_HELP;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.LEGAL_HELPER;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.MAIN_MENU;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.PLUMBER_HELPER;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.TAXI_HELPER;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.TRANSFER_HELPER;
import static es.tl.ukr.bot.menu.constant.InlineButtonTypes.TRANSLATOR_HELPER;

public class ExternalMenuService implements MenuService {

    private final MustacheTemplateService mustacheTemplateService;
    private final List<InlineButtonTypes> types;
    private Optional<ExternalHelp> externalHelp = Optional.empty();

    public ExternalMenuService() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            var eh = objectMapper.readValue(DATA, ExternalHelp.class);
            externalHelp = Optional.of(eh);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mustacheTemplateService = new MustacheTemplateService();
        types = List.of(TAXI_HELPER, ELECTRICITY_HELPER, PLUMBER_HELPER, DOOR_HELPER, TRANSLATOR_HELPER,
                TRANSFER_HELPER, ESTATE_HELPER, LEGAL_HELPER, EXTERNAL_HELP);
    }

    @Override
    public SendMessage getMessage(long chatId, InlineButtonTypes inlineButtonTypes) {
        if (InlineButtonTypes.EXTERNAL_HELP.equals(inlineButtonTypes)) {
            return create(chatId);
        }

        return subMenuMessage(chatId, inlineButtonTypes);
    }

    public SendMessage create(Long chatId) {
        SendMessage message = new SendMessage(); // Create a message object object
        message.setChatId(chatId);
        message.setText("Виберіть розділ");
        message.setReplyMarkup(getExternalMenu());
        return message;
    }

    private SendMessage subMenuMessage(long chatId, InlineButtonTypes inlineButtonTypes) {
        SendMessage message = new SendMessage(); // Create a message object object
        message.setChatId(chatId);
        message.setText("Сабменю");

        if (externalHelp.isPresent()) {
            var persons = externalHelp.get().external_helpers().stream()
                    .filter(helper -> inlineButtonTypes.getValue().equalsIgnoreCase(helper.type()))
                    .map(ExternalHelpContainer::contacts)
                    .flatMap(Collection::stream)
                    .toList();

            message.setText(mustacheTemplateService.compileExternalHelp(persons));
        }

        message.setParseMode("Markdown");
        message.setReplyMarkup(getExternalMenu());

        return message;
    }

    private InlineKeyboardMarkup getExternalMenu() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(5);
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>(2);
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>(2);
        List<InlineKeyboardButton> rowInline3 = new ArrayList<>(2);
        List<InlineKeyboardButton> rowInline4 = new ArrayList<>(2);
        List<InlineKeyboardButton> rowInline5 = new ArrayList<>(1);

        InlineKeyboardButton inlineDoorLocksButton = new InlineKeyboardButton();
        inlineDoorLocksButton.setText(DOOR_HELPER.getName());
        inlineDoorLocksButton.setCallbackData(DOOR_HELPER.getValue());

        InlineKeyboardButton inlineElectricButton = new InlineKeyboardButton();
        inlineElectricButton.setText(ELECTRICITY_HELPER.getName());
        inlineElectricButton.setCallbackData(ELECTRICITY_HELPER.getValue());

        InlineKeyboardButton inlinePlumberButton = new InlineKeyboardButton();
        inlinePlumberButton.setText(PLUMBER_HELPER.getName());
        inlinePlumberButton.setCallbackData(PLUMBER_HELPER.getValue());

        InlineKeyboardButton inlineTaxiButton = new InlineKeyboardButton();
        inlineTaxiButton.setText(TAXI_HELPER.getName());
        inlineTaxiButton.setCallbackData(TAXI_HELPER.getValue());

        InlineKeyboardButton inlineTranslatorButton = new InlineKeyboardButton();
        inlineTranslatorButton.setText(TRANSLATOR_HELPER.getName());
        inlineTranslatorButton.setCallbackData(TRANSLATOR_HELPER.getValue());

        InlineKeyboardButton inlineTransferButton = new InlineKeyboardButton();
        inlineTransferButton.setText(TRANSFER_HELPER.getName());
        inlineTransferButton.setCallbackData(TRANSFER_HELPER.getValue());

        InlineKeyboardButton inlineEstateButton = new InlineKeyboardButton();
        inlineEstateButton.setText(ESTATE_HELPER.getName());
        inlineEstateButton.setCallbackData(ESTATE_HELPER.getValue());

        InlineKeyboardButton inlineLegalButton = new InlineKeyboardButton();
        inlineLegalButton.setText(LEGAL_HELPER.getName());
        inlineLegalButton.setCallbackData(LEGAL_HELPER.getValue());

        InlineKeyboardButton inlineBackButton = new InlineKeyboardButton();
        inlineBackButton.setText(MAIN_MENU.getName());
        inlineBackButton.setCallbackData(MAIN_MENU.getValue());

        rowInline1.add(inlineDoorLocksButton);
        rowInline1.add(inlineElectricButton);
        rowInline2.add(inlinePlumberButton);
        rowInline2.add(inlineTaxiButton);
        rowInline3.add(inlineTranslatorButton);
        rowInline3.add(inlineTransferButton);
        rowInline4.add(inlineEstateButton);
        rowInline4.add(inlineLegalButton);
        rowInline5.add(inlineBackButton);
        // Set the keyboard to the markup
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);
        rowsInline.add(rowInline4);
        rowsInline.add(rowInline5);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }

    @Override
    public boolean contains(InlineButtonTypes inlineButtonTypes) {
        return types.contains(inlineButtonTypes);
    }

    private static final String DATA = """
            {
              "external_helpers": [
                {
                  "type": "door_locks_help",
                  "contacts": [
                    {
                      "name": "Людвиг",
                      "phone": "+34606329725",
                      "description": ""
                    },
                    {
                      "name": "Іля",
                      "phone": "+34688790760",
                      "description": ""
                    },
                    {
                      "name": "Ferreteria Perelló",
                      "phone": "+34965705088",
                      "description": "https://maps.app.goo.gl/2A7wDKGzHyPWWmkd9"
                    }
                  ]
                },
                {
                  "type": "electricity_help",
                  "contacts": [
                    {
                      "name": "Ігор",
                      "phone": "+34617059648",
                      "description": ""
                    },
                    {
                      "name": "Марян",
                      "phone": "+34643144609",
                      "description": ""
                    }
                  ]
                },
                {
                  "type": "plumber_help",
                  "contacts": [
                    {
                      "name": "Ігор",
                      "phone": "+34617059648",
                      "description": ""
                    }
                  ]
                },
                {
                  "type": "taxi_help",
                  "contacts": [
                    {
                      "name": "Nordisk Taxi",
                      "phone": "+34622157212",
                      "description": "4,5 (716 відгуків)"
                    },
                    {
                      "name": "MaxiTaxi",
                      "phone": "+34627464577",
                      "description": "4,6 (75 відгуків)"
                    },
                    {
                      "name": "Сергій",
                      "phone": "+34605541214",
                      "description": ""
                    },
                    {
                      "name": "Олександр",
                      "phone": "+34623531762",
                      "description": ""
                    },
                    {
                      "name": "Руслан",
                      "phone": "+380674223355",
                      "description": ""
                    },
                    {
                      "name": "Анита",
                      "phone": "+34643638842",
                      "description": ""
                    }
                  ]
                },
                {
                  "type": "translator_help",
                  "contacts": [
                    {
                      "name": "Ала",
                      "phone": "+34611327292",
                      "description": ""
                    },
                    {
                      "name": "Наталія",
                      "phone": "+34675226449",
                      "description": "допомагає в медичних установах"
                    },
                    {
                      "name": "Альбіна",
                      "phone": "+34654607760",
                        
                      "description": "допомагає в медичних установах"
                    },
                    {
                      "name": "Олена",
                      "phone": "+34615212582",
                      "description": ""
                    },
                    {
                      "name": "Софія",
                      "phone": "+34653893425",
                      "description": "допомагає і в державних установах"
                    },
                    {
                      "name": "Ольга",
                      "phone": "+34622106645",
                      "description": "з машиною, перекладає в будь-яких установах"
                    },
                    {
                      "name": "Олена",
                      "phone": "+34674424217",
                      "description": ""
                    },
                    {
                      "name": "Українсько-іспанський перекладач по телефону",
                      "phone": "+34936940515",
                      "description": "з 9 до 17, безкоштовно"
                    }
                  ]
                },
                {
                  "type": "transfer_help",
                  "contacts": [
                    {
                      "name": "Зоряна",
                      "phone": "+346652215573",
                      "description": "https://t.me/spaintrans"
                    },
                    {
                      "name": "Тарас",
                      "phone": "+380977858287",
                      "description": ""
                    },
                    {
                      "name": "Семен",
                      "phone": "+34622257553",
                      "description": ""
                    }
                  ]
                },
                {
                  "type": "estate_helper",
                  "contacts": [
                    {
                      "name": "Олексій",
                      "phone": "+34661592594",
                      "description": "Менеджер компанії Алегрія"
                    }
                  ]
                },
                {
                  "type": "legal_help",
                  "contacts": [
                    {
                      "name": "Дмитро",
                      "phone": "+34607430439",
                      "description": "питання нерухомості, розлучення інше"
                    },
                    {
                      "name": "Елена",
                      "phone": "+34642679995",
                      "description": "Хестор"
                    },
                    {
                      "name": "Віталій",
                      "phone": "+34952585449",
                      "description": "юридична допомога адвоката у справах спадщини (www.delgadogarrucho.com)"
                    }
                  ]
                }
              ]
            }
            """;

}
