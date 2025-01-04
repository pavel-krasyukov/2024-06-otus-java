package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    private Object configInstance;

    private static final String NOT_FOUND = "Component %s not found in context.";
    private static final String DUPLICATED = "Component %s is duplicated in context.";

    public AppComponentsContainerImpl(Class<?>... configClasses) {
        Arrays.sort(configClasses, (c1, c2) -> {
            int order1 = getConfigOrder(c1);
            int order2 = getConfigOrder(c2);
            return Integer.compare(order1, order2);
        });
        for (Class<?> initialConfigClass : configClasses) {
            processConfig(initialConfigClass);
        }
    }

    private int getConfigOrder(Class<?> configClass) {
        AppComponentsContainerConfig annotation = configClass.getAnnotation(AppComponentsContainerConfig.class);
        return annotation != null ? annotation.order() : Integer.MAX_VALUE; // На случай, если аннотации нет
    }

    private void processConfig(Class<?> configClass) {
	checkConfigClass(configClass);
	try {
	    configInstance = configClass.getDeclaredConstructor().newInstance();
	} catch (Exception exception) {
	    throw new RuntimeException("Filed create configInstance", exception);
	}
	Method[] methods = configClass.getDeclaredMethods();

	// Сортируем методы, чтобы обеспечить порядок создания компонентов
	Arrays.stream(methods)
			.filter(method -> method.isAnnotationPresent(AppComponent.class))
			.sorted((m1, m2) -> {
			    int order1 = m1.getAnnotation(AppComponent.class).order();
			    int order2 = m2.getAnnotation(AppComponent.class).order();
			    return Integer.compare(order1, order2);
			})
			.forEach(method -> createComponent(method, configInstance));
    }

    private void createComponent(Method method, Object configInstance) {
	try {
	    // Получаем параметры для метода компонента
	    Class<?>[] parameterTypes = method.getParameterTypes();
	    Object[] parameters = new Object[parameterTypes.length];

	    for (int i = 0; i < parameterTypes.length; i++) {
		// Рекурсивно ищем и подготавливаем необходимые параметры
		parameters[i] = getAppComponent(parameterTypes[i]);
	    }

	    // Создаем экземпляр компонента
	    Object component = method.invoke(configInstance, parameters);

	    // Сохраняем компонент в коллекциях
	    String componentName = method.getAnnotation(AppComponent.class).name();
	    appComponents.add(component);
	    // Проверка на дублирование имени компонента
	    if (appComponentsByName.containsKey(componentName)) {
		throw new RuntimeException(String.format(DUPLICATED, componentName));
	    }
	    appComponentsByName.put(componentName, component);
	} catch (Exception e) {
	    throw new RuntimeException("Failed to create app component", e);
	}
    }

    private void checkConfigClass(Class<?> configClass) {
	if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
	    throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
	}
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
	List<Object> matchedComponents = appComponents.stream()
			.filter(component -> componentClass.isAssignableFrom(component.getClass()))
			.toList();

	if (matchedComponents.isEmpty()) {
	    throw new RuntimeException(String.format(NOT_FOUND, componentClass.getName()));
	}

	if (matchedComponents.size() > 1) {
	    throw new RuntimeException(String.format(DUPLICATED, componentClass.getName()));
	}

	return componentClass.cast(matchedComponents.get(0));
	/*return appComponents.stream()
			.filter(componentClass::isInstance)
			.map(componentClass::cast)
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("No component found for class: " + componentClass));*/
    }

    @Override
    public <C> C getAppComponent(String componentName) {
	Object component = appComponentsByName.get(componentName);
	if (component == null) {
	    throw new RuntimeException(String.format(NOT_FOUND, componentName));
	}
	return (C) component;
    }
}
