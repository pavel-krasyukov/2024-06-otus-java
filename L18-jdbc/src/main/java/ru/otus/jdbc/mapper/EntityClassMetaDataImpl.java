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
    private final Constructor defConstructor;
    private final Field[] declaredFields;

    public EntityClassMetaDataImpl(Class<T> aClass) {
	this.aClass = aClass;
	try {
	    defConstructor = aClass.getDeclaredConstructor();
	} catch (NoSuchMethodException e) {
	    throw new RuntimeException(String.format("Class %s no default constructor found", aClass.getSimpleName()));
	}
	declaredFields = aClass.getDeclaredFields();
    }

    @Override
    public String getName() {
	return aClass.getSimpleName();
    }

    @Override
    public Constructor getConstructor() {
	return defConstructor;
    }

    @Override
    public Field getIdField() {
	return Stream.of(declaredFields)
			.filter(field -> field.isAnnotationPresent(Id.class))
			.findFirst()
			.orElseThrow(() -> new RuntimeException("no found feild Id"));
    }

    @Override
    public List<Field> getAllFields() {
	return Stream.of(declaredFields).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
	return Stream.of(declaredFields).filter(field -> !field.isAnnotationPresent(Id.class)).toList();
    }
}
