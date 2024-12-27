package ru.otus.services;

import ru.otus.crm.service.DbServiceClientImpl;

public interface DbService {
    DbServiceClientImpl getDbServiceClientImpl();
    void dbMigrate();
}
