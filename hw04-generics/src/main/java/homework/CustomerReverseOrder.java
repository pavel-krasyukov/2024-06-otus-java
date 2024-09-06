package homework;

import java.util.ArrayDeque;

public class CustomerReverseOrder {

    private final ArrayDeque<Customer> customerArrayDeque = new ArrayDeque<>();

    public void add(homework.Customer customer) {
	customerArrayDeque.push(customer);
    }

    public homework.Customer take() {
	return customerArrayDeque.pop();
    }
}