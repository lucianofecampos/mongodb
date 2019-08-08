package br.com.htecweb.mongodb.controller;

import br.com.htecweb.mongodb.dto.CustomerDTO;
import br.com.htecweb.mongodb.representation.CustomerRepresentation;
import br.com.htecweb.mongodb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createCustomer(@Validated @RequestBody CustomerDTO customerDTO) {
        final CustomerRepresentation customer = customerService.createCustomer(customerDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public CustomerRepresentation findCustomerById(@PathVariable String id) {
        return customerService.findById(id);
    }

    @GetMapping("/firstname/{firstName}")
    public List<CustomerRepresentation> findCustomerByFirstName(@PathVariable String firstName) {
        return customerService.findAllByFirstName(firstName);
    }

    @GetMapping("/lastname/{lastName}")
    public List<CustomerRepresentation> findAllCustomerByLastName(@PathVariable String lastName) {
        return customerService.findAllByLastName(lastName);
    }
}
