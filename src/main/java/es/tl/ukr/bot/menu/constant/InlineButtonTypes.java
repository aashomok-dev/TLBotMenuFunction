package es.tl.ukr.bot.menu.constant;

// https://unicode-table.com/en/1F517/

public enum InlineButtonTypes {
    MAIN_MENU("Меню", "main_menu"),
    USEFUL_LINKS("\uD83D\uDD17 Корисні посилання", "useful_links"),
    EXTERNAL_HELP("ℹ Інформація", "external_help"),
    DOOR_HELPER("\uD83D\uDD11 Дверi", "door_locks_help"),
    ELECTRICITY_HELPER("\uD83D\uDD0C Електрик", "electricity_help"),
    PLUMBER_HELPER("\uD83D\uDD27 Cантехнік", "plumber_help"),
    TAXI_HELPER("\uD83D\uDE95 Таксі", "taxi_help"),
    TRANSLATOR_HELPER("\uD83D\uDDE3 Перекладачі", "translator_help"),
    TRANSFER_HELPER("\uD83D\uDCEE Пошта в Україну","transfer_help"),
    ESTATE_HELPER("\uD83C\uDFE0 Аренда","estate_helper"),
    LEGAL_HELPER("\uD83E\uDD1D Юридична допомога","legal_help");

    private final String name;
    private final String value;

    InlineButtonTypes(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
