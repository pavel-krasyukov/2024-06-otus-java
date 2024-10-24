import annotations.Log;

/**
 * @author administrator on 17.10.2024.
 */

public class Logging implements LoggingInterface {

    @Log
    public void calculation(int param1) {
	System.out.println("Logging calculation1");
    }
    @Log
    public void calculation(int param1, int param2) {
	System.out.println("Logging calculation2");
    }
    @Log
    public void calculation(int param1, int param2, String param3) {
	System.out.println("Logging calculation3");
    }
}
