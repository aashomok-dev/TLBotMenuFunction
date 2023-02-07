package es.tl.ukr.bot.menu.data;

import java.util.List;

public record Employee(String name,
                       String surname,
                       String age,
                       String gender,
                       String whatsAppNumber,
                       String kmFromCenter,
                       List<String> skills,
                       List<String> properties,
                       String about) {
}
