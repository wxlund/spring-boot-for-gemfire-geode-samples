package io.pivotal.support;

import org.springframework.data.repository.CrudRepository;

interface CustomerRepository extends CrudRepository<Customer ,Integer>{}
