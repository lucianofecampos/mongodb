package br.com.htecweb.mongodb.service.impl;

import br.com.htecweb.mongodb.entity.Customer;
import br.com.htecweb.mongodb.exception.CustomerNotFoundException;
import br.com.htecweb.mongodb.repository.CustomerRepository;
import br.com.htecweb.mongodb.dto.CustomerDTO;
import br.com.htecweb.mongodb.representation.CustomerRepresentation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.htecweb.mongodb.service.CustomerService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final Logger logger = getLogger(this.getClass());

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerRepresentation createCustomer(final CustomerDTO customerDTO) {
        logger.info("Creating customer {}", customerDTO.getFirstName());
        final Customer customer = Customer.createCustomer(customerDTO);
        return CustomerRepresentation.createCustomerRepresentation(customerRepository.insert(customer));
    }

    @Override
    public List<CustomerRepresentation> findAllByFirstName(final String firstName) {
        logger.info("Finding customer by first name {}", firstName);
        final List<Customer> customers = customerRepository.findAllByFirstName(firstName);
        return getListOfCustomersRepresentationByListOfCustomers(customers);
    }

    @Override
    public List<CustomerRepresentation> findAllByLastName(final String lastName) {
        logger.info("Finding customer by last name {}", lastName);
        final List<Customer> customers = customerRepository.findAllByLastName(lastName);
        return getListOfCustomersRepresentationByListOfCustomers(customers);
    }

    @Override
    public CustomerRepresentation findById(final String id) {
        logger.info("Finding customer by id {}", id);
        final Optional<Customer> customer = customerRepository.findById(id);
        return CustomerRepresentation.createCustomerRepresentation(customer.orElseThrow(
                () -> new CustomerNotFoundException(id)));
    }

    private List<CustomerRepresentation> getListOfCustomersRepresentationByListOfCustomers(List<Customer> customers) {
        return customers.stream().map(CustomerRepresentation::createCustomerRepresentation).collect(Collectors.toList());
    }
}
