package br.com.htecweb.mongodb.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class CustomerDTO {

    @NotEmpty(message = "Customer's first name must not be null or empty")
    private final String firstName;

    @NotEmpty(message = "Customer's last name must not be null or empty")
    private final String lastName;

    @Email(message = "Customer's email must fit the pattern ^@\\s]+@[^@\\s]+. eg.: example@server.com")
    @NotEmpty(message = "Customer's email must not be null or empty")
    private final String email;

    public CustomerDTO(@NotEmpty(message = "Customer's first name must not be null or empty") String firstName,
                       @NotEmpty(message = "Customer's last name must not be null or empty") String lastName,
                       @Email(message = "Customer's email must fit the pattern ^@\\s]+@[^@\\s]+. eg.: example@server.com")
                       @NotEmpty(message = "Customer's email must not be null or empty") String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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
        if (!(o instanceof CustomerDTO)) return false;
        CustomerDTO that = (CustomerDTO) o;
        return Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getEmail(), that.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getEmail());
    }
}
