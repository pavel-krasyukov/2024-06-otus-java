package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<homework.Customer, String> mapCustomers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<homework.Customer, String> getSmallest() {
	var firstEntry = mapCustomers.firstEntry();
	return firstEntry == null ? null : getEntry(firstEntry);
    }

    public Map.Entry<homework.Customer, String> getNext(homework.Customer customer) {
	var nextEntry = mapCustomers.higherEntry(customer);
	return nextEntry == null ? null : getEntry(nextEntry);
    }

    public void add(homework.Customer customer, String data) {
	mapCustomers.put(customer, data);
    }

    private Map.Entry<Customer, String> getEntry(Map.Entry<Customer, String> entry) {
	return Map.entry(new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores()), entry.getValue());
    }
}