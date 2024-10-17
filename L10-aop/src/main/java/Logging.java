import annotations.Log;

/**
 * @author administrator on 17.10.2024.
 */

public class Logging implements LoggingInterface {

    public void calculation(int param1) {
	System.out.println("Logging calculation1");
    }

    public void calculation(int param1, int param2) {
	System.out.println("Logging calculation2");
    }

    public void calculation(int param1, int param2, String param3) {
	System.out.println("Logging calculation3");
    }
}
