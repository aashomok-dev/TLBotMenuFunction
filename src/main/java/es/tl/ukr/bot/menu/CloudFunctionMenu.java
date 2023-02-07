package es.tl.ukr.bot.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import es.tl.ukr.bot.menu.handler.MenuHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.net.HttpURLConnection;

public class CloudFunctionMenu implements HttpFunction {

    private final MenuHandler handler;
    private final ObjectMapper mapper;

    public CloudFunctionMenu() {
        this.handler = new MenuHandler();
        this.mapper = new ObjectMapper();
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            var update = mapper.readValue(request.getReader(), Update.class);
            var message = handler.onWebhookUpdateReceived(update);

            response.setStatusCode(HttpURLConnection.HTTP_OK);
            response.setContentType("application/json; charset=utf-8");
            var writer = response.getWriter();
            mapper.writeValue(writer, message);
        }
    }

}
