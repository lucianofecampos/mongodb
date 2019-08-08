package br.com.htecweb.mongodb.service;

import br.com.htecweb.mongodb.dto.CustomerDTO;
import br.com.htecweb.mongodb.representation.CustomerRepresentation;

import java.util.List;

public interface CustomerService {

    CustomerRepresentation createCustomer(CustomerDTO customerDTO);

    List<CustomerRepresentation> findAllByFirstName(String firstName);

    List<CustomerRepresentation> findAllByLastName(String lastName);

    CustomerRepresentation findById(String id);
}
