package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public enum MethodTypes {
    BEFORE(Before.class),
    TEST(Test.class),
    AFTER(After.class);

    private Class<? extends Annotation> annotationClass;

    MethodTypes(Class<? extends Annotation> annotationClass) {
	this.annotationClass = annotationClass;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public static MethodTypes fromAnnotationClass(Class<? extends Annotation> annotationClass) {
        for (MethodTypes type : MethodTypes.values()) {
            if (type.getAnnotationClass().equals(annotationClass)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No MethodTypes found for class: " + annotationClass.getName());
    }

    private static final Map<MethodTypes, List<Method>> mapMethods = initMapMethods();

    private static Map<MethodTypes, List<Method>> initMapMethods() {
        LinkedHashMap<MethodTypes, List<Method>> result = new LinkedHashMap<>();
        for (MethodTypes methodTypes : MethodTypes.values()) {
            result.put(methodTypes, null);
        }
        return result;
    }

    public static Map<MethodTypes, List<Method>> getMapMethods() {
        return mapMethods;
    }

    public static void clearMapMethodsListMethods() {
        for (List<Method> methods : mapMethods.values()) {
            if (methods != null) {
                methods.clear();
            }
        }
    }
}
