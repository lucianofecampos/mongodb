package br.com.htecweb.mongodb.controller;

import br.com.htecweb.mongodb.dto.CustomerDTO;
import br.com.htecweb.mongodb.exception.CustomerNotFoundException;
import br.com.htecweb.mongodb.representation.CustomerRepresentation;
import br.com.htecweb.mongodb.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerControllerTest {

    private final CustomerRepresentation customerRepresentation = new CustomerRepresentation("id",
                                                                                             "firstName",
                                                                                             "lastName",
                                                                                             "email@email.com");

    private final String customerJson = "{\"firstName\":\"firstName\", \"lastName\":\"lastName\", \"email\":\"email@email.com\"}";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CustomerService customerService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .build();
    }

    @Test
    public void createCustomer() throws Exception {
        final CustomerDTO customerDTO = new CustomerDTO("firstName", "lastName", "email@email.com");
        when(customerService.createCustomer(customerDTO))
                .thenReturn(customerRepresentation);
        mockMvc.perform(post("/customer/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(customerJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", "http://localhost/customer/id"));
        verify(customerService, times(1)).createCustomer(customerDTO);
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void createCustomerValidationException() throws Exception {
        final String customerInvalidJson = "{\"firstName\":\"\", \"lastName\":\"lastName\", \"email\":\"email@email.com\"}";
        mockMvc.perform(post("/customer/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(customerInvalidJson))
                .andExpect(status().isBadRequest());
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void createCustomerValidationEmailFormatException() throws Exception {
        final String customerInvalidEmailJson = "{\"firstName\":\"firstName\", \"lastName\":\"lastName\", \"email\":\"email@email.com@email.com\"}";
        mockMvc.perform(post("/customer/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(customerInvalidEmailJson))
                .andExpect(status().isBadRequest());
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void findCustomerById() throws Exception {
        when(customerService.findById(customerRepresentation.getId())).thenReturn(customerRepresentation);
        mockMvc.perform(get("/customer/{id}", customerRepresentation.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(customerJson));
        verify(customerService, times(1)).findById(customerRepresentation.getId());
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void findCustomerByIdNotFound() throws Exception {
        when(customerService.findById("fakeId")).thenThrow(CustomerNotFoundException.class);
        mockMvc.perform(get("/customer/{id}", "fakeId"))
                .andExpect(status().isNotFound());
        verify(customerService, times(1)).findById("fakeId");
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void findCustomerByFirstName() throws Exception {
        when(customerService.findAllByFirstName(customerRepresentation.getFirstName())).thenReturn(Collections.singletonList(customerRepresentation));
        mockMvc.perform(get("/customer/firstname/{firstName}", customerRepresentation.getFirstName()))
                .andExpect(status().isOk())
                .andExpect(content().json("[" + customerJson + "]"));
        verify(customerService, times(1)).findAllByFirstName(customerRepresentation.getFirstName());
        verifyNoMoreInteractions(customerService);
    }

    @Test
    public void findAllCustomerByLastName() throws Exception {
        when(customerService.findAllByLastName(customerRepresentation.getLastName())).thenReturn(Collections.singletonList(customerRepresentation));
        mockMvc.perform(get("/customer/lastname/{lastName}", customerRepresentation.getLastName()))
                .andExpect(status().isOk())
                .andExpect(content().json("[" + customerJson + "]"));
        verify(customerService, times(1)).findAllByLastName(customerRepresentation.getLastName());
        verifyNoMoreInteractions(customerService);
    }
}
