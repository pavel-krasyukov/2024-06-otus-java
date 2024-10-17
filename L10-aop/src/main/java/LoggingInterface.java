import annotations.Log;

public interface LoggingInterface {
    @Log
    void calculation(int param1);

    @Log
    void calculation(int param1, int param2);

    @Log
    void calculation(int param1, int param2, String param3);
}
