/**
 * @author administrator on 24.12.2024.
 */
package ru.otus.servlet;

import com.google.gson.Gson;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Client;
import ru.otus.services.DbService;

import java.io.*;
import java.util.List;

public class ClientsServlet extends HttpServlet {
    private final DbService dbService;
    private final transient Gson gson;

    public ClientsServlet(DbService dbService, Gson gson){
	this.dbService = dbService;
	this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

	//если запрос связан с получением списка клиентов
	if ("/clients/list".equals(req.getServletPath())) {
	    // Обработка запроса на получение списка клиентов
	    List<Client> clientList = dbService.getDbServiceClientImpl().findAll();
	    resp.setContentType("application/json;charset=UTF-8");
	    resp.getWriter().print(gson.toJson(clientList)); // отправляем список клиентов в формате JSON
	    return;
	}
        //Иначе возвращаем страницу работы с клиентами
	// Устанавливаем тип контента
	resp.setContentType("text/html;charset=UTF-8");
	// Получаем поток с содержимым файла clients.html
	InputStream inputStream = getClass().getClassLoader().getResourceAsStream("templates/clients.html");
	if (inputStream == null) {
	    throw new ServletException("Файл templates/clients.html не найден");
	}

	try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			PrintWriter out = resp.getWriter()) {
	    String line;
	    while ((line = reader.readLine()) != null) {
		out.println(line);
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при чтении файла");
	}
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	String name = req.getParameter("name");
	Client client = new Client(name);
	dbService.getDbServiceClientImpl().saveClient(client);
	resp.getOutputStream().print("The client's information has been successfully saved");
    }
}
