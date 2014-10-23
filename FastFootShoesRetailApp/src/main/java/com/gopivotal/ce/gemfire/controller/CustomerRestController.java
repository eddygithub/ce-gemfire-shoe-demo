package com.gopivotal.ce.gemfire.controller;

import io.pivotal.ce.gemfire.fastfootshoes.model.Customer;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.CustomerRepository;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController {

	@Autowired
	CustomerRepository customerRepository;

	@RequestMapping("/customers")
	Collection<Customer> search(@RequestParam String email) {
		return this.customerRepository.searchCustomers(email + "%");
	}
}
