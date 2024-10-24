import java.util.Map;

public interface DepositBoxInterface {
    void putBanknoteInDepositBox(int banknote);

    Map<Integer, Integer> getSumFromDepositBox(int amount);

    Map<Integer, Integer> getAllFromDepositBox();
}
