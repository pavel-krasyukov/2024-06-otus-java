package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

import java.lang.annotation.Annotation;

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

}
