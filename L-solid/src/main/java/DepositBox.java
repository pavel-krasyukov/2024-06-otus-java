import java.util.*;

/**
 * @author administrator on 24.10.2024.
 */

public class DepositBox implements DepositBoxInterface {

    //Хранилище банкнот (ключ - номинал, значение - кол-во)
    private final Map<Integer, Integer> depositBox = new HashMap<>();

    @Override
    public void putBanknoteInDepositBox(int banknote) {
        int countBanknotes = depositBox.get(banknote) != null ? depositBox.get(banknote) : 0;
        depositBox.put(banknote, ++countBanknotes);
    }

    @Override
    public Map<Integer, Integer> getSumFromDepositBox(int amount) {
        int amountOrig = amount;
        Map<Integer, Integer> depositBoxCopy = new HashMap<>(depositBox);
        Map<Integer, Integer> result = new HashMap<>();
        List<Integer> banknotes = new ArrayList<>(depositBox.keySet());
        Collections.sort(banknotes, Collections.reverseOrder());

        for (Integer banknote : banknotes) {
            int count = depositBox.get(banknote);
            while (amount >= banknote && count > 0) {
                if (!result.containsKey(banknote)) {
                    result.put(banknote, 0);
                }
                result.put(banknote, result.get(banknote) + 1);
                count--;
                amount -= banknote;
            }
            // Обновляем количество банкнот в ячейке
            depositBox.put(banknote, count);
            if (amount == 0) {
                break;
            }
        }
        if (amount > 0) {
            System.out.println("Невозможно выдать запрашиваемую сумму " + amountOrig);
            depositBox.clear();
            depositBox.putAll(depositBoxCopy);
            return Collections.emptyMap();
        }
        return result;
    }

    @Override
    public Map<Integer, Integer> getAllFromDepositBox() {
        return depositBox;
    }
}
