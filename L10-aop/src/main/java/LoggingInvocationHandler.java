import annotations.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author administrator on 17.10.2024.
 */

public class LoggingInvocationHandler implements InvocationHandler {

    private final Set<Method> cache = new HashSet<>();

    private static final Logger logger = LoggerFactory.getLogger(LoggingInvocationHandler.class);
    private final Object target;

    public LoggingInvocationHandler(Object target) {
	this.target = target;
	for (Method method : target.getClass().getMethods()) {
	    if (method.isAnnotationPresent(Log.class)) {
		cache.add(method);
	    }
	}
    }

    private boolean isMethodLoggable(Method method) {
	for (Method methodInCache : cache) {
	    if (methodInCache.getName().equals(method.getName()) && Arrays.equals(methodInCache.getParameterTypes(),
			    method.getParameterTypes()))
		return true;
	}
	return false;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	if (isMethodLoggable(method)) {
	    StringBuilder logStr = new StringBuilder();
	    logStr.append(String.format("executed method: %s", method.getName()));
	    if (args != null && args.length > 0) {
		for (int i = 0; i < args.length; i++) {
		    logStr.append(String.format(", param: %s", args[i]));
		}
	    }
	    logger.info(logStr.toString());
	}
	return method.invoke(target, args);
    }
}
