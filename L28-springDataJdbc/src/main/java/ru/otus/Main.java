/**
 * @author administrator on 30.12.2024.
 */
package ru.otus;

import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.dbmigrations.MigrationsExecutorFlyway;

//http://localhost:8080/clients

@SpringBootApplication
public class Main {
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
	Configuration configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

	String dbUrl = configuration.getProperty("hibernate.connection.url");
	String dbUserName = configuration.getProperty("hibernate.connection.username");
	String dbPassword = configuration.getProperty("hibernate.connection.password");
	new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();
	SpringApplication.run(Main.class);
    }
}
