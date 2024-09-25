package homework;

import homework.annotations.RunStatistics;

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
	LinkedHashMap<MethodTypes, List<Method>> result = new LinkedHashMap<>();
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
	Object testObject = classForTest.getConstructor().newInstance();
	try {
	    runAnnotationMetods(mapMethods, testObject, BEFORE);
	    RunStatistics runStatistics = runAnnotationMetods(mapMethods, testObject, TEST);
	    successCount = runStatistics.getCountSucess();
	    failureCount = runStatistics.getCountError();
	} catch (Exception exception) {
	    System.out.println(String.format("Failed, error - %s ", exception.getCause()));
	} finally {
	    runAnnotationMetods(mapMethods, testObject, AFTER);
	}
	printResult(mapMethods.get(TEST).size(), successCount, failureCount);
    }

    private static RunStatistics runAnnotationMetods(Map<MethodTypes, List<Method>> mapMethods, Object testObject, MethodTypes methodTypes)
		    throws InvocationTargetException, IllegalAccessException {
	RunStatistics runStatistics = new RunStatistics();
	int countSucess = 0;
	int countError = 0;
	for (Method method : mapMethods.get(methodTypes)) {
            try {
		method.invoke(testObject);
		countSucess++;
	    } catch (Exception exception) {
		System.out.println(String.format("Failed, error for methodClass %s - %s ", methodTypes.getAnnotationClass(),
				exception.getCause()));
		countError++;
	    }
	}
	runStatistics.setCountSucess(countSucess);
	runStatistics.setCountError(countError);
	return runStatistics;
    }

    private static void printResult(int total, int sucess, int error) {
	System.out.println(String.format("Total tests run: %s", total));
	System.out.println(String.format("Tests passed: %s", sucess));
	System.out.println(String.format("Tests failed: %s", error));
    }

}
