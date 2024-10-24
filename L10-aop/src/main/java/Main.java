import java.lang.reflect.Proxy;

/**
 * @author administrator on 17.10.2024.
 */

public class Main {
    public static void main(String[] args) {
	Logging logging = new Logging();
	LoggingInterface loggingProxy = (LoggingInterface) Proxy.newProxyInstance(logging.getClass().getClassLoader(),
			new Class[] { LoggingInterface.class }, new LoggingInvocationHandler(logging));
	loggingProxy.calculation(1);
	loggingProxy.calculation(1, 2);
	loggingProxy.calculation(1, 2, "test");
    }
}
