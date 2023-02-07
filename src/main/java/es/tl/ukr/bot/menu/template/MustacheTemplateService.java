package es.tl.ukr.bot.menu.template;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import es.tl.ukr.bot.menu.data.ExternalHelpPerson;
import es.tl.ukr.bot.menu.data.UsefulLink;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MustacheTemplateService {
    private final Mustache externalHelp;
    private final Mustache usefulLinks;

    public MustacheTemplateService() {
        MustacheFactory mf = new DefaultMustacheFactory();
        externalHelp = mf.compile("external_help.mustache");
        usefulLinks = mf.compile("useful_links.mustache");
    }

    public String compileExternalHelp(List<ExternalHelpPerson> persons) {
        Map<String, Object> context = new HashMap<>();
        context.put("persons", persons);

        StringWriter writer = new StringWriter();
        try {
            externalHelp.execute(writer, context).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public String compileUsefulLinks(List<UsefulLink> list) {
        Map<String, Object> context = new HashMap<>();
        context.put("data", list);

        StringWriter writer = new StringWriter();
        try {
            usefulLinks.execute(writer, context).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }
}
