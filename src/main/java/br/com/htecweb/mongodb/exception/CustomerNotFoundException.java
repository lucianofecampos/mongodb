package br.com.htecweb.mongodb.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String id) {
        super("Customer id '" + id + "' not founded.");
    }
}
