/**
 * @author administrator on 24.12.2024.
 */
package ru.otus.services;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DbServiceClientImpl;

public class DbServiceImpl implements DbService {
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    private final Configuration configuration;
    private final String dbUrl;
    private final String dbUserName;
    private final String dbPassword;
    private final SessionFactory sessionFactory;
    private final TransactionManagerHibernate transactionManager;
    private final DataTemplateHibernate clientTemplate;
    private final DbServiceClientImpl dbServiceClient;

    public DbServiceImpl(){
	this.configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

	this.dbUrl = configuration.getProperty("hibernate.connection.url");
	this.dbUserName = configuration.getProperty("hibernate.connection.username");
	this.dbPassword = configuration.getProperty("hibernate.connection.password");
	dbMigrate();
	this.sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);
	this.transactionManager = new TransactionManagerHibernate(sessionFactory);
	this.clientTemplate = new DataTemplateHibernate<>(Client.class);
	this.dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
    }

    public void dbMigrate(){
	new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();
    }

    @Override
    public DbServiceClientImpl getDbServiceClientImpl() {
	return this.dbServiceClient;
    }
}
