import java.lang.reflect.Proxy;

/**
 * @author administrator on 17.10.2024.
 */

public class Main {
    public static void main(String[] args) {
	LoggingInterface loggingInterface = (LoggingInterface) Proxy.newProxyInstance(LoggingInterface.class.getClassLoader(),
			new Class[] { LoggingInterface.class }, new LoggingInvocationHandler(new Logging()));
	loggingInterface.calculation(1);
	loggingInterface.calculation(1, 2);
	loggingInterface.calculation(1, 2, "test");
    }
}
