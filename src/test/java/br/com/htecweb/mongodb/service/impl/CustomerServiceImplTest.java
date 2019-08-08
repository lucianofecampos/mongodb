package br.com.htecweb.mongodb.service.impl;

import br.com.htecweb.mongodb.dto.CustomerDTO;
import br.com.htecweb.mongodb.entity.Customer;
import br.com.htecweb.mongodb.exception.CustomerNotFoundException;
import br.com.htecweb.mongodb.repository.CustomerRepository;
import br.com.htecweb.mongodb.representation.CustomerRepresentation;
import br.com.htecweb.mongodb.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceImplTest {

    private final Customer customer = new Customer("id", "firstName", "lastName", "email");

    @MockBean
    private CustomerRepository customerRepositoryMock;

    @Autowired
    private CustomerService customerService;

    @Test
    public void createCustomerSuccess() {
        final CustomerDTO customerDTO = new CustomerDTO("firstName", "lastName", "email");
        when(customerRepositoryMock.insert(Customer.createCustomer(customerDTO))).thenReturn(customer);
        final CustomerRepresentation customerSaved = customerService.createCustomer(customerDTO);
        assertEquals(customerSaved, CustomerRepresentation.createCustomerRepresentation(customer));
    }

    @Test
    public void findAllByFirstName() {
        when(customerRepositoryMock.findAllByFirstName(customer.getFirstName())).thenReturn(Collections.singletonList(customer));
        final List<CustomerRepresentation> customerRepresentations = customerService.findAllByFirstName(customer.getFirstName());
        assertEquals(1, customerRepresentations.size());
        assertEquals(CustomerRepresentation.createCustomerRepresentation(customer), customerRepresentations.get(0));
    }

    @Test
    public void findAllByLastName() {
        when(customerRepositoryMock.findAllByLastName(customer.getLastName())).thenReturn(Collections.singletonList(customer));
        final List<CustomerRepresentation> customerRepresentations = customerService.findAllByLastName(customer.getLastName());
        assertEquals(1, customerRepresentations.size());
        assertEquals(CustomerRepresentation.createCustomerRepresentation(customer), customerRepresentations.get(0));
    }

    @Test
    public void findById() {
        when(customerRepositoryMock.findById(customer.getId())).thenReturn(Optional.of(customer));
        final CustomerRepresentation customerRepresentation = customerService.findById(customer.getId());
        assertEquals(CustomerRepresentation.createCustomerRepresentation(customer), customerRepresentation);
    }

    @Test(expected = CustomerNotFoundException.class)
    public void findByIdCustomerNotFound() {
        customerService.findById("fakeId");
    }
}
