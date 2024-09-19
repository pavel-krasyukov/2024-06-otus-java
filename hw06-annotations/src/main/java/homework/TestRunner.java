package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static homework.MethodTypes.TEST;

/**
 * @author administrator on 09.09.2024.
 */

public class TestRunner {

    private static Map<MethodTypes, List<Method>> getMapMethods() {
	LinkedHashMap<MethodTypes, List<Method>> result = new LinkedHashMap<>();
	for (MethodTypes methodTypes : MethodTypes.values()) {
	    result.put(methodTypes, new ArrayList<>());
	}
	return result;
    }

    public static void runTests(String className) {
	int successCount = 0;
	int failureCount = 0;
	var mapMethods = getMapMethods();
	try {
	    Class<?> classForTest = Class.forName(className);
	    Method[] classMethods = classForTest.getDeclaredMethods();
	    //Распределить методы по спискам согласно их аннотациям
	    for (MethodTypes methodTypes : MethodTypes.values()) {
		fillMapMethods(classMethods, mapMethods, methodTypes.getAnnotationClass());
	    }
	    Object testObject = classForTest.getConstructor().newInstance();
	    try {
		runAnnotationMetods(classForTest, testObject, Before.class);
		runAnnotationMetods(classForTest, testObject, Test.class);
		successCount++;
	    } catch (Exception exception) {
		failureCount++;
		System.out.println(String.format("Failed, error - %s ", exception.getCause()));
	    } finally {
		runAnnotationMetods(classForTest, testObject, After.class);
	    }

	} catch (Exception exc) {
	    exc.printStackTrace();
	}
	printResult(mapMethods.get(TEST).size(), successCount, failureCount);
    }

    private static void fillMapMethods(Method[] methods, Map<MethodTypes, List<Method>> mapMethods,
		    Class<? extends Annotation> annotationClass) {
	for (Method method : methods) {
	    if (method.isAnnotationPresent(annotationClass)) {
		mapMethods.get(MethodTypes.fromAnnotationClass(annotationClass)).add(method);
	    }
	}
    }

    private static void runAnnotationMetods(Class<?> classForTest, Object testObject, Class<? extends Annotation> annotationClass)
		    throws InvocationTargetException, IllegalAccessException {
	for (Method method : classForTest.getDeclaredMethods()) {
	    if (method.isAnnotationPresent(annotationClass)) {
		method.invoke(testObject);
	    }
	}
    }

    private static void printResult(int total, int sucess, int error) {
	System.out.println(String.format("Total tests run: %s", total));
	System.out.println(String.format("Tests passed: %s", sucess));
	System.out.println(String.format("Tests failed: %s", error));
    }

}
