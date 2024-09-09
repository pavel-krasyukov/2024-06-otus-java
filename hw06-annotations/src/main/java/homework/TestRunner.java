package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author administrator on 09.09.2024.
 */

public class TestRunner {

    public static void runTests(String className) {
	int successCount = 0;
	int failureCount = 0;
	int totalCount = 0;

	try {
	    Class<?> classForTest = Class.forName(className);
	    Method[] methods = classForTest.getDeclaredMethods();
	    for (Method method : methods) {
		if (method.isAnnotationPresent(Test.class)) {
		    totalCount++;
		    Object testObject = classForTest.getConstructor().newInstance();
		    try {
			runAnnotationMetods(classForTest, testObject, Before.class);
			method.invoke(testObject);
			successCount++;
		    } catch (Exception exception) {
			failureCount++;
			System.out.println(String.format("Failed method : %s, error - %s ", method.getName(), exception.getCause()));
		    } finally {
			runAnnotationMetods(classForTest, testObject, After.class);
		    }
		}
	    }
	} catch (Exception exc) {
	    exc.printStackTrace();
	}
	System.out.println("Total tests run: " + totalCount);
	System.out.println("Tests passed: " + successCount);
	System.out.println("Tests failed: " + failureCount);
    }

    private static void runAnnotationMetods(Class<?> classForTest, Object testObject, Class<? extends Annotation> annotationClass)
		    throws InvocationTargetException, IllegalAccessException {
	for (Method method : classForTest.getDeclaredMethods()) {
	    if (method.isAnnotationPresent(annotationClass)) {
		method.invoke(testObject);
	    }
	}
    }

    public static void main(String[] args) {
	runTests("homework.MyTest");
    }

}
