package es.tl.ukr.bot.menu.service;

import es.tl.ukr.bot.menu.constant.InlineButtonTypes;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MenuRouter {

    private final MainMenuService mainMenuService;
    private final List<MenuService> menuServices;

    public MenuRouter() {
        this.mainMenuService = new MainMenuService();
        menuServices = List.of(new MainMenuService(),
                new UsefulLinksMenuService(), new ExternalMenuService());
    }

    public Optional<SendMessage> route(String command, long chatId) {
        if ("/start".equalsIgnoreCase(command)) {
            return Optional.of(mainMenuService.create(chatId));
        }

        var optType= Arrays.stream(InlineButtonTypes.values())
                .filter(t -> t.getValue().equalsIgnoreCase(command))
                .findFirst();

        if (optType.isPresent()) {
            var type = optType.get();
            var optionalMenuService = menuServices.stream()
                    .filter(ms -> ms.contains(type))
                    .findFirst();

            return optionalMenuService.map(menuService -> menuService.getMessage(chatId, type));
        }

        return Optional.empty();
    }

}
