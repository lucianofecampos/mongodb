package br.com.htecweb.mongodb.representation;

import br.com.htecweb.mongodb.entity.Customer;

import java.util.Objects;

public class CustomerRepresentation {

    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public CustomerRepresentation() {
    }

    public CustomerRepresentation(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static CustomerRepresentation createCustomerRepresentation(Customer customer) {
        return new CustomerRepresentation(customer.getId(), customer.getFirstName(),
                                          customer.getLastName(), customer.getEmail());
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerRepresentation)) return false;
        CustomerRepresentation that = (CustomerRepresentation) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getEmail());
    }
}
