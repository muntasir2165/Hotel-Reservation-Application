package service;

import model.Customer;

import java.util.ArrayList;
import java.util.Collection;

public class CustomerService {
    private static final Collection<Customer> customers = new ArrayList<Customer>();
    private static CustomerService INSTANCE;

    private CustomerService() {
    }

    public static CustomerService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CustomerService();
        }

        return INSTANCE;
    }

    public Collection<Customer> getAllCustomers() {
        return customers;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        try {
            Customer customer = new Customer(firstName, lastName, email);
            customers.add(customer);
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add customer. Please enter valid email.");
        }
    }

    public Customer getCustomer(String customerEmail) {
        Customer result = null;
        for (Customer customer : customers) {
            if (customer.getEmail().equals(customerEmail)) {
                result = customer;
                break;
            }
        }

        return result;
    }
}
