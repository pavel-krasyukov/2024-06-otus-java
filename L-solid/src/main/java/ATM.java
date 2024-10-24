import java.util.Map;

/**
 * @author administrator on 24.10.2024.
 */

public class ATM {
    DepositBoxInterface depositBoxInterface;

    public ATM(DepositBoxInterface depositBoxInterface) {
	this.depositBoxInterface = depositBoxInterface;
    }

    /**
     * Внести любую банкноту
     * banknote - номинал банкноты
    */
    public void putBanknote(int banknote) {
	depositBoxInterface.putBanknoteInDepositBox(banknote);
    }

    /**
     * Получить любую сумму
     * amount - сумма, которую необходимо выдать
    */
    public void getAmount(int amount) {
	Map<Integer, Integer> result = depositBoxInterface.getSumFromDepositBox(amount);
	System.out.println("getAmount " + result);
    }

    /**
     * Выдать остаток
     */
    public void getAll() {
	Map<Integer, Integer> result = depositBoxInterface.getAllFromDepositBox();
	System.out.println("getAll " + result);
    }

}

