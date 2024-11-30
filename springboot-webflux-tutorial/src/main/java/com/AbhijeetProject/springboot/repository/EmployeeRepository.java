package com.AbhijeetProject.springboot.repository;

import com.AbhijeetProject.springboot.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee,String> {

}
