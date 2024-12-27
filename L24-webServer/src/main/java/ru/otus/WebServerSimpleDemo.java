package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.server.CLientsWebServer;
import ru.otus.server.CLientsWebServerSimple;
import ru.otus.services.*;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница клиентов (переходим туда после авторизации)
    //на этой странице можно создать пользователя и получить список пользователей
    http://localhost:8080/clients

*/
public class WebServerSimpleDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        UserDao userDao = new InMemoryUserDao();
        UserAuthService userAuthService = new UserAuthServiceImpl(userDao);
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        DbService dbService = new DbServiceImpl();

        CLientsWebServer CLientsWebServer = new CLientsWebServerSimple(WEB_SERVER_PORT, userDao, gson, templateProcessor, userAuthService,
                        dbService);

        CLientsWebServer.start();
        CLientsWebServer.join();
    }
}
