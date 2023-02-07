package es.tl.ukr.bot.menu.handler;

import es.tl.ukr.bot.menu.service.MenuRouter;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MenuHandler extends TelegramWebhookBot {

    private final String name = "help_ukr_in_torrevieja_2023_bot";
    private final String token = "5852801650:AAFvl2hAoyzJQEA3YOVhIERrn_GBroXZ2XA";

    private final MenuRouter menuRouter;

    public MenuHandler() {
        this.menuRouter = new MenuRouter();
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return handle(update);
    }

    @Override
    public String getBotPath() {
        return "https://europe-west1-tl-bot-spain.cloudfunctions.net/function-tl-ukr-bot";
    }

    private SendMessage handle(Update update) {
        Long chatId = -1L;
        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
            var optMessage = menuRouter.route(update.getMessage().getText(), chatId);

            if (optMessage.isPresent()) {
                return optMessage.get();
            }

        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            var callData = update.getCallbackQuery().getData();
            long callbackChatId = update.getCallbackQuery().getMessage().getChatId();
            var optMessage = menuRouter.route(callData, callbackChatId);
            if (optMessage.isPresent()) {
                return optMessage.get();
            }
        }

        return getFailedMessage(chatId);
    }

    private SendMessage getFailedMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Не вірна команда. Введіть /start");
        return message;
    }
}
