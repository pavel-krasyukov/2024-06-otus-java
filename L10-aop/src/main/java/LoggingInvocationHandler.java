import annotations.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author administrator on 17.10.2024.
 */

public class LoggingInvocationHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInvocationHandler.class);
    private final Object target;

    public LoggingInvocationHandler(Object target) {
	this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	if (method.isAnnotationPresent(Log.class)) {
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
