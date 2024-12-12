/**
 * @author administrator on 11.12.2024.
 */
package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData{

    private final Class<T> aClass;

    public EntityClassMetaDataImpl(Class<T> aClass){
	this.aClass = aClass;
    }

    @Override
    public String getName() {
	return aClass.getSimpleName();
    }

    @Override
    public Constructor getConstructor() {
	return Stream.of(aClass.getDeclaredConstructors())
			.filter(constructor -> constructor.getParameterCount() == 0)
			.map(constructor -> (Constructor<T>) constructor)
			.findFirst()
			.orElseThrow(() -> new RuntimeException(
					"Класс " + aClass.getSimpleName() + " не найдено конструктора по умолчанию"));
    }

    @Override
    public Field getIdField() {
	return Stream.of(aClass.getDeclaredFields())
			.filter(field -> field.isAnnotationPresent(Id.class))
			.findFirst()
			.orElseThrow(() -> new RuntimeException("не найдено поля Id"));
    }

    @Override
    public List<Field> getAllFields() {
	return Stream.of(aClass.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
	return Stream.of(aClass.getDeclaredFields()).filter(field -> !field.isAnnotationPresent(Id.class)).toList();
    }
}
