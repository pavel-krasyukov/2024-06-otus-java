package ru.otus.demo;

import lombok.extern.log4j.Log4j;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cache.HwCache;
import ru.otus.cache.HwListener;
import ru.otus.cache.MyCache;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        demoCache();
        demoCacheDb();
    }

    private static void demoCache() {
        HwCache<String, Integer> cache = new MyCache<>();

        // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
        @SuppressWarnings("java:S1604")
        HwListener<String, Integer> listener = new HwListener<String, Integer>() {
            @Override
            public void notify(String key, Integer value, String action) {
                log.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        cache.addListener(listener);
        cache.put("1", 1);

        log.info("getValue:{}", cache.get("1"));
        cache.remove("1");
        cache.removeListener(listener);
    }

    private static void demoCacheDb() {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        ///
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        ///
        HwCache<String, Client> cache = new MyCache<>();
        HwListener<String, Client> listener = new HwListener<String, Client>() {
            @Override
            public void notify(String key, Client value, String action) {
                log.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };
        cache.addListener(listener);

        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate, cache);

        dbServiceClient.saveClient(new Client("dbServiceFirst"));
        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));

        var clientSecondSelected = dbServiceClient
                        .getClient(clientSecond.getId())
                        .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);
        ///
        dbServiceClient.saveClient(new Client(clientSecondSelected.getId(), "dbServiceSecondUpdated"));
        var clientUpdated = dbServiceClient
                        .getClient(clientSecondSelected.getId())
                        .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
        log.info("clientUpdated:{}", clientUpdated);

        log.info("All clients");
        dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));
    }
}
