package homework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static homework.MethodTypes.*;

/**
 * @author administrator on 09.09.2024.
 */

public class TestRunner {

    private static Map<MethodTypes, List<Method>> initializeMapMethods() {
	Map<MethodTypes, List<Method>> result = new LinkedHashMap<>();
	for (MethodTypes methodTypes : MethodTypes.values()) {
	    result.put(methodTypes, new ArrayList<>());
	}
	return result;
    }

    public static void runTests(String className) {
	try {
	    Class<?> classForTest = Class.forName(className);
	    Method[] classMethods = classForTest.getDeclaredMethods();
	    //Распределить методы по спискам согласно их аннотациям
	    var mapMethods = fillMapMethods(classMethods);
	    //Вызов методов
	    runClassMethods(classForTest, mapMethods);
	} catch (Exception exc) {
	    exc.printStackTrace();
	}
    }

    private static Map<MethodTypes, List<Method>> fillMapMethods(Method[] methods) {
	var mapMethods = initializeMapMethods();
	for (MethodTypes methodTypes : MethodTypes.values()) {
	    for (Method method : methods) {
		if (method.isAnnotationPresent(methodTypes.getAnnotationClass())) {
		    mapMethods.get(MethodTypes.fromAnnotationClass(methodTypes.getAnnotationClass())).add(method);
		}
	    }
	}
	return mapMethods;
    }

    private static void runClassMethods(Class<?> classForTest, Map<MethodTypes, List<Method>> mapMethods)
		    throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
	int successCount = 0;
	int failureCount = 0;
	var listTestMethods = mapMethods.get(TEST);
	for (Method testMethod : listTestMethods) {
	    Object testObject = classForTest.getConstructor().newInstance();
	    try {
		runAnnotationMetods(mapMethods, testObject, BEFORE);
		testMethod.invoke(testObject);
		successCount++;
	    } catch (Exception exception) {
		System.out.println(String.format("Failed, error - %s ", exception.getCause()));
		failureCount++;
	    } finally {
		runAnnotationMetods(mapMethods, testObject, AFTER);
	    }
	}
	printResult(listTestMethods.size(), successCount, failureCount);
    }

    private static void runAnnotationMetods(Map<MethodTypes, List<Method>> mapMethods, Object testObject, MethodTypes methodTypes)
		    throws InvocationTargetException, IllegalAccessException {
	for (Method method : mapMethods.get(methodTypes)) {
	    method.invoke(testObject);
	}
    }

    private static void printResult(int total, int sucess, int error) {
	System.out.println(String.format("Total tests run: %s", total));
	System.out.println(String.format("Tests passed: %s", sucess));
	System.out.println(String.format("Tests failed: %s", error));
    }

}
