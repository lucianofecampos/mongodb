package br.com.htecweb.mongodb.repository;

import br.com.htecweb.mongodb.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    List<Customer> findAllByFirstName(String firstName);
    List<Customer> findAllByLastName(String lastName);
}
