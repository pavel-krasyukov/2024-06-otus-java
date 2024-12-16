/**
 * @author administrator on 11.12.2024.
 */
package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData{

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
	this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
	return String.format("select * from %s", entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
	return String.format("select * from %s where %s = ?", entityClassMetaData.getName(), entityClassMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
	String fieldsStr = entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName)
			.collect(Collectors.joining(", "));
	String valuesStr = entityClassMetaData.getFieldsWithoutId().stream().map(f -> "?").collect(Collectors.joining(", "));

	return String.format("insert into %s (%s) values (%s)", entityClassMetaData.getName(), fieldsStr, valuesStr);
    }

    @Override
    public String getUpdateSql() {
	String fieldsStr = entityClassMetaData.getFieldsWithoutId().stream().map(field -> field.getName()+"=?")
			.collect(Collectors.joining(","));
	return String.format("update %s set %s where %s = ?", entityClassMetaData.getName(), fieldsStr,
			entityClassMetaData.getIdField().getName());
    }
}
