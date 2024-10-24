/**
 * @author administrator on 24.10.2024.
 */

public class Main {
    public static void main(String[] args) {
	ATM atm = new ATM(new DepositBox());
	atm.putBanknote(100);
	atm.putBanknote(500);
	atm.putBanknote(1000);
	atm.putBanknote(5000);

	atm.getAmount(570000);

	atm.getAmount(5500);

	atm.getAll();

    }

}
