package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.util.ReflectionUtils;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                return rs.next() ? getObjectFromResultSet(rs) : null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), List.of(), rs -> {
            List<T> result = new ArrayList<>();
            try {
                while (rs.next()) {
                    result.add(getObjectFromResultSet(rs));
                }
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
            return result;
        }).orElse(List.of());
    }

    @Override
    public long insert(Connection connection, T client) {
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), entityClassMetaData.getFieldsWithoutId()
                        .stream()
                        .map(field -> {
                            field.setAccessible(true);
                            return ReflectionUtils.getField(field, client);
                        })
                        .toList());
    }

    @Override
    public void update(Connection connection, T client) {
        var params = entityClassMetaData.getAllFields()
                        .stream()
                        .map(field -> {
                            field.setAccessible(true);
                            return ReflectionUtils.getField(field, client);
                        })
                        .toList();


       dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
    }

    private T getObjectFromResultSet(ResultSet resultSet) {
        try {
            var constructor = entityClassMetaData.getConstructor();
            var object = constructor.newInstance();
            for (Field field : entityClassMetaData.getAllFields()) {
                field.setAccessible(true);
                field.set(object, resultSet.getObject(field.getName()));
            }
            return object;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
